package command;

import server.FTPUserController;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Dir 目录指令
 */
public class ListCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {

        String dirPath = ftpUserController.getRootPath() + File.separator
                + ftpUserController.getCurrentChildPath() + File.separator + info;
        File dir = new File(dirPath);
        //File rootDir = new File(ftpUserController.getRootPath());

        if (!dir.exists()) {
            try {
                writer.write("501 Syntax error in parameters or arguments.");
                writer.write("\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            StringBuilder dirRes = new StringBuilder();
            dirRes.append("Category :\n");
            fileInformation(dir, dirRes);   //置入文件信息

            try (Socket res = new Socket(ftpUserController.getUserHost(), ftpUserController.getUserPort());
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(res.getOutputStream(), "UTF-8"))) {

                writer.write("150 File status okay;about to open data connection.\r\n");
                bw.write(dirRes.toString());
                bw.flush();
                writer.write("220 ending.\r\n");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 文件信息
    private void fileInformation(File dir, StringBuilder dirRes) {

        if (dir == null)
            return;

        //置入子元素
        for (String name : dir.list()) {
            File temp = new File(dir.getAbsolutePath() + File.separator + name);
            // time
            dirRes.append(new SimpleDateFormat("yyyy/MM/dd  HH:mm")
                    .format(new Date(temp.lastModified())) + "\t");
            // type
            if (temp.isDirectory())
                dirRes.append("<DIR>\t");
            else
                dirRes.append("<FILE>\t");
            // size
            dirRes.append(this.fileSize(temp) + "\t");
            // name
            dirRes.append(temp.getName() + "\n");
        }
    }

    // 文件大小
    private long fileSize(File file) {
        if (file.isFile())
            return file.length();

        File[] listFiles = file.listFiles();
        long fileLength = 0;
        if (listFiles != null) {
            for (File files : listFiles)
                fileLength += fileSize(files);
        }
        return fileLength;
    }

}
