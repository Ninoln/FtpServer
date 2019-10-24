package command;

import server.FTPUserController;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 打印工作目录
 */
public class PwdCommand implements Command {
    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        String currentPath = File.separator + ftpUserController.getCurrentChildPath();

        try {
            writer.write("257 currentPath:" + currentPath + "\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
