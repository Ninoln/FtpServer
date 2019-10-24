package command;

import server.FTPUserController;

import java.io.Writer;

public interface Command {

    /**
     *
     * @param ftpUserController
     * @param writer
     * @param info
     */
    void result(FTPUserController ftpUserController, Writer writer, String info);
}
