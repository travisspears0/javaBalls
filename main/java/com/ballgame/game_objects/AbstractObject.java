package com.ballgame.game_objects;

public abstract class AbstractObject implements GameObject {
    
    protected int x;
    protected int y;
    protected int size;
    protected GameObject.COLOR color;
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
        String resultColor = color.toString().replace("_", "#");
        return resultColor;
    }

    @Override
    public void setColor(GameObject.COLOR color) {
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
    
}
