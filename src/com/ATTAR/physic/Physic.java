package com.ATTAR.physic;

import com.ATTAR.defaultes.FrameLimiter;
import org.joml.Vector2f;

public class Physic {




    public boolean collidingY = true;
    private float g =25f;
    private float gVector =0;
    public void SetGravityVector(float gVector) {
        this.gVector = gVector;
    }
    public float getGravityVector() {
        return gVector;
    }
    public void update() {
        if (gVector>-28){

            gVector-=(float) (FrameLimiter.secsPerFrame * g);
        }

    }
    public float GravityVector() {


        if (!collidingY) {

            return gVector;
        }
        else {
            this.gVector = 0;
        }

        return 0;
    }
}
