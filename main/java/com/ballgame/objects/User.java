package com.ballgame.objects;

import com.ballgame.game_objects.PlayerObject;
import javax.websocket.Session;

public class User {
    
    private static int CURRENT_ID=0;
    private final int id = User.CURRENT_ID++;
    
    private String name = "user #" + this.id;
    private PlayerObject playerObject;
    private final UsersMediator mediator;
    private final Session session;
    private boolean inGame = false;
    
    public User(UsersMediator mediator, Session session) {
        this.mediator = mediator;
        this.mediator.addUser(this);
        this.session = session;
    }
    
    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    
    public String getColor() {
        if( this.playerObject == null ) {
            return "#FFFFFF";
        }
        return this.playerObject.getColor();
    }

    public Session getSession() {
        return session;
    }

    public PlayerObject getPlayerObject() {
        return playerObject;
    }

    public void assignPlayerObject() {
        this.playerObject = new PlayerObject();
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
