package com.ballgame.data_handlers;

import com.ballgame.objects.User;

public class HelpCommandHandler implements CommandHandler {
    
    private final static HelpCommandHandler instance = new HelpCommandHandler();
    
    private HelpCommandHandler(){}
    
    @Override
    public CommandHandler getInstance() {
        return HelpCommandHandler.instance;
    }

    @Override
    public void execute(User caller) {
        //do stuff...send response to user.getSession...
    }

    @Override
    public String getCommand() {
        return "help";
    }
    
}
