package com.ATTAR.defaultes;

import com.ATTAR.Sound.*;

import java.io.*;
import java.util.*;

public class AssetsPool {

    private static Map<String, Sound> sounds = new HashMap<>();

    public static Collection<Sound> getAllSounds() {
        return sounds.values();
    }

    public static Sound getSound(String soundFile) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            assert false : "Sound file not added '" + soundFile + "'";
        }

        return null;
    }

    public static Sound addSound(String soundFile, boolean loops) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            Sound sound = new Sound(file.getAbsolutePath(), loops);
            AssetsPool.sounds.put(file.getAbsolutePath(), sound);
            return sound;
        }
    }
}
