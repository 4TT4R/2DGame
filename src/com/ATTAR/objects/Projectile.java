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
    private Vector2f pos, size;
    private Vector3f Scale;
    private CompRender render;
    private Vector4f AABB;
    private float destroy = 0;
    private String Texture;

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
        AABB = new Vector4f((float) Math.floor(32d/32d*100d),(float) Math.floor(32d/32d*100d), 0,0);
        explosionTxID = tx.getTexID();
        pos = new Vector2f(0);
        vector = new Vector2f(0);
        speed = 10;
        txID = projectileTxID;
        Scale = new Vector3f(1f);
        setSize(new Vector2f(100));
        render = new CompRender();
        render.init(shader,Texture, cam, false);
    }
    public void calculateVector() {

        distance = (float) Math.sqrt((Math.pow(Collector.getCursorPos().x,2)+Math.pow(Collector.getCursorPos().y,2)));
        vector.set(Collector.getCursorPos().x/(distance/speed), Collector.getCursorPos().y/(distance/speed));
    }
    public void calculateVector(float x, float y) {

        double a = ((Collector.getCamSize().x/Collector.getWinSize().x*Collector.getCursorPos().x+Collector.getCamPos().x)-x);
        double b = ((Collector.getCamPos().y+Collector.getCamSize().y-Collector.getCamSize().y/Collector.getWinSize().y*Collector.getCursorPos().y)-y);
 /*       if (a<0) {
            a=a*-1;
        }
        if (b<0) {
            b=b*-1;
        }*/
        distance = (float) Math.sqrt(((Math.pow(a,2)+Math.pow(b,2))));


        vector.set((float) a/speed, (float) b/speed);
    }

    public float[] init() {
        pos.set(Collector.getCamPos());
        calculateVector();
        return new float[] {pos.x, pos.y, vector.x, vector.y};
    }
    public float[] init(float x, float y) {
        pos.set(x, y);
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

    public void update() {
        if(collision.AABBProjectile(getTileByCenter(), getTile(), new Vector2f(), new Vector2f(), AABB, vector, new Vector4f(), new Vector2f(), pos, new Vector2f())) {
            destroy= 1;
        }
        else {
            destroy = 0;
        }
        pos.set(pos.x + vector.x, pos.y + vector.y);
        render.setPos(pos.x, pos.y);
        render.Update(Scale, txID);


    }


}
