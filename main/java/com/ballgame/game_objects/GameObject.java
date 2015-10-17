package com.ballgame.game_objects;

public interface GameObject {
    /*
    public enum SHAPE {
        CIRCLE,
        SQUARE
    }*/
    
    public enum COLOR {
        _FF0000,//red
        _00FF00,//green
        _0000FF,//blue
        _990099,//purple
        _FF9900,//orange
        _996600,//brown
        _FF0099,//pink
        _FFFF33,//yellow
        _FFFFFF,//white
        _000000//black
    }
    
    public int getX();
    public void setX(int x);
    public int getY();
    public void setY(int y);
    public int getSize();
    public void setSize(int size);
    public String getColor();
    public void setColor(COLOR color);
    public void setChanged(boolean changed);
    public boolean isChanged();
    
}
