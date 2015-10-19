package com.ballgame.game;

import com.ballgame.game_objects.GameObject;
import com.ballgame.objects.User;
import com.ballgame.servers.Game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class GameManager {
    
    private static final String[] playerColors = {
        "#FF0000",//red
        "#00FF00",//green
        "#0000FF",//blue
        "#990099",//purple
        "#FF9900",//orange
        "#996600",//brown
        "#FF0099",//pink
        "#FFFF33",//yellow
    };
    
    public static final int MAX_USERS = 8;
    
    private final List<User> users = new ArrayList<>();
    private final List<GameObject> gameObjects = new ArrayList<>();
    private Thread gameLoopThread = null;
    private boolean[] userColorsTaken = new boolean[GameManager.playerColors.length];
    
    public GameManager() {
        for( int i=0 ; i<this.userColorsTaken.length ; ++i ) {
            this.userColorsTaken[i] = false;
        }
    }
    
    public void notifyUsers() {
        JSONObject objectToSend = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for( GameObject ob : this.gameObjects ) {
            if( ob.isChanged() ) {
                jsonArray.put(ob);
                ob.setChanged(false);
            }
        }
        objectToSend.put("type", "gameStatus");
        objectToSend.put("data", jsonArray);
        for( User user : this.users ) {
            try {
                user.getSession().getBasicRemote().sendText(objectToSend.toString());
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
            this.gameLoopThread = new Thread(GameLoop.getInstance());
            this.gameLoopThread.start();
        }
        this.users.add(user);
        user.assignPlayerObject();
        user.setInGame(true);
        return true;
    }
    
    public void removeUser(User user) {
        boolean removed = this.users.remove(user);
        if( removed ) {
            this.freeColor(user.getColor());
            if( this.users.isEmpty() ) {
                this.gameLoopThread.interrupt();
                this.gameLoopThread = null;
            }
        }
    }

    public List<User> getUsers() {
        List<User> copy = new ArrayList<>(this.users);
        return copy;
    }
    
    public String getFristFreeColor() {
        for( int i=0 ; i<GameManager.playerColors.length ; ++i ) {
            if( !this.userColorsTaken[i] ) {
                this.userColorsTaken[i] = true;
                return GameManager.playerColors[i];
            }
        }
        return null;
    }
    
    private void freeColor(String color) {
        int index = -1;
        for( int i=0 ; i<GameManager.playerColors.length ; ++i ) {
            if( color.equals(GameManager.playerColors[i]) ) {
                index = i;
                break;
            }
        }
        if( index != -1 ) {
            this.userColorsTaken[index] = false;
        }
    }
    
}
