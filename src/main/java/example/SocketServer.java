package example;

import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCVCommand;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer{
    public static void main(String[] args){
        Server server = new Server();
    }

}

class Server implements SSCVCommand {
    ServerSocket socket;
    public Server(){
        try {
            socket = new ServerSocket(10000);
            while (true){
                try {
                    SSCV1 sscv1 = new SSCV1(socket.accept());
                    sscv1.setCommandHandler(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCommand(SSCV1 sscv1, String command, String[] args) {
        if(command.equalsIgnoreCase("test")){
            System.out.println("test");
        }
    }
}
