package command;

import dao.UserDao;
import server.FTPUserController;

import java.io.IOException;
import java.io.Writer;

public class UserCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        String res;

        // 验证用户
        if (UserDao.userRight(info)) {
            res = "331 user " + info + " is ready,need password.";    //用户名正确，需要口令
            ftpUserController.setUser(info);
        } else {
            res = "501 username error.";    //参数语法错误
        }

        try {
            writer.write(res + "\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
