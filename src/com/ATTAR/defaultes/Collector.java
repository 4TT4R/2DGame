package com.ATTAR.defaultes;

import com.ATTAR.grafic.Texture;
import com.ATTAR.objects.Tiles;
import com.ATTAR.scenes.Scene;
import org.joml.*;

import java.util.HashMap;

public class Collector {





    private static int Level, Ceil, Floor;
    private static Vector2f PlayerPos;
    private static Vector4f Time;
    private static Vector2f CursorPos = new Vector2f(), winSize = new Vector2f(), CamPos = new Vector2f() ;
    private static HashMap<Vector2f, Tiles> BlockMap = new HashMap<>();
    private static HashMap<String, Texture> textures = new HashMap<>();
    private static HashMap<String, Scene> Scenes = new HashMap<>();


    public static Vector2f getCamPos() {
        return CamPos;
    }

    public static void setCamPos(float x, float y) {
        CamPos.set(x, y);
    }

    public static void addScene(String key, Scene value) {
        Scenes.put(key, value);
        key = null;
        value = null;

    }

    public static Vector2f getWinSize() {
        return winSize;
    }

    public static void setWinSize(Vector2f winSize) {
        Collector.winSize = winSize;
    }

    public static Vector2f getCursorPos() {
        return CursorPos;
    }

    public static void setCursorPos(Vector2f cursorPos) {
        CursorPos = cursorPos;
    }

    public static HashMap<String, Scene> getScenes() {
        return Scenes;
    }


    public static int getFloor() {
        return Floor;
    }

    public static void setFloor(int floor) {
        Collector.Floor = floor;
    }

    public static int getCeil() {
        return Ceil;
    }

    public static void setCeil(int ceil) {
        Collector.Ceil = ceil;

    }
//
    public static void addToBlockMap(Vector2f key, Tiles value) {
        BlockMap.put(key, value);
        key = null;
        value = null;

    }

    public static HashMap<String, Texture> getTextures() {
        return textures;
    }

    public static void replaceInBlockMap(Vector2f key, Tiles value) {
        BlockMap.replace(key, value);
    }
    public static void addTexture(String key, Texture value) {
        textures.put(key, value);
    }
    public static Texture getTexture(String key) {return  textures.get(key);}
    public static HashMap<Vector2f, Tiles> getBlockMap() {
        return BlockMap;
    }

    public static void setBlockMap(HashMap<Vector2f, Tiles> blockMap) {
        Collector.BlockMap = blockMap;
    }

    public static void clearBlockMap() {
        Collector.BlockMap.clear();
    }

    public  static int getLevel() {
        return Level;
    }

    public static void setLevel(int level) {
        Collector.Level = level;
    }

    public static Vector2f getPlayerPos() {
        return PlayerPos;
    }

    public static void setPlayerPos(Vector2f playerPos) {
        Collector.PlayerPos = playerPos;
    }

    public static Vector4f getTime() {
        return Time;
    }

    public static void setTime(Vector4f time) {
        Collector.Time = time;
    }
}
