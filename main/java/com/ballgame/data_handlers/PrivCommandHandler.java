package com.ballgame.data_handlers;

import com.ballgame.exceptions.UserDataException;
import com.ballgame.objects.User;
import com.ballgame.servers.Game;
import org.json.JSONObject;

public class PrivCommandHandler implements CommandHandler {
    
    @Override
    public void execute(User caller) throws UserDataException {
        throw new UserDataException("no arguments provided");
    }

    @Override
    public void execute(User caller, String[] args) throws UserDataException {
        if( args.length < 2 ) {
            throw new UserDataException("at least 2 arguments expected(user name and message), got "
                + args.length);
        }
        String userName = args[0];
        String message = "";
        User user = Game.getUsersMediator().getUserByName(userName);
        if( user == null ) {
            throw new UserDataException("there is no user named " + userName);
        }
        for( int i=1 ; i<args.length ; ++i ) {
            message += args[i] + " ";
        }
        message = message.substring(0,message.length()-1);
        message = Game.getCurrentTime() + "{PM}" + caller.getName() + ": " + message; 
        JSONObject ob = new JSONObject();
        ob.put("type", "privateMessage");
        ob.put("color", user.getColor());
        ob.put("message", message);
        Game.getDataHandlerManager().sendDataToOneUser(user, ob);
    }

    @Override
    public String getCommand() {
        return "pm";
    }
    
}
