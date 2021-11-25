package com.ATTAR.grafic;

import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.defaultes.FrameLimiter;
import org.joml.*;

import java.util.ArrayList;
import java.util.List;

public class Animation {


    private int i,frame;
    private List<Integer> TexList = new ArrayList<>();
    private boolean looping;

    public int getFrames() {
        return TexList.size() ;
    }
    public Animation(boolean looping) {
        i=-1;
        frame =0;
        this.looping = looping;

    }


    public void setTex(String name) {
        
        this.TexList= AssetsPool.getTexList(name);

    }

    public int reset() {
        return 0;

    }
    public int update(int framePerSec) {
        i++;
        if(i>=60&& isLooping()) {
            i=0;
        }
        if (i%Math.ceil(60/framePerSec) == 0 && i<60) {
            frame = (int) (i/Math.ceil(60/framePerSec));
            return TexList.get(frame);
        }
        return TexList.get(frame);

    }

    public boolean isLooping() {
        return looping;
    }
}
