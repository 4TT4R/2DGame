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

    public static HashMap<Integer, Integer> blocks = new HashMap<>();

    public static HashMap<Integer, Integer> spikes = new HashMap<>();

    public static HashMap<String, List<Integer>> AnimatedTexture = new HashMap<>();

    public static HashMap<String, Integer> FPS = new HashMap<>();

    public static HashMap<String, Integer> Textures = new HashMap<>();

    public static Tiles getTile(int ID) {
        return Tiles.get(ID);
    }

    public static void addTile(Tiles tile){
        Tiles.add(tile);
    }

    public static int getFPS(String key) {return FPS.get(key);}

    public static Sound getSound(String soundFile) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            assert false : "Sound file not added '" + soundFile + "'";
        }

        return null;
    }



    public static void setTextures(HashMap<String, Integer> textures) {
        Textures = textures;
    }

    public static void addTexList(List<Integer> List, String value) {
        AnimatedTexture.put(value, List);
    }

    public static void addBlock(Integer key, Integer value) {
        blocks.put(key, value);
    }

    public static void addSpike(Integer key, Integer value) {
        spikes.put(key, value);
    }

    public static void addFPS(String key, Integer value) {
        FPS.put(key, value);
    }

    public static void addTexture(String key, Integer value) {
        Textures.put(key, value);
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
