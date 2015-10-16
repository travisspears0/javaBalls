package com.ballgame.game_objects;

public class ObjectFactory {
    
    public static GameObject getPlayerObject(GameObject.SHAPE shape) {
        return new PlayerObject(shape);
    }
    
    public static GameObject getBonusObject(GameObject.SHAPE shape) {
        return new BonusObject(shape);
    }
    
}
