package com.ballgame.game;

import com.ballgame.servers.Game;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameLoop implements Runnable {
    
    private final static GameLoop gameLoop = new GameLoop();
    
    private GameLoop() {
    }
    
    @Override
    public void run() {
        Random r = new Random();
        try {
            while( !Thread.interrupted() ) {
                Game.getGameManager().notifyUsers();
                System.out.println("loop " + r.nextInt());
                TimeUnit.MILLISECONDS.sleep(50);
            }
        } catch(InterruptedException e) {
            System.out.println("game ended");
        } finally {
            this.clear();
        }
    }
    
    private void clear() {
        
    }
    
    public static GameLoop getInstance() {
        return GameLoop.gameLoop;
    }
    
}
