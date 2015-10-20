package com.ballgame.game_objects;

import com.ballgame.servers.Game;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public class PlayerObject extends AbstractObject implements Runnable {

    private static final Random random = new Random();
    private static final int MAX_SPEED = 10;
    /**
     * degrees of direction, value between 0 and 359, where:
     *      0       up
     *      90      right
     *      180     down
     *      270     left
     */
    private int angle = random.nextInt(360);
    /**
     * value between 1 and 10
     */
    private int speed = 5;
    private boolean ghost = true;
    private Thread thread;
    private final int userId;
    private final Map<String, String> cacheMap = new HashMap<>();
    
    public PlayerObject(int userId) {
        this.userId = userId;
        this.color = Game.getGameManager().getFristFreeColor();
        this.thread = new Thread(this);
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
                this.update();
                System.out.println("player thread");
            }
        } catch(InterruptedException e) {
            System.out.println("player done");
        }
    }
    
    private void update() {
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
    
    public void startThread() {
        this.thread.start();
    }
    
    public void interruptThread() {
        this.thread.interrupt();
    }
    
    @Override
    public JSONObject getChangedStatus() {
        JSONObject ob = new JSONObject();
        if( !this.color.equals(this.cacheMap.get("color")) ) {
            this.cacheMap.put("color", this.color);
            ob.put("color", this.color);
        }
        if( !Integer.toString(this.x).equals(this.cacheMap.get("x")) ) {
            this.cacheMap.put("x", this.x+"");
            ob.put("x", this.x);
        }
        if( !Integer.toString(this.y).equals(this.cacheMap.get("y")) ) {
            this.cacheMap.put("y", this.y+"");
            ob.put("y", this.y);
        }
        if( !Integer.toString(this.angle).equals(this.cacheMap.get("angle")) ) {
            this.cacheMap.put("angle", this.angle+"");
            ob.put("angle", this.angle);
        }
        if( !Integer.toString(this.size).equals(this.cacheMap.get("size")) ) {
            this.cacheMap.put("size", this.size+"");
            ob.put("size", this.size);
        }
        return ob;
    }
    
}
