package com.ATTAR.physic;

import org.joml.*;

public class AABB {

    private Vector2f CorrectPos = new Vector2f();
    private Vector2f e_vector = new Vector2f();


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
                    System.out.println("left collision");
                    setCorrectPos(new Vector2f(t_pos.x+t_size.z-e_size.z+0.5f, e_pos.y));
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
                    System.out.println("right collision");
                    setCorrectPos(new Vector2f(t_pos.x-e_size.x-0.5f, e_pos.y));
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

                if (e_pos.x+e_size.z+e_vector.x >= t_pos.x && e_pos.x+e_size.z+e_vector.x<= t_pos.x+ t_size.z || e_pos.x+e_vector.x+ e_size.x>= t_pos.x&& e_pos.x+e_vector.x+ e_size.x <= t_pos.x+ t_size.z ) {
                    System.out.println("top collision");
                    setCorrectPos(new Vector2f(e_pos.x,t_pos.y+t_size.w));
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
                if (e_pos.x+e_size.z+e_vector.x >= t_pos.x && e_pos.x+e_size.z+e_vector.x<= t_pos.x+ t_size.z || e_pos.x+e_vector.x+ e_size.x>= t_pos.x&& e_pos.x+e_vector.x+ e_size.x <= t_pos.x+ t_size.z ) {
                    System.out.println("bottom collision");
                    setCorrectPos(new Vector2f(e_pos.x,t_pos.y+t_size.y-e_size.y));
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

    public AABB() {
        
    }

}