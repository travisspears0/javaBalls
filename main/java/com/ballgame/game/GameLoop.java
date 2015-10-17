package com.ballgame.game;

import com.ballgame.servers.Game;
import java.util.concurrent.TimeUnit;

public class GameLoop implements Runnable {
    
    @Override
    public void run() {
        try {
            while( !Thread.interrupted() ) {
                Game.getGameNotifierManager().notifyUsers();
                TimeUnit.MILLISECONDS.sleep(50);
            }
        } catch(InterruptedException e) {
            
        }
    }
    
}
