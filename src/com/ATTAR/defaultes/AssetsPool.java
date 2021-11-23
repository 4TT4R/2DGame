package com.ATTAR.defaultes;

import com.ATTAR.Sound.*;
import com.ATTAR.grafic.Texture;
import com.ATTAR.objects.Tiles;

import java.io.*;
import java.util.*;

public class AssetsPool {

    private static Map<String, Sound> sounds = new HashMap<>();

    public static Collection<Sound> getAllSounds() {
        return sounds.values();
    }
    private static List<Tiles> Tiles = new ArrayList<>();
    public static HashMap<String, List<Integer>> AnimatedTexture = new HashMap<>();
    public static Tiles getTile(int ID) {
        return Tiles.get(ID);
    }
    public static void addTile(Tiles tile){
        Tiles.add(tile);
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
    public static void addTexList(List<Integer> List, String value) {
        AnimatedTexture.put(value, List);
    }
    public static List<Integer> getTexList(String value) {
        return AnimatedTexture.get(value);
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
