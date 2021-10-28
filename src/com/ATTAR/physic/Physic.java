package com.ATTAR.physic;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Physic {




    private Vec2 gravity = new Vec2(0,-10);
    private World world = new World(gravity);
    private float physicTime = 0.0f;
    private float physicTimeStep = 1.0f /60.0f;
    private int velocityInterations = 8;
    private int positionInterations = 3;

    public void update(float dt){
        physicTime += dt;
        if (physicTime >= 0.0f) {
            physicTime -= physicTimeStep;
            world.step(physicTime, velocityInterations, positionInterations);
        }
    }


}
