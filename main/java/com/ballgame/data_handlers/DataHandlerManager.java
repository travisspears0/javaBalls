package com.ballgame.data_handlers;

import com.ballgame.objects.User;
import com.ballgame.servers.Game;
import java.io.IOException;
import java.util.Map;
import javax.websocket.Session;
import org.json.JSONObject;

public class DataHandlerManager {
    
    public void sendDataToManyUsers(JSONObject jsonObjectToSend, Session author) {
        Map<Session,User> users = Game.getUsers();
        for (Map.Entry pair : users.entrySet()) {
            try {
                Session session = (Session)pair.getKey();
                if( session == author ) {
                    JSONObject ob = jsonObjectToSend;
                    ob.put("dataReturned", "true");
                    session.getBasicRemote().sendText(ob.toString());
                    continue;
                }
                session.getBasicRemote().sendText(jsonObjectToSend.toString());
            } catch(IOException e) {
                User user = (User)pair.getValue();
                System.out.println("DataHandlerManager error: could not send message to user " + 
                    user.getName());
            }
        }
    }
    
    public void sendDataToManyUsers(JSONObject jsonObjectToSend) {
        this.sendDataToManyUsers(jsonObjectToSend, null);
    }
    
    public void sendDataToOneUser(User receiver, JSONObject objectToSend) {
        try {
            receiver.getSession().getBasicRemote().sendText(objectToSend.toString());
        } catch(IOException e) {
            System.out.println("CommandHandlersManager error: could not send response to user "
                + receiver.getName());
        }
    }
    
    public void handleMessageData(Session source, String data) {
        String currentTime = Game.getCurrentTime();
        User author = Game.getUsers().get(source);
        String message = currentTime + author.getName() + ": " + data;
        JSONObject ob = new JSONObject();
        ob.put("type", "message");
        ob.put("color", author.getColor());
        ob.put("message", message);
        this.sendDataToManyUsers(ob);
    }
    
    public void handleAddUserData(int id, String name, Session newSession) {
        JSONObject ob = new JSONObject();
        ob.put("type", "addUser");
        ob.put("id", id);
        ob.put("name", name);
        this.sendDataToManyUsers(ob, newSession);
        this.sendUsersList(newSession);
    }
    
    public void handleRemoveUserData(int id, Session excludedSession) {
        JSONObject ob = new JSONObject();
        ob.put("type", "removeUser");
        ob.put("id", id);
        Game.getUsers().remove(excludedSession);
        this.sendDataToManyUsers(ob);
    }
    
    private void sendUsersList(Session newSession) {
        Map<Session,User> users = Game.getUsers();
        User newUser = users.get(newSession);
        JSONObject groupOb = new JSONObject();
        for (Map.Entry pair : users.entrySet()) {
            User user = (User)pair.getValue();
            if( user == newUser ) {
                continue;
            }
            JSONObject userOb = new JSONObject();
            userOb.put("id",user.getId());
            userOb.put("name",user.getName());
            userOb.put("color",user.getColor());
            groupOb.put(user.getId()+"",userOb.toString());
        }
        JSONObject resultOb = new JSONObject();
        resultOb.put("type", "usersList");
        resultOb.put("users", groupOb);
        try {
            newSession.getBasicRemote().sendText(resultOb.toString());
        } catch(IOException e) {
            System.out.println(Game.getCurrentTime() + 
                "could not send users list to " + newUser.getName());
        }
    }
    
}
