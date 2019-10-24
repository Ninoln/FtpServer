package command;

import server.FTPUserController;

import java.io.IOException;
import java.io.Writer;

/**
 * 未实现命令
 */
public class NoCommand implements Command {

    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {

        try {
            writer.write("502 has not achieve\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
