package command;

import server.FTPUserController;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 删除指令
 */
public class DeleCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        String path = ftpUserController.getRootPath() + File.separator +
                ftpUserController.getCurrentChildPath() + File.separator + info;
        File file = new File(path);
        boolean isdele = false;

        if (file.exists()) {
            isdele = file.delete();
        }

        try {
            if (!isdele) {
                writer.write("450 delete error.\r\n");
            } else {
                writer.write("250 delete success.\r\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
