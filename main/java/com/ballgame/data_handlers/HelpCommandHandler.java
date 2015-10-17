package com.ballgame.data_handlers;

import com.ballgame.objects.User;
import com.ballgame.servers.Game;
import org.json.JSONArray;
import org.json.JSONObject;

public class HelpCommandHandler implements CommandHandler {
    
    @Override
    public void execute(User caller) {
        String[] messageLines = {
            "WELCOME TO HELP",
            "TO SET YOUR NAME, TYPE: /name *new name*",
            "TO SEND PRIVATE MESSAGE, TYPE: /pm *receiver name* *message*",
            "TO START PLAYING, TYPE: /play",
            "ENJOY :)"
        } ;
        JSONObject ob = new JSONObject();
        ob.put("type", "messageFromServer");
            JSONArray messageArray = new JSONArray();
            for( String msg : messageLines ) {
                messageArray.put(msg);
            }
        ob.put("message", messageArray);
        Game.getDataHandlerManager().sendDataToOneUser(caller, ob);
    }

    @Override
    public void execute(User caller, String[] args) {
        this.execute(caller);
    }

    @Override
    public String getCommand() {
        return "help";
    }
    
}
