package com.ATTAR.physic;

import org.joml.*;

public class AABB {
    private float leftside = 19;
    private Vector2f CorrectPos = new Vector2f();

    public void setCorrectPos(Vector2f correctPos) {
        CorrectPos = correctPos;
    }

    public Vector2f getCorrectPos() {
        return CorrectPos;
    }

    public boolean isAabbXCollision(Vector2f e_pos, Vector2f e_size,Vector2f e_vector, Vector2f t_pos, Vector4f t_size)
    {
        /**left collision beginning**/
        if(e_pos.x+leftside >= t_pos.x+t_size.z) {


            if (e_pos.x+leftside+e_vector.x < t_pos.x+ t_size.z) {
                if (e_pos.y+ e_vector.y>= t_pos.y+t_size.y && e_pos.y+ e_vector.y<= t_pos.y+ t_size.w || e_pos.y+ e_size.y+ e_vector.y<= t_pos.y+t_size.w && e_pos.y+ e_vector.y+ e_size.y>= t_pos.y+t_size.y){

                    setCorrectPos(new Vector2f(t_pos.x+e_size.x+0.5f, e_pos.y));

                    return true;
                }
            }
        }
        /**left collision end**/
        /**right side collision beginning**/
        if(e_pos.x+ e_size.x <= t_pos.x) {

            if (e_pos.x+ e_size.x+e_vector.x > t_pos.x) {

                if (e_pos.y+ e_vector.y>= t_pos.y+t_size.y && e_pos.y+ e_vector.y<= t_pos.y+ t_size.w || e_pos.y+ e_size.y+ e_vector.y<= t_pos.y+t_size.w && e_pos.y+ e_vector.y+ e_size.y>= t_pos.y+t_size.y){

                    setCorrectPos(new Vector2f(t_pos.x-e_size.x-0.5f, e_pos.y));

                    return true;
                }
            }
        }
        /**right collision end**/
        return false;
    }

    public boolean isAabbYCollision(Vector2f e_pos, Vector2f e_size,Vector2f e_vector, Vector2f t_pos, Vector4f t_size)
    {

        if (e_pos.y >= t_pos.y+t_size.w) {

            if(e_pos.y + e_vector.y < t_pos.y+t_size.w) {

                if (e_pos.x+leftside+e_vector.x >= t_pos.x && e_pos.x+leftside+e_vector.x<= t_pos.x+ t_size.z || e_pos.x+e_vector.x+ e_size.x>= t_pos.x&& e_pos.x+e_vector.x+ e_size.x <= t_pos.x+ t_size.z ) {

                    setCorrectPos(new Vector2f(e_pos.x,t_pos.y+t_size.w+0.5f));

                    return true;
                }
            }
        }
        if (e_pos.y + e_size.y <= t_pos.y+t_size.y) {
            if (e_pos.y + e_size.y + e_vector.y> t_pos.y+t_size.y) {
                if (e_pos.x+leftside+e_vector.x >= t_pos.x && e_pos.x+leftside+e_vector.x<= t_pos.x+ t_size.z || e_pos.x+e_vector.x+ e_size.x>= t_pos.x&& e_pos.x+e_vector.x+ e_size.x <= t_pos.x+ t_size.z ) {

                    setCorrectPos(new Vector2f(e_pos.x,t_pos.y+t_size.y-e_size.y-0.5f));

                    return true;
                }
            }
        }

        return false;
    }

}