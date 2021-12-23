package com.ATTAR.grafic;

import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.defaultes.FrameLimiter;
import org.joml.*;

import java.util.ArrayList;
import java.util.List;

public class Animation {


    private int i,frame,j;
    private String texName;
    private List<Integer> TexList = new ArrayList<>();
    private boolean looping;

    public int getFrames() {
        return TexList.size() ;
    }
    public Animation(boolean looping) {
        i=-1;
        frame =0;
        j=-1;
        this.looping = looping;

    }


    public void setTex(String name) {
        this.texName = name;
        this.TexList= AssetsPool.getTexList(name);

    }

    public int reset() {
        return 0;

    }
    private int NumOfLoops;
    public int update(int framePerSec, int frames) {
        i++;
        NumOfLoops = (int) Math.ceil(framePerSec/frames);
        if (i%(Math.floor(60/NumOfLoops))==0) {

            j++;
        }
        if(i>=60&& isLooping()) {
            i=0;
        }
        if (NumOfLoops==1) {

            if (i%Math.ceil(60/framePerSec) == 0 && i<60) {
                frame = (int) (i/Math.ceil(60/framePerSec));
                if (frame>=TexList.size()){
                    frame= TexList.size()-1;
                }

                return TexList.get(frame);
            }
            return (TexList.get(frame));
        }
        else {

            if (j>=TexList.size()){
                j= 0;
            }

            return TexList.get(j);
        }


    }

    public boolean isLooping() {
        return looping;
    }
}
