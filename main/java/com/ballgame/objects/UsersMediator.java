package com.ballgame.objects;

import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;

public class UsersMediator {
    
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
    }
    
    public boolean removeUser(User user) {
        return this.users.remove(user);
    }
    
    public User getUserBySession(Session session) {
        for( User user : this.users ) {
            if( user.getSession() == session ) {
                return user;
            }
        }
        return null;
    }
    
    public User getUserByName(String name) {
        for( User user : this.users ) {
            if( user.getName().equals(name) ) {
                return user;
            }
        }
        return null;
    }
    
    public void notifyAll(User source) {
        for( User player : this.users ) {
            if( player == source ) {
                continue;
            }
            
        }
    }
    
    public List<User> getUsers() {
        List<User> copy = new ArrayList<>(this.users);
        return copy;
    }
    
}
