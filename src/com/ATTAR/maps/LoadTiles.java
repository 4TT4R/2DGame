package com.ATTAR.maps;

import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.defaultes.Collector;
import com.ATTAR.objects.Tiles;
import org.joml.Vector2f;
import org.joml.Vector3f;
import com.ATTAR.grafic.*;

import java.util.*;
public class LoadTiles {
    private static Set<Vector2f> BlockPos;


    public static Tiles loadTiles(Vector2f pos, Vector3f scale, Vector2f Scales, int ID, Camera cam) {
        Tiles tiles = new Tiles(AssetsPool.getTile(ID-1),cam);
        tiles.init();
        tiles.setScale(scale);
        tiles.setPos(new Vector2f(pos.x * (100*Scales.x), pos.y* (100*Scales.y)));
        return tiles;

    }
    public static Tiles replaceTile(Vector2f Pos, Tiles tile, Camera cam) {
        Tiles tiles = new Tiles(tile,cam);
        tiles.init();
        tiles.setScale(new Vector3f(1));
        tiles.setPos(Pos);
        return tiles;

    }

    public static void load(HashMap<Vector2f, Integer> Map, Camera cam){

        BlockPos = Map.keySet();

        Collector.clearBlockMap();
        for (int i = BlockPos.size()-1; i >=0; i--) {

            Vector2f pos = (Vector2f) BlockPos.toArray()[i];

            if (((Vector2f) BlockPos.toArray()[i]).y <= (float) Collector.getCeil() && ((Vector2f) BlockPos.toArray()[i]).y >= (float) Collector.getFloor()) {

                Collector.addToBlockMap(pos, loadTiles(pos, new Vector3f(1f), new Vector2f(1f), Map.get(pos), cam));
            }
        }
        
    }



}
