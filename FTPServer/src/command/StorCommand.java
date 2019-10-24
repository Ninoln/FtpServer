package command;

import server.FTPUserController;

import java.io.*;
import java.net.Socket;

/**
 * 上传文件
 */
public class StorCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        String path = ftpUserController.getRootPath()+File.separator+ftpUserController.getCurrentChildPath()
                + File.separator + info;
        File file = new File(path);

        try (
                Socket s = new Socket(ftpUserController.getUserHost(), ftpUserController.getUserPort());
                BufferedInputStream in = new BufferedInputStream(s.getInputStream());
                FileOutputStream fout = new FileOutputStream(file)
        ) {

            writer.write("125 ready to transfer.\r\n");
            writer.flush();

            byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf)) != -1) {
                fout.write(buf, 0, n);
            }

            writer.write("220 put file success.\r\n");
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
