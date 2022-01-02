package com.ATTAR.objects;

import com.ATTAR.components.*;
import com.ATTAR.grafic.*;

import org.joml.*;

import java.io.*;

public class Background {



    private Texture tx;
    private Vector2f pos;
    private CompRender render;
    private int txID, tx2ID;
    private Vector3f Scale;
    private Shader shader = new Shader("Shaders/Default.glsl");
    public Background(Camera cam) {
        pos = new Vector2f();
        render = new CompRender();
        try {
            tx = new Texture("./Assets/Backgrounds/BG_0.png",0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        txID = tx.getTexID();
        try {
            tx = new Texture("./Assets/Backgrounds/BG_1.png",0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        render.init(shader,tx , cam, false);
        tx2ID = tx.getTexID();
        Scale = new Vector3f(100,25,1);

    }
    public void setPos(float x, float y) {
       pos.set(x, y);
    }

    public void update(int BG_ID) {
        render.setPos(pos.x, pos.y);
        if (BG_ID == 0) {
            render.Update(Scale, txID);
        }
        else if (BG_ID == 1) {
            render.Update(Scale, tx2ID);
        }
    }





}
