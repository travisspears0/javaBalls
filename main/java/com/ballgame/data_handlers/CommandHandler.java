package com.ballgame.data_handlers;

import com.ballgame.exceptions.UserDataException;
import com.ballgame.objects.User;

public interface CommandHandler {
    
    public void execute(User caller) throws UserDataException;
    public void execute(User caller, String[] args) throws UserDataException;
    public String getCommand();
    
}
