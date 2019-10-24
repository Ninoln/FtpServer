package command;

import server.FTPUserController;

import java.io.IOException;
import java.io.Writer;

/**
 * 设置端口
 */
public class PortCommand implements Command {
    @Override
    public void result(FTPUserController ftpUserController, Writer writer, String info) {

        try {

            String[] data =  info.split(",");
            String ip = data[0]+"."+data[1]+"."+data[2]+"."+data[3];
            int port = 256*Integer.parseInt(data[4])+Integer.parseInt(data[5]);


            ftpUserController.setUserHost(ip);
            ftpUserController.setUserPort(port);

            writer.write("200 PORT Command successful.");
            writer.write("\r\n");
            writer.flush();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}

