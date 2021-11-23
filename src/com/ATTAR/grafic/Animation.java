package com.ATTAR.grafic;

import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.defaultes.FrameLimiter;
import org.joml.*;

import java.util.ArrayList;
import java.util.List;

public class Animation {


    private int i;
    private List<Integer> TexList = new ArrayList<>();
    private boolean looping;

    public int getFrames() {
        return TexList.size() ;
    }
    public Animation(boolean looping) {
        this.looping = looping;

    }


    public int getTex(int i, String name) {
        TexList= AssetsPool.getTexList(name);
        return TexList.get(i);
    }

    public int reset() {
        return 0;

    }
    public void update() {

    }

    public boolean isLooping() {
        return looping;
    }
}
