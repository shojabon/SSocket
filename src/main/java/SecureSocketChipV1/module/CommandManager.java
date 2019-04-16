package SecureSocketChipV1.module;

import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCVCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    SSCV1 main;

    public CommandManager(SSCV1 main){
        this.main = main;
    }

    public void executeCommand(SSCV1 sscv1, String command, String[] args){
        if(main.getCommandHandler() == null) return;
        main.getBaseCommandHandler().onCommand(sscv1, command, args);
        main.getCommandHandler().onCommand(sscv1, command, args); }


}
