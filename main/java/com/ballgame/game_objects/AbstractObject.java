package com.ballgame.game_objects;

public abstract class AbstractObject implements GameObject {
    
    /*
    public static final String[] colors = {
        "#FF0000",  //red
        "#00FF00",  //green
        "#0000FF",  //blue
        "#990099",  //purple
        "#FF9900",  //orange
        "#996600",  //brown
        "#FF0099",  //pink
        "#FFFF33"   //yellow
    };
    */
    
    protected int x;
    protected int y;
    protected int size;
    protected final SHAPE shape;
    protected GameObject.COLOR color;
    
    public AbstractObject(SHAPE shape) {
        this.shape = shape;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
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
    
    
    
}
