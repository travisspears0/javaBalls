package com.ballgame.data_handlers;

import com.ballgame.objects.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.websocket.Session;
import org.json.JSONObject;

public class CommandHandlersManager {
    
    private final List<CommandHandler> commandHanlders = new ArrayList<>();
    private final DataHandlerManager dataHandlerManager;
    
    public CommandHandlersManager(DataHandlerManager dataHandlerManager) {
        this.dataHandlerManager = dataHandlerManager;
    }
    
    public void handle(User caller, String data, Map<Session, User> users) {
        String command = data.split(" ")[0];
        try {
            switch( command ) {
                case "help": {
                    this.dataHandlerManager.sendDataToOneUser(caller,
                        "help hereeeee<br/>asdasddas<br/>");
                    break;
                }
                case "name": {
                    try {
                        String newName = data.split(" ")[1];
                        caller.setName(newName);
                        JSONObject ob = new JSONObject();
                        ob.put("type", "userChangedName");
                        ob.put("name", newName);
                        ob.put("id", caller.getId());
                        this.dataHandlerManager.sendDataToManyUsers(ob, users);
                    } catch(ArrayIndexOutOfBoundsException e) {
                        throw new UserDataException("no name provided");
                    }
                    break;
                }
                default: {
                    throw new UserDataException("no such command");
                }
            }
        } catch(UserDataException e) {
            this.dataHandlerManager.sendDataToOneUser(caller, e.getMessage());
        }
    }
    
    private class UserDataException extends Exception {
        public UserDataException(String msg) {
            super(msg);
        }
    }
    
}
