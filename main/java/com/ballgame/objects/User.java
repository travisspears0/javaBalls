package com.ballgame.objects;

import javax.websocket.Session;

public class User {
    
    private static int CURRENT_ID=0;
    private final int id = User.CURRENT_ID++;
    
    private String name = "user #" + this.id;
    private Player player;
    private final UsersMediator mediator;
    private final Session session;
    
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        if( this.player == null ) {
            return "#FFFFFF";
        }
        return this.player.getPlayerObject().getColor();
    }

    public Session getSession() {
        return session;
    }
}
