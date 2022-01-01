package com.ATTAR.objects;

import com.ATTAR.components.*;
import com.ATTAR.grafic.*;
import org.joml.*;


public class Squere {
    private Camera cam;
    private Shader squers = new Shader("Shaders/Geometry.glsl");
    private Vector3f color;
    public Squere(Camera cam) {
        this.cam = cam;
        color = new Vector3f(1);
    }
    private Vector3f Scale;
    private CompRender render;

    public Vector3f getScale() {
        return Scale;
    }

    public void setScale(Vector3f scale) {
        Scale = scale;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public void init(Vector3f color){
        Scale = new Vector3f(1);
        this.color= color;


        render = new CompRender();
        render.init(squers, cam);




    }

    public void update() {
        render.Update(Scale);
        render.setColor(new Vector4f(color,1));

    }
    public void setPos(float x, float y ) {

        render.setPos(x, y);

    }
    public Vector2f getPos() {

        return render.getPos();

    }

}
