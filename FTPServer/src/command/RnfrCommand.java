package command;

import server.FTPUserController;

import java.io.File;
import java.io.Writer;

/**
 * 接收重命名文件信息
 */
public class RnfrCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        String path = ftpUserController.getRootPath() + File.separator
                + ftpUserController.getCurrentChildPath() + File.separator + info;
        File file = new File(path);


        try {
            if (file.exists()) {
                ftpUserController.setRenameFile(file);
                writer.write("350 File exists, ready for destination name\r\n");
                writer.flush();
            } else {
                writer.write("450 file or directory non-exist.\r\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
