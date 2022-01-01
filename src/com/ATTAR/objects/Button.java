package com.ATTAR.objects;






import com.ATTAR.defaultes.KeyListener;
import org.joml.*;
import com.ATTAR.grafic.*;

import java.awt.event.KeyEvent;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;

public class Button {





    private Squere sq;
    private boolean Selected = false;
    private Vector3f color;
    private KeyListener keyListener;

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public Button(Camera cam, Vector2f size, Vector2f pos, long win) {
        sq = new Squere(cam);
        sq.init(new Vector3f(255));
        sq.setPos(pos.x, pos.y);
        sq.setScale(sizeToScale(size));
        setColor(new Vector3f(1));
        keyListener = new KeyListener(win);

    }
    public Vector3f sizeToScale(Vector2f size){

        return new Vector3f(size.x/100, size.y/100, 1);
    }
    public void update() {
        sq.update();

        if(isSelected()){
            changeColor(new Vector3f(0.1f));
        }
        else {
            changeColor(color);
        }

    }
    public void changeColor(Vector3f color) {
        sq.setColor(color);
    }
    public void setColor(Vector3f color) {
        this.color = color;
    }
    public void function() {
//        System.out.println("This button doesn't have function");
    }
}
