package com.ATTAR.physic;

import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.defaultes.Collector;
import com.ATTAR.maps.LoadTiles;
import com.ATTAR.objects.BlockTimer;
import com.ATTAR.objects.Tiles;
import org.joml.*;

public class AABB {

    private Vector2f CorrectPos = new Vector2f();
    private Vector2f e_vector = new Vector2f();
    private Vector2f tileKey = new Vector2f(), current_tile_pos = new Vector2f(), vector2f = new Vector2f();
    private Tiles current_tile, under_tile;


    public Vector2f getPosByKey(Vector2f key) {

        vector2f.set(key.x*100, key.y*100);
        return vector2f;
    }
    public void setCorrectPos(Vector2f correctPos) {
        CorrectPos = correctPos;
    }

    public Vector2f getCorrectPos() {
        return CorrectPos;
    }

    public boolean isAabbXCollision(Vector2f e_pos, Vector4f e_size,Vector2f e_vector, Vector2f t_pos, Vector4f t_size) {
        /**left collision beginning**/
        if(e_pos.x+e_size.z >= t_pos.x+t_size.z) {

            if (e_pos.x+e_size.z+e_vector.x < t_pos.x+ t_size.z) {

                if (e_pos.y+ e_vector.y>= t_pos.y+t_size.y && e_pos.y+ e_vector.y<= t_pos.y+ t_size.w || e_pos.y+ e_size.y+ e_vector.y<= t_pos.y+t_size.w && e_pos.y+ e_vector.y+ e_size.y>= t_pos.y+t_size.y){
//                    System.out.println("left collision");
                    setCorrectPos(new Vector2f(e_pos.x-(e_pos.x-t_pos.x-t_size.z+e_size.z), e_pos.y));
                    e_pos = null;
                    e_size = null;

                    t_pos = null;
                    t_size = null;

                    return true;
                }
            }
        }
        /**left collision end**/
        /**right side collision beginning**/
        if(e_pos.x+ e_size.x <= t_pos.x) {

            if (e_pos.x+ e_size.x+e_vector.x > t_pos.x) {

                if (e_pos.y+ e_vector.y>= t_pos.y+t_size.y && e_pos.y+ e_vector.y<= t_pos.y+ t_size.w || e_pos.y+ e_size.y+ e_vector.y<= t_pos.y+t_size.w && e_pos.y+ e_vector.y+ e_size.y>= t_pos.y+t_size.y){
//                    System.out.println("right collision");
                    setCorrectPos(new Vector2f(e_pos.x+(t_pos.x-e_pos.x-e_size.x-0.5f), e_pos.y));
                    e_pos = null;
                    e_size = null;
                    
                    t_pos = null;
                    t_size = null;

                    return true;
                }
            }
        }
        /**right collision end**/
        e_pos = null;
        e_size = null;
        
        t_pos = null;
        t_size = null;

        return false;
    }

