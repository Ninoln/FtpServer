package command;

import server.FTPUserController;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 改变工作目录
 */
public class CwdCommand implements Command {
    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {
        info = info.replace('/', File.separatorChar);
        String targetPath = ftpUserController.getCurrentChildPath();


        File path = new File(ftpUserController.getRootPath() + File.separator
                + ftpUserController.getCurrentChildPath() + File.separator + info);
        File rootPath = new File(ftpUserController.getRootPath() + File.separator + info);
        boolean flag;

        if (info.equals("..")) {
            int length = targetPath.lastIndexOf(File.separator);
            if (length != -1) {
                targetPath = targetPath.substring(0, targetPath.lastIndexOf(File.separator));
            } else {
                targetPath = "";
            }
            flag = true;
        } else if (!info.startsWith(".") && path.exists() && path.isDirectory()) {
            targetPath = targetPath + File.separator + info;
            flag = true;
        } else if (!info.startsWith(".") && rootPath.exists() && rootPath.isDirectory()) {
            targetPath = info;
            flag = true;
        } else {
            flag = false;
        }

        System.out.println(targetPath);
        ftpUserController.setCurrentChildPath(targetPath);
        System.out.println(ftpUserController.getCurrentChildPath());

        try {
            if (flag)
                writer.write("250 okay.\r\n");
            else
                writer.write("501 argument error.\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
