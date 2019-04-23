package SecureSocketChipV1;

import SecureSocketChipV1.Enums.SSCV1Mode;
import SecureSocketChipV1.EventClasses.SSCClientConnectEvent;
import SecureSocketChipV1.interfaces.SSCEvent;
import SecureSocketChipV1.interfaces.SSCVCommand;
import SecureSocketChipV1.module.BaseCommand.SendKeyCommand;
import SecureSocketChipV1.module.CommandManager;
import SecureSocketChipV1.module.CommunicationsManager;
import SecureSocketChipV1.module.EncryptionManager;
import SecureSocketChipV1.module.ProtocolManager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SSCV1 implements SSCVCommand{

    Socket socket;
    CommunicationsManager communicationsManager;
    EncryptionManager encryptionManager;

    ProtocolManager protocolManager;

    CommandManager commandManager;
    List<SSCVCommand> commandHandler = new ArrayList<>();
    List<SSCEvent> eventHandler = new ArrayList<>();

    SSCVCommand baseCommandHandler;

    SSCV1Mode mode;

    public SSCV1(Socket socket, SSCV1Mode mode){
        this.socket = socket;
        this.mode = mode;
        this.communicationsManager = new CommunicationsManager(this);
        this.communicationsManager.openCommunication();
        this.commandManager = new CommandManager(this);
        this.encryptionManager = new EncryptionManager(this);
        this.baseCommandHandler = this;
        this.protocolManager = new ProtocolManager(this);
    }

    public SSCV1(Socket socket, SSCV1Mode mode, SSCEvent event) {
        this.socket = socket;
        this.mode = mode;
        this.communicationsManager = new CommunicationsManager(this);
        this.communicationsManager.openCommunication();
        this.commandManager = new CommandManager(this);
        this.encryptionManager = new EncryptionManager(this);
        this.baseCommandHandler = this;
        this.protocolManager = new ProtocolManager(this);
        eventHandler.add(event);
        for (SSCEvent even : eventHandler) {
            even.onClientConnect(new SSCClientConnectEvent(this));
        }
    }

    @Override
    public void onCommand(SSCV1 sscv1, String command, String[] args, UUID uuid) {
        if(args.length == 0){
            if(command.equalsIgnoreCase("PING")){
                getCom().sendMessage("PONG");
                return;
            }
        }
        if(args.length == 1){
            if(command.equalsIgnoreCase("SK")){
                new SendKeyCommand(this, command, args);
                return;
            }
        }
    }


    public CommandManager getCommandManager() {
        return commandManager;
    }

    public List<SSCVCommand> getCommandHandler() {
        return commandHandler;
    }

    public List<SSCEvent> getEventHandler() {
        return eventHandler;
    }

    public void registerEvent(SSCEvent event){
        eventHandler.add(event);
    }

    public void addCommandHandler(SSCVCommand commands){
        this.commandHandler.add(commands);
    }

    public Socket getSocket() {
        return socket;
    }

    public CommunicationsManager getCom() {
        return communicationsManager;
    }

    public SSCV1Mode getMode() {
        return mode;
    }

    public EncryptionManager getEncryptionManager() {
        return encryptionManager;
    }

    public SSCVCommand getBaseCommandHandler() {
        return baseCommandHandler;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