    public boolean isAabbYCollision(Vector2f e_pos, Vector4f e_size, float e_vector_x, float e_vector_y, Vector2f t_pos, Vector4f t_size) {
        e_vector.set(e_vector_x, e_vector_y);
        if (e_pos.y >= t_pos.y+t_size.w) {

            if(e_pos.y + e_vector.y < t_pos.y+t_size.w) {

                if (e_pos.x+e_size.z+e_vector.x > t_pos.x && e_pos.x+e_size.z+e_vector.x< t_pos.x+ t_size.z || e_pos.x+e_vector.x+ e_size.x> t_pos.x&& e_pos.x+e_vector.x+ e_size.x < t_pos.x+ t_size.z ) {
//                    System.out.println("top collision");
                    setCorrectPos(new Vector2f(e_pos.x,e_pos.y-(e_pos.y-t_pos.y-t_size.w)));
                    e_pos = null;
                    e_size = null;
                    
                    t_pos = null;
                    t_size = null;

                    return true;
                }
            }
        }
        if (e_pos.y + e_size.y <= t_pos.y+t_size.y) {
            if (e_pos.y + e_size.y + e_vector.y> t_pos.y+t_size.y) {
                if (e_pos.x+e_size.z+e_vector.x > t_pos.x && e_pos.x+e_size.z+e_vector.x< t_pos.x+ t_size.z || e_pos.x+e_vector.x+ e_size.x> t_pos.x&& e_pos.x+e_vector.x+ e_size.x < t_pos.x+ t_size.z ) {
//                    System.out.println("bottom collision");
             
                    setCorrectPos(new Vector2f(e_pos.x,e_pos.y+(t_pos.y-e_pos.y-e_size.y)));
                    e_pos = null;
                    e_size = null;

                    t_pos = null;
                    t_size = null;

                    return true;
                }
            }
        }
        e_pos = null;
        e_size = null;
        
        t_pos = null;
        t_size = null;

        return false;
    }
    public boolean AABBProjectileWithPlayer(Vector2f en1_pos, Vector2f en1_vector, Vector4f en1_AABB, Vector2f en2_pos, Vector2f en2_vector, Vector4f en2_AABB){
        if(     en1_pos.x + en1_vector.x + en1_AABB.z < en2_pos.x + en2_vector.x + en2_AABB.x &&
                en1_pos.x + en1_vector.x + en1_AABB.x > en2_pos.x + en2_vector.x + en2_AABB.z &&
                en1_pos.y + en1_vector.y + en1_AABB.w < en2_pos.y + en2_vector.y + en2_AABB.y &&
                en1_pos.y + en1_vector.y + en1_AABB.y> en2_pos.y + en2_vector.y + en2_AABB.w) {
            return true;
        }
        return false;
    }
    public boolean AABBProjectile(Vector2f e1_tile_byCenter, Vector2f e1_tile, Vector4f e1_size, Vector2f e1_vector, Vector2f e1_pos) {
        /*left collision beginning*/
        if (Collector.getBlockMap().containsKey(tileKey.set(e1_tile_byCenter.x - 1, e1_tile.y)) && Collector.getBlockMap().get(tileKey)!=null && !Collector.getBlockMap().get(tileKey).isTriger()) {
            current_tile = Collector.getBlockMap().get(tileKey);
            current_tile_pos = getPosByKey(tileKey);
            if (isAabbXCollision(e1_pos,
                    e1_size, e1_vector,
                    current_tile_pos,
                    new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

                return true;
                

                
            

            }
        }
        if (Collector.getBlockMap().containsKey(tileKey.set(e1_tile_byCenter.x - 1, e1_tile.y + 1)) && Collector.getBlockMap().get(tileKey)!=null && !Collector.getBlockMap().get(tileKey).isTriger()) {
            current_tile_pos = getPosByKey(tileKey);
            current_tile = Collector.getBlockMap().get(tileKey);
            if (isAabbXCollision(e1_pos,
                    e1_size, e1_vector,
                    current_tile_pos,
                    new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

                return true;

            }
                

        }
        /*left collision end*/
        /*right collision beginning*/
        if (Collector.getBlockMap().containsKey(tileKey.set(e1_tile_byCenter.x + 1, e1_tile.y)) && Collector.getBlockMap().get(tileKey)!=null && !Collector.getBlockMap().get(tileKey).isTriger()) {
            current_tile_pos = getPosByKey(tileKey);
            current_tile = Collector.getBlockMap().get(tileKey);
            if (isAabbXCollision(e1_pos,
                    e1_size, e1_vector,
                    current_tile_pos,
                    new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

                return true;


            }
        }
        if (Collector.getBlockMap().containsKey(tileKey.set(e1_tile_byCenter.x + 1, e1_tile.y + 1)) && Collector.getBlockMap().get(tileKey)!=null && !Collector.getBlockMap().get(tileKey).isTriger()) {
            current_tile_pos = getPosByKey(tileKey);
            current_tile = Collector.getBlockMap().get(tileKey);
            if (isAabbXCollision(e1_pos,
                    e1_size, e1_vector,
                    current_tile_pos,
                    new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


                return true;



            }

        }
        /*right collision end*/
        /*top collision beginning*/
        if (Collector.getBlockMap().containsKey(tileKey.set(e1_tile.x, e1_tile_byCenter.y + 1)) && Collector.getBlockMap().get(tileKey)!=null && !Collector.getBlockMap().get(tileKey).isTriger()) {
            current_tile_pos = getPosByKey(tileKey);
            current_tile = Collector.getBlockMap().get(tileKey);
            if (isAabbYCollision(e1_pos,
                    e1_size, e1_vector.x, e1_vector.y,
                    current_tile_pos,
                    new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


                return true;


            }

        }
        if (Collector.getBlockMap().containsKey(tileKey.set(e1_tile.x + 1, e1_tile_byCenter.y + 1)) && Collector.getBlockMap().get(tileKey)!=null && !Collector.getBlockMap().get(tileKey).isTriger()) {
            current_tile_pos = getPosByKey(tileKey);
            current_tile = Collector.getBlockMap().get(tileKey);
            if (isAabbYCollision(e1_pos,
                    e1_size, e1_vector.x, e1_vector.y,
                    current_tile_pos,
                    new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {


                return true;


            }

        }
        /*top collision end*/
        /*bottom collision beginning*/
        if (Collector.getBlockMap().containsKey(tileKey.set(e1_tile.x, e1_tile_byCenter.y - 1)) && Collector.getBlockMap().get(tileKey)!=null && !Collector.getBlockMap().get(tileKey).isTriger()) {
            current_tile_pos = getPosByKey(tileKey);

            current_tile = Collector.getBlockMap().get(tileKey);
            if (isAabbYCollision(e1_pos,
                    e1_size, e1_vector.x, e1_vector.y,
                    current_tile_pos,
                    new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100,
                            current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

                return true;


            }


        }
        if (Collector.getBlockMap().containsKey(tileKey.set(e1_tile.x + 1, e1_tile_byCenter.y - 1)) && Collector.getBlockMap().get(tileKey)!=null && !Collector.getBlockMap().get(tileKey).isTriger()) {
            current_tile_pos = getPosByKey(tileKey);

            current_tile = Collector.getBlockMap().get(tileKey);
            if (isAabbYCollision(e1_pos,
                    e1_size, e1_vector.x, e1_vector.y,
                    current_tile_pos,

                    new Vector4f(current_tile.getAABB().x / 32 * 100, current_tile.getAABB().y / 32 * 100, current_tile.getAABB().z / 32 * 100, current_tile.getAABB().w / 32 * 100)) && current_tile.isSolid()) {

                return true;



            }

        }

        return false;
    }
    public AABB() {
        
    }

}