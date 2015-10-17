package com.ballgame.data_handlers;

import com.ballgame.exceptions.UserDataException;
import com.ballgame.objects.User;
import com.ballgame.servers.Game;
import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;
import org.json.JSONObject;

public class CommandHandlersManager {
    
    private final Map<String, CommandHandler> commandHanlders = new HashMap<>();
    
    public CommandHandlersManager() {
        this.commandHanlders.put("help", new HelpCommandHandler());
        this.commandHanlders.put("pm", new PrivCommandHandler());
        this.commandHanlders.put("name", new NameCommandHandler());
        this.commandHanlders.put("play", new PlayCommandHandler());
    }
    
    public void handle(User caller, String data, Map<Session, User> users) {
        String[] dataArray = data.split(" ");
        String command = dataArray[0];
        String[] arguments = new String[dataArray.length-1];
        for( int i=1 ; i<dataArray.length ; ++i ) {
            arguments[i-1] = dataArray[i];
        }
        try {
            if( !this.commandHanlders.containsKey(command) ) {
                throw new UserDataException("no such command");
            }
            this.commandHanlders.get(command).execute(caller,arguments);
        } catch(UserDataException e) {
            JSONObject ob = new JSONObject();
            ob.put("type", "messageFromServer");
            ob.put("message", e.getMessage());
            Game.getDataHandlerManager().sendDataToOneUser(caller, ob);
        }
    }
    
}
