package com.ballgame.game_objects;

import org.json.JSONObject;

public abstract class AbstractObject implements GameObject {
    
    protected int x=0;
    protected int y=0;
    /**
     * a value between 10 and 30(may be changed) which determines the radius of the ball
     * representing object
     */
    protected int size = 20 ;
    protected String color;
    protected boolean  changed = false;

    @Override
    public synchronized int getX() {
        return x;
    }

    @Override
    public synchronized void setX(int x) {
        this.x = x;
    }

    @Override
    public synchronized int getY() {
        return y;
    }

    @Override
    public synchronized void setY(int y) {
        this.y = y;
    }

    @Override
    public synchronized int getSize() {
        return size;
    }

    @Override
    public synchronized void setSize(int size) {
        this.size = size;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public synchronized boolean isChanged() {
        return changed;
    }

    @Override
    public synchronized void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    @Override
    public abstract JSONObject getChangedStatus();
    
}
