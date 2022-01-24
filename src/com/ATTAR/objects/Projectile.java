package com.ATTAR.objects;

import com.ATTAR.components.*;
import com.ATTAR.defaultes.*;
import com.ATTAR.grafic.*;
import org.joml.*;

import java.io.IOException;

public class Projectile {

    private Shader shader = new Shader("Shaders/Default.glsl");
    private Texture tx;
    private Vector2f vector;
    private float distance, speed;
    private Vector2f pos, size;
    private Vector3f Scale;
    private CompRender render;
    private Vector4f AABB;
    private String Texture;



    private int projectileTxID, explosionTxID, txID;

    public String getTexture() {
        return Texture;
    }

    public void setTexture(String texture) {
        Texture = texture;
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f size) {
        this.size = size;
    }

    public Projectile(Camera cam) {

        render = new CompRender();
        try {
            tx = new Texture("./Assets/Tiles/Player_idle.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        projectileTxID = tx.getTexID();
        try {
            tx = new Texture("./Assets/Tiles/Floor.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        explosionTxID = tx.getTexID();
        pos = new Vector2f(0);
        vector = new Vector2f(0);
        speed = 10;
        txID = projectileTxID;
        Scale = new Vector3f(1);
        setSize(new Vector2f(100));
        render = new CompRender();
        render.init(shader,Texture, cam, false);
    }
    public void calculateVector() {
        distance = (float) Math.sqrt((Math.pow(Collector.getCursorPos().x,2)+Math.pow(Collector.getCursorPos().y,2)));
        vector.set(Collector.getCursorPos().x/(distance/speed), Collector.getCursorPos().y/(distance/speed));
    }

    public float[] init() {
        pos.set(Collector.getCamPos());
        calculateVector();
        return new float[] {pos.x, pos.y, vector.x, vector.y};
    }

    public void setDistanceAndPos(float posX, float posY, float vectorX, float vectorY) {
        pos.set(posX, posY);
        vector.set(vectorX, vectorY);
    }

    public float[] updateArray() {
        return new float[] {pos.x, pos.y, vector.x, vector.y};
    }

    public void update() {
        pos.set(pos.x + vector.x, pos.y + vector.y);
        render.setPos(pos.x, pos.y);
        render.Update(Scale, txID);


    }


}
