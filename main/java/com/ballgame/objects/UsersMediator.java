package com.ballgame.objects;

import java.util.ArrayList;
import java.util.List;

public class UsersMediator {
    
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
    }
    
    public boolean removeUser(User user) {
        return this.users.remove(user);
    }
    
    public void notifyAll(User source) {
        for( User player : this.users ) {
            if( player == source ) {
                continue;
            }
            
        }
    }
    
}
