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
    private static List<Vector2f> oldKeys = new ArrayList<>();
    public static Tiles loadTiles(Vector2f pos, Vector3f scale, Vector2f Scales, int ID, Camera cam) {
        Tiles tiles = AssetsPool.getTile(ID-1);
        tiles.setCam(cam);
        tiles.init();
        tiles.setScale(scale);
        tiles.setPos(pos.x * (100*Scales.x), pos.y* (100*Scales.y));
        pos = null;
        scale = null;
        Scales = null;
        cam = null;
        return tiles;


    }


    public static Tiles replaceTile(Vector2f Pos, Tiles tile, Camera cam) {
        Tiles tiles = new Tiles(tile,cam);
        tiles.init();
        tiles.setScale(new Vector3f(1));
        tiles.setPos(Pos.x, Pos.y);
        return tiles;

    }

    public static void load(HashMap<Vector2f, Integer> Map, Camera cam){

//        BlockPos = Map.keySet();


        System.gc();

        Vector2f pos = new Vector2f(0);
//        for (int i = BlockPos.size()-1; i >=0; i--) {
//
//            pos = (Vector2f) BlockPos.toArray()[i];
//            if (pos.y <= (float) Collector.getCeil() && pos.y >= (float) Collector.getFloor()) {
//                if (!Collector.getBlockMap().containsKey(pos)) {
//                    Collector.addToBlockMap(pos, loadTiles(pos, new Vector3f(1f), new Vector2f(1f), Map.get(pos), cam));
//                }
//                else {
//                    Collector.getBlockMap().replace(pos, loadTiles(pos, new Vector3f(1f), new Vector2f(1f), Map.get(pos), cam));
//
//                }
//            }
//        }
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 25; j++) {
                pos = new Vector2f(i,j);
                if (Map.containsKey(new Vector2f(i,(float) j+Collector.getFloor()))) {
                    if (Collector.getBlockMap().containsKey(pos)) {
                        Collector.replaceInBlockMap(pos, loadTiles(pos, new Vector3f(1f), new Vector2f(1f), Map.get(new Vector2f(i, j+Collector.getFloor())), cam));

                    }
                    else {
                        Collector.addToBlockMap(pos, loadTiles(pos, new Vector3f(1f), new Vector2f(1f), Map.get(new Vector2f(i, j + Collector.getFloor())), cam));
                    }
                }
                else {
//                    System.out.println(new Vector2f(i,j+Collector.getFloor()) + "Null");
                    if (Collector.getBlockMap().containsKey(pos)) {

                        Collector.replaceInBlockMap(pos, null);

                    }
                    else {

                        Collector.addToBlockMap(pos, null);
                    }
                }

            }

        }

        pos = null;
        System.gc();
        
    }



}
