package com.ballgame.data_handlers;

import com.ballgame.exceptions.UserDataException;
import com.ballgame.objects.User;
import com.ballgame.servers.Game;
import org.json.JSONObject;

public class NameCommandHandler implements CommandHandler {
    
    @Override
    public void execute(User caller) throws UserDataException {
        throw new UserDataException("no name specified");
    }

    @Override
    public void execute(User caller, String[] args) throws UserDataException {
        if( args.length != 1 ) {
            throw new UserDataException("expected 1 argument, got " + args.length);
        }
        String name = args[0];
        if( Game.getUsersMediator().getUserByName(name) != null ) {
            throw new UserDataException("the name " + name + " seems to be taken");
        }
        caller.setName(name);
        JSONObject ob = new JSONObject();
        ob.put("type", "userChangedName");
        ob.put("id", caller.getId());
        ob.put("name", name);
        Game.getDataHandlerManager().sendDataToManyUsers(ob);
    }

    @Override
    public String getCommand() {
        return "name";
    }
    
}
