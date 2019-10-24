package command;

import server.FTPUserController;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 创建目录
 */
public class MkdCommand implements Command {
    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        String mkdir = ftpUserController.getRootPath() + File.separator + ftpUserController.getCurrentChildPath()
                + File.separator + info;

        File dir = new File(mkdir);
        boolean success = false;

        if (!dir.exists()) {
            success = dir.mkdir();
        }

        try {
            if (success){
                writer.write("257 create directory success.\r\n");
            }else{
                writer.write("550 create directory error.\r\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
