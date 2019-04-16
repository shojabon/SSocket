package SecureSocketChipV1.module;

import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCVCommand;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Arrays;

public class CommunicationsManager{
    private boolean communicationsOpen = false;
    private boolean communicationEncrypted = false;
    private Thread thread;

    SSCV1 main;
    public CommunicationsManager(SSCV1 main){
        this.main = main;
    }

    public boolean sendMessage(String message){
        if(!communicationsOpen)return false;
        if(communicationEncrypted){
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(main.getSocket().getOutputStream()));
                out.write(main.getEncryptionManager().encryptMessage(message));
                out.newLine();
                out.flush();
                return true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(main.getSocket().getOutputStream()));
                out.write(message);
                out.newLine();
                out.flush();
                return true;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public void openCommunication(){
        if(communicationsOpen) return;
        communicationsOpen = true;
        thread = new Thread(() -> {
            while(communicationsOpen){
                try {
                    InputStream is = main.getSocket().getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String data;
                    while ((data = br.readLine()) != null) {
                        System.out.println(data);
                        String[] a = data.split(" ");
                        main.getCommandManager().executeCommand(main, a[0], Arrays.copyOfRange(a, 1, a.length));
                    }
                }catch (IOException e){
                }
            }
        });
        thread.start();
    }

    public void closeCommunication(){
        if(!communicationsOpen) return;
        communicationsOpen = false;
    }

    public void setCommunicationEncrypted(boolean communicationEncrypted) {
        this.communicationEncrypted = communicationEncrypted;
    }
}
