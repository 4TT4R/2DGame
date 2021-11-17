package com.ATTAR.physic;

import org.joml.Vector2f;

public class AABB {
    private float leftside = 19;
    private Vector2f CorrectPos = new Vector2f();

    public void setCorrectPos(Vector2f correctPos) {
        CorrectPos = correctPos;
    }

    public Vector2f getCorrectPos() {
        return CorrectPos;
    }

    public boolean isAabbXCollision(Vector2f e_pos, Vector2f e_size,Vector2f e_vector, Vector2f t_pos, Vector2f t_size)
    {
        // Adapted from https://tutorialedge.net/gamedev/aabb-collision-detection-tutorial/

        if(e_pos.x+leftside >= t_pos.x+t_size.x) {


            if (e_pos.x+leftside+e_vector.x < t_pos.x+ t_size.x) {
                if (e_pos.y+ e_vector.y>= t_pos.y && e_pos.y+ e_vector.y<= t_pos.y+ t_size.y || e_pos.y+ e_size.y+ e_vector.y<= t_pos.y+t_size.y && e_pos.y+ e_vector.y+ e_size.y>= t_pos.y){
                    System.out.println("Collision LEFT");
                    setCorrectPos(new Vector2f(t_pos.x+e_size.x+0.5f, e_pos.y));
                    return true;
                }
            }
        }
        if(e_pos.x+ e_size.x <= t_pos.x) {

            if (e_pos.x+ e_size.x+e_vector.x > t_pos.x) {

                if (e_pos.y+ e_vector.y>= t_pos.y && e_pos.y+ e_vector.y<= t_pos.y+ t_size.y || e_pos.y+ e_size.y+ e_vector.y<= t_pos.y+t_size.y && e_pos.y+ e_vector.y+ e_size.y>= t_pos.y){

                    System.out.println("Collision RIGHT");
                    setCorrectPos(new Vector2f(t_pos.x-e_size.x-0.5f, e_pos.y));

                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAabbYCollision(Vector2f e_pos, Vector2f e_size,Vector2f e_vector, Vector2f t_pos, Vector2f t_size)
    {
        // Adapted from https://tutorialedge.net/gamedev/aabb-collision-detection-tutorial/

        if (e_pos.y >= t_pos.y+t_size.y) {

            if(e_pos.y + e_vector.y < t_pos.y+t_size.y) {

                if (e_pos.x+leftside+e_vector.x >= t_pos.x && e_pos.x+leftside+e_vector.x<= t_pos.x+ t_size.x || e_pos.x+e_vector.x+ e_size.x>= t_pos.x&& e_pos.x+e_vector.x+ e_size.x <= t_pos.x+ t_size.x ) {
                    System.out.println("collision TOP");
                    setCorrectPos(new Vector2f(e_pos.x,t_pos.y+t_size.y+0.5f));

                    return true;
                }
            }
        }
        if (e_pos.y + e_size.y <= t_pos.y) {
            if (e_pos.y + e_size.y + e_vector.y> t_pos.y) {
                if (e_pos.x+leftside+e_vector.x >= t_pos.x && e_pos.x+leftside+e_vector.x<= t_pos.x+ t_size.x || e_pos.x+e_vector.x+ e_size.x>= t_pos.x&& e_pos.x+e_vector.x+ e_size.x <= t_pos.x+ t_size.x ) {
                    System.out.println("Collision BOTTOM");
                    setCorrectPos(new Vector2f(e_pos.x,t_pos.y-e_size.y-0.5f));

                    return true;
                }
            }
        }

        return false;
    }

}