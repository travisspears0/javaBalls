package com.ballgame.data_handlers;

import com.ballgame.exceptions.UserDataException;
import com.ballgame.objects.User;
import com.ballgame.servers.Game;
import org.json.JSONObject;

public class PlayCommandHandler implements CommandHandler {
    
    @Override
    public void execute(User caller) throws UserDataException {
        this.execute(caller, null);
    }

    @Override
    public void execute(User caller, String[] args) throws UserDataException {
        boolean joined = Game.getGameNotifierManager().addUser(caller);
        JSONObject ob = new JSONObject();
        if( joined ) {
            ob.put("type", "joinedGame");
        } else {
            ob.put("type", "gameFull");
        }
        Game.getDataHandlerManager().sendDataToOneUser(caller, ob);
    }

    @Override
    public String getCommand() {
        return "play";
    }
    
}
