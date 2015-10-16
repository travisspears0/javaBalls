package com.ballgame.data_handlers;

import com.ballgame.objects.User;

public interface CommandHandler {
    
    public void execute(User caller);
    public String getCommand();
    public CommandHandler getInstance();
    
}
