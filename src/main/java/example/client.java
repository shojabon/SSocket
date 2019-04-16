package example;

import SecureSocketChipV1.SSCV1;

import java.io.IOException;
import java.net.Socket;

public class client {

    public static void main(String[] args){
        try {
            Socket socket = new Socket("127.0.0.1", 10000);
            SSCV1 serv = new SSCV1(socket);
            serv.getProtocolManager().exchangeKeysProtocol();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
