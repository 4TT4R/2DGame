package com.ATTAR.defaultes;

import org.joml.*;

import java.util.Timer;

public class StopWatch {



    private Vector4f time;
    public StopWatch(Vector4f time) {
        this.time = time;

    }


    public void addTime(){

       time.set(time.x, time.y, time.z, (float) (time.w+10f/6f));


        if (time.w>=100) {
            time.set(time.x, time.y, time.z+1, time.w-100);
        }
        if (time.z>=60) {
            time.set(time.x, time.y+1, time.z-60, time.w);
        }
        if (time.y>=60) {
            time.set(time.x+1, time.y-60, time.z, time.w);
        }

    }
    public Vector4f getTime() {
        return time;
    }
}
