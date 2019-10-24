package command;


public class CommandController {

    public static Command useCommand(String command) {

        String choose = command.toUpperCase();
        switch (choose) {
            case "USER":
                return new UserCommand();
            case "PASS":
                return new PassCommand();
            case "LIST":
                return new ListCommand();
            case "RETR":
                return new RetrCommand();
            case "STOR":
                return new StorCommand();
            case "PORT":
                return new PortCommand();
            case "DELE":
                return new DeleCommand();
            case "RNFR":
                return new RnfrCommand();
            case "RNTO":
                return new RntoCommand();
            case "QUIT":
                return new QuitCommand();
            case "XPWD":
            case "PWD":
                return new PwdCommand();
            case "XMKD":
            case "MKD":
                return new MkdCommand();
            case "CWD":
                    return new CwdCommand();
            default:
        }
        return new NoCommand();
    }

}