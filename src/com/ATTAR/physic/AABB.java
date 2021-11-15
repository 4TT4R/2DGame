package com.ATTAR.physic;

import org.joml.Vector2f;

public class AABB {



    public static boolean isAabbXCollision(Vector2f e_pos, Vector2f e_size,Vector2f e_vector, Vector2f t_pos, Vector2f t_size)
    {
        // Adapted from https://tutorialedge.net/gamedev/aabb-collision-detection-tutorial/

        if(e_pos.x+19 >= t_pos.x+t_size.x) {


            if (e_pos.x+19+e_vector.x < t_pos.x+ t_size.x) {
                if (e_pos.y>= t_pos.y && e_pos.y<= t_pos.y+ t_size.y || e_pos.y+ e_size.y<= t_pos.y+t_size.y && e_pos.y+ e_size.y>= t_pos.y){
                    System.out.println("Collision LEFT");
                    return true;
                }
            }
        }
        if(e_pos.x+ e_size.x <= t_pos.x) {

            if (e_pos.x+ e_size.x+e_vector.x > t_pos.x) {

                if (e_pos.y>= t_pos.y && e_pos.y<= t_pos.y+ t_size.y || e_pos.y+ e_size.y<= t_pos.y+t_size.y && e_pos.y+ e_size.y>= t_pos.y){

                    System.out.println("Collision RIGHT");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isAabbYCollision(Vector2f e_pos, Vector2f e_size,Vector2f e_vector, Vector2f t_pos, Vector2f t_size)
    {
        // Adapted from https://tutorialedge.net/gamedev/aabb-collision-detection-tutorial/

        if (e_pos.y >= t_pos.y+t_size.y) {

            if(e_pos.y + e_vector.y < t_pos.y+t_size.y) {

                if (e_pos.x+19 >= t_pos.x && e_pos.x+19<= t_pos.x+ t_size.x || e_pos.x+ e_size.x>= t_pos.x&& e_pos.x+ e_size.x <= t_pos.x+ t_size.x ) {
                    System.out.println("collision TOP");
                    return true;
                }
            }
        }
        if (e_pos.y + e_size.y <= t_pos.y) {
           if (e_pos.y + e_size.y + e_vector.y> t_pos.y) {
               if (e_pos.x+19 >= t_pos.x && e_pos.x+19<= t_pos.x+ t_size.x || e_pos.x+ e_size.x>= t_pos.x&& e_pos.x+ e_size.x <= t_pos.x+ t_size.x ) {
                   System.out.println("Collision BOTTOM");
                   return true;
               }
           }
        }

        return false;
    }

}
