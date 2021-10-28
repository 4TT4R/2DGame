package com.ATTAR.defaultes;

public class FrameLimiter {




    public static double secsPerFrame = 1.0d / 60.0d;
    public static float timeStarted = System.nanoTime();
    public static float getTime() { return (float)((System.nanoTime() - timeStarted) * 1E-9); }

}
