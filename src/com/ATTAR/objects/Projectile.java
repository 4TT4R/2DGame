package com.ATTAR.objects;

import com.ATTAR.components.*;
import com.ATTAR.defaultes.*;
import com.ATTAR.grafic.*;
import com.ATTAR.physic.AABB;
import org.joml.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;
import java.io.IOException;

public class Projectile {

    private Shader shader = new Shader("Shaders/Default.glsl");
    private Texture tx;
    private Vector2f vector;
    private float distance, speed;
    private Vector2f pos, size, t_pos;
    private Vector3f Scale;
    private CompRender render;
    private Vector4f AABB;
    private float destroy = 0;
    private String Texture;
    private float scale = 0.5f;

    private AABB collision = new AABB();


    private int projectileTxID, explosionTxID, txID;
    private GLFWCursorPosCallback cursorPosCallback;


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
    public Vector2f getTileByCenter() {
        return new Vector2f((int)Math.floor((pos.x+50)/100), (int)Math.floor((pos.y+50)/100));
    }
    public Vector2f getTile() {
        return new Vector2f((int)Math.floor(pos.x/100), (int)Math.floor(pos.y/100));
    }
    public Projectile(Camera cam) {
        render = new CompRender();
        try {
            tx = new Texture("./Assets/Tiles/Bullet.png");
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
        AABB = new Vector4f((float) Math.floor(32d/32d*(100*scale)),(float) Math.floor(32d/32d*(100*scale)), 0,0);
        pos = new Vector2f(0);
        t_pos = new Vector2f(0);
        vector = new Vector2f(0);
        speed = 0.05f;
        txID = projectileTxID;
        Scale = new Vector3f(scale);
        setSize(new Vector2f(100));

        render.init(shader,tx, cam, false);

    }

    public void calculateVector() {

        distance = (float) Math.sqrt((Math.pow(t_pos.x,2)+Math.pow(t_pos.y,2)));
        vector.set(t_pos.x/(distance/speed), t_pos.y/(distance/speed));
    }
    public void calculateVector(float x, float y) {

        double a = ((Collector.getCamSize().x/Collector.getWinSize().x*t_pos.x+Collector.getCamPos().x)-x);
        double b = ((Collector.getCamPos().y+Collector.getCamSize().y-Collector.getCamSize().y/Collector.getWinSize().y*t_pos.y)-y);
 /*       if (a<0) {
            a=a*-1;
        }
        if (b<0) {
            b=b*-1;
        }*/
        distance = (float) Math.sqrt(((Math.pow(a,2)+Math.pow(b,2))));


        vector.set((float) a/(distance * speed), (float) b/(distance * speed));
    }

    public float[] init() {
        pos.set(Collector.getCamPos());
        calculateVector();
        return new float[] {pos.x, pos.y, vector.x, vector.y};
    }
    public float[] init(float x, float y, float t_x, float t_y) {
        pos.set(x, y);
        t_pos.set(t_x, t_y);
        calculateVector(x, y);
        return new float[] {pos.x, pos.y, vector.x, vector.y,destroy};
    }

    public void setDistanceAndPos(float posX, float posY, float vectorX, float vectorY) {
        pos.set(posX, posY);
        vector.set(vectorX, vectorY);
    }

    public float[] updateArray() {

        return new float[] {pos.x, pos.y, vector.x, vector.y,destroy};
    }
    public float[] updateArray(float vec_x, float vec_y) {
        return new float[] {pos.x, pos.y, vec_x, vec_y,destroy};
    }
    public float[] updateArray(float destroy) {
        return new float[] {pos.x, pos.y, vector.x, vector.y,destroy};
    }

    public void update(Vector2f p_pos, Vector2f p_vector, Vector4f p_AABB) {
        if(collision.AABBProjectile(getTileByCenter(), getTile(), AABB, vector, pos) ||collision.AABBProjectileWithPlayer(pos, vector, AABB, p_pos, p_vector, p_AABB)) {
            destroy= 1;
            txID = explosionTxID;
        }
        else {
            destroy = 0;
            txID = projectileTxID;
        }
        pos.set(pos.x + vector.x, pos.y + vector.y);
        render.setPos(pos.x, pos. y);
        render.Update(Scale, txID);


    }


}
