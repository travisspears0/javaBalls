package com.ballgame.objects;

import com.ballgame.game_objects.GameObject;
import com.ballgame.game_objects.PlayerObject;

public class Player {
    
    private final PlayerObject playerObject = new PlayerObject(GameObject.SHAPE.CIRCLE);

    public PlayerObject getPlayerObject() {
        return playerObject;
    }
    
}
