package command;

import server.FTPUserController;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 重命名
 */
public class RntoCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        File file = ftpUserController.getRenameFile();
        ftpUserController.setRenameFile(null);
        String filePath = ftpUserController.getRootPath() + File.separator + ftpUserController.getCurrentChildPath();

        boolean change = file.renameTo(new File(filePath + File.separator + info));

        try {
            if (change) {
                writer.write("250 rename success.\r\n");
            } else {
                writer.write("553 rename error.\r\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
