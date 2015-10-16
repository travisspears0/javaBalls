package com.ballgame.data_handlers;

import com.ballgame.objects.User;
import java.util.ArrayList;
import java.util.List;

public class CommandHandlersManager {
    
    private final List<CommandHandler> commandHanlders = new ArrayList<>();
    
    public CommandHandlersManager() {
        
    }
    
    public void handle(User caller, String command) {
        //switch...commandHandlers.get(n).execute()...
    }
    
}
