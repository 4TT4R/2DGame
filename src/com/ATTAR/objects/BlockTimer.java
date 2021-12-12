package com.ATTAR.objects;

import org.joml.Vector2f;

public class BlockTimer {



    private Vector2f key;
    private Tiles tile;
    private double time, time_plus, current_time;
    private boolean end, change;

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public boolean isEnd(){return end;}
    public void setEnd(boolean end) {this.end = end;}
    public Vector2f getKey() {
        return key;
    }

    public Tiles getTile() {
        return tile;
    }

    public BlockTimer(Vector2f key, Tiles tile, double time) {
        this.key = key;
        this.tile = tile;
        this.time = time;
        end = false;
        change=false;
        time_plus = 1d/60d;
        current_time = 0d;
    }
    public void update() {
        current_time += time_plus;
        if (current_time>=time) {
            end = true;
        }

    }
    public void updateR() {
        current_time += time_plus;
        if (current_time>=time) {
            change = true;
        }

    }

}
