package com.ATTAR.defaultes;

import com.ATTAR.objects.Tiles;
import org.joml.*;

import java.util.HashMap;

public class Collector {





    private static int Level, Ceil, Floor;
    private static Vector2f PlayerPos;
    private static Vector4f Time;
    private static HashMap<Vector2f, Tiles> BlockMap = new HashMap<>();

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

    public static void addToBlockMap(Vector2f key, Tiles value) {
        BlockMap.put(key, value);
    }

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
