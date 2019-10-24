package command;

import dao.UserDao;
import server.FTPUserController;

import java.io.IOException;
import java.io.Writer;

public class PassCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        String user = ftpUserController.getUser();
        String pwd = info;

        String res;
        if (UserDao.pwdRight(user, pwd)) {
            res = "230 User " + user + " logged in";
            ftpUserController.setLogin(true);
            ftpUserController.setRootPath("FTPServer/file/"+user);
        } else {
            res = "501 Login Error.";
            ftpUserController.setUser(null);
        }


        try {
            writer.write(res);
            writer.write("\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
