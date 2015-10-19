package com.ballgame.game_objects;

import com.ballgame.servers.Game;
import java.util.concurrent.TimeUnit;

public class PlayerObject extends AbstractObject implements Runnable {

    private static final int MAX_SPEED = 10;
    /**
     * degrees of direction, value between 0 and 359, where:
     *      0       up
     *      90      right
     *      180     down
     *      270     left
     */
    private int angle = 0;
    /**
     * value between 1 and 10
     */
    private int speed = 5;
    private boolean ghost = true;
    
    public PlayerObject() {
        this.color = Game.getGameManager().getFristFreeColor();
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(100/this.speed);
                this.updateOffsets();
            }
        } catch(InterruptedException e) {
            
        }
    }
    
    private void updateOffsets() {
        ++this.x;
        ++this.y;
        this.changed = true;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = Math.min(speed, PlayerObject.MAX_SPEED);
        this.speed = Math.max(this.speed, 1);
    }

    public boolean isGhost() {
        return ghost;
    }

    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }
    
}
