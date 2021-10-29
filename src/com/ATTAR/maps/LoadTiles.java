package com.ATTAR.maps;

import com.ATTAR.objects.Tiles;
import org.joml.Vector2f;
import org.joml.Vector3f;
import com.ATTAR.grafic.*;

import java.util.*;
public class LoadTiles {
    private static Set<Vector2f> BlockPos;
    private static HashMap<Vector2f, Tiles> BlockMap = new HashMap<>();
    public static Tiles loadTiles(Vector2f pos, Vector3f scale, Vector2f Scales, int Type, Camera cam) {
        Tiles tiles = new Tiles(cam);
        tiles.init(Type);
        tiles.setScale(scale);
        tiles.setPos(new Vector2f(pos.x * (100*Scales.x), pos.y* (100*Scales.y)));
        return tiles;

    }

    public static HashMap<Vector2f, Tiles> load(HashMap<Vector2f, Integer> Map, Camera cam){
        BlockPos = Map.keySet();
        for (int i = 0; i < BlockPos.size(); i++) {

            Vector2f pos = (Vector2f) BlockPos.toArray()[i];

            BlockMap.put(pos, loadTiles(pos, new Vector3f(1f), new Vector2f(1f), Map.get(pos), cam));
        };
        return BlockMap;
    }



}
