package command;

import server.FTPUserController;

import java.io.*;
import java.net.Socket;

/**
 * 对应 RETR指令，即下载
 */
public class RetrCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        String path = ftpUserController.getRootPath() + File.separator
                + ftpUserController.getCurrentChildPath() + File.separator + info;
        File file = new File(path);

        try {
            // 文件不存在
            if (!file.exists() || !file.isFile()) {
                writer.write("550 file does not exist\r\n");
                writer.flush();
                return;
            }

            // 传输开始
            writer.write("125 Data connection already open; Transfer starting.\r\n");
            writer.flush();

            Socket s = new Socket(ftpUserController.getUserHost(), ftpUserController.getUserPort());
            BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
            FileInputStream is = new FileInputStream(file);

            byte[] buf = new byte[1024];
            int n;
            while ((n = is.read(buf)) != -1) {
                out.write(buf, 0, n);
            }

            writer.write("220 File transfer complete.\r\n");
            writer.flush();

            out.flush();
            is.close();
            out.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
