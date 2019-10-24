package server;

import command.*;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class FTPUserController extends Thread {
    private Socket socket;
    private String userHost;
    private int userPort;

    private String user;
    private boolean isLogin = false;
    private boolean firstVisit = true;
    private String rootPath;
    private String currentChildPath = "";
    private File RenameFile;


    public FTPUserController(Socket socket) {
        this.socket = socket;
        this.userHost = socket.getInetAddress().getHostAddress();
        this.userPort = socket.getPort();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserHost() {
        return userHost;
    }

    public void setUserHost(String userHost) {
        this.userHost = userHost;
    }

    public int getUserPort() {
        return userPort;
    }

    public void setUserPort(int userPort) {
        this.userPort = userPort;
    }

    public File getRenameFile() {
        return RenameFile;
    }

    public void setRenameFile(File renameFile) {
        this.RenameFile = renameFile;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getCurrentChildPath() {
        return currentChildPath;
    }

    public void setCurrentChildPath(String currentChildPath) {
        this.currentChildPath = currentChildPath;
    }


    // 输出连接信息
    private void printConnect() {
        System.out.println("-------");
        System.out.println(new Date().toString());
        System.out.println("Connect IP:" + this.userHost + "\tport:" + this.userPort);
        System.out.println("-------");
    }

    // 判断是否需要登录
    private boolean notNeedLogin(Command command) {
        if (command instanceof UserCommand || command instanceof PassCommand)
            return true;
        else
            return isLogin;

    }

    @Override
    public void run() {
        printConnect();

        try (
                //得到输入输出流
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"))
        ) {
            while (true) {
                //初次启动
                if (firstVisit) {
                    writer.write("220 Service ready for new user.");    //服务器就绪
                    writer.write("\r\n");
                    writer.flush();
                }

                //初始化完成
                if (!socket.isClosed()) {
                    String rd = reader.readLine();
                    System.out.println(rd);
                    String[] command = rd.split(" ");

                    Command cmd = CommandController.useCommand(command[0]);

                    // 判断终止
                    if (cmd instanceof QuitCommand) {
                        writer.write("221 Connect close.\r\n");
                        writer.flush();
                        return;
                    }

                    // 命令执行
                    if (notNeedLogin(cmd)) {
                        String info = "";
                        if (command.length >= 2)
                            info = command[1];
                        cmd.result(this, writer, info);
                    } else {
                        if (firstVisit) {
                            firstVisit = false;
                            writer.write("\r\n");
                        }else
                            writer.write("has not logged in\r\n");
                        writer.flush();
                    }

                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
