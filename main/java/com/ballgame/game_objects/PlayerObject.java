package com.ballgame.game_objects;

public class PlayerObject extends AbstractObject {

    private int angle = 0;
    
    public PlayerObject(SHAPE shape) {
        super(shape);
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
    
}
