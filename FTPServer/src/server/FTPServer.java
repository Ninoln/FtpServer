package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {

    private static int port = 21;


    public static void main(String[] args) throws IOException {
        //绑定端口
        ServerSocket serverSocket = new ServerSocket(port);

        //监听
        while (true){
            Socket socket = serverSocket.accept();
            FTPUserController ftpUserController = new FTPUserController(socket);
            ftpUserController.start();
        }
    }
}
