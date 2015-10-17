package com.ballgame.game;

import com.ballgame.game_objects.GameObject;
import com.ballgame.objects.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class GameManager {
    
    public static final int MAX_USERS = 8;
    
    private final List<User> users = new ArrayList<>();
    private final List<GameObject> gameObjects = new ArrayList<>();
    private Thread gameLoopThread = null;
    
    public void notifyUsers() {
        JSONArray jsonArray = new JSONArray();
        for( GameObject ob : this.gameObjects ) {
            if( ob.isChanged() ) {
                jsonArray.put(ob);
                ob.setChanged(false);
            }
        }
        for( User user : this.users ) {
            try {
                user.getSession().getBasicRemote().sendText(jsonArray.toString());
            } catch(IOException e) {
                System.out.println("could not send game data to user " + user.getName());
            }
        }
    }
    
    public void addGameObject(GameObject object) {
        this.gameObjects.add(object);
    }
    
    public boolean addUser(User user) {
        if( this.users.size() >= MAX_USERS ) {
            return false;
        }
        if( this.gameLoopThread == null ) {
            this.gameLoopThread = new Thread(new GameLoop());
        }
        this.users.add(user);
        return true;
    }
    
    public void removeUser(User user) {
        this.users.remove(user);
        if( this.users.isEmpty() ) {
            this.gameLoopThread.interrupt();
            this.gameLoopThread = null;
        }
    }
    
}
