package com.ballgame.game_objects;

public class ObjectFactory {
    
    public static GameObject getPlayerObject() {
        return new PlayerObject();
    }
    
    public static GameObject getBonusObject() {
        return new BonusObject();
    }
    
}
