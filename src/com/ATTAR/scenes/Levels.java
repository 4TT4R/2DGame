package com.ATTAR.scenes;

import com.ATTAR.defaultes.*;
import com.ATTAR.grafic.Camera;
import com.ATTAR.objects.Button;
import org.joml.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class Levels extends Scene {
    private Camera cam;
    private Button Lvl1, Lvl2;
    private List<Button> Buttons = new ArrayList<>();
    private ButtonListener buttonListener;
    private KeyListener keyListener;
    private SceneManager scmg;
    public Levels(Vector2f size, long win, SceneManager scmg, int i) {
        this.scmg = scmg;
        cam = new Camera(new Vector2f(0), i);
        Buttons.add(Lvl1 = new Button(cam,new Vector2f(200,100),new Vector2f(500,50),win){
            @Override
            public void function() {

                scmg.switchScene("game", "Map");
            }
        });
        Buttons.add(Lvl2 = new Button(cam,new Vector2f(200,100),new Vector2f(500,160),win){
            @Override
            public void function() {

                scmg.switchScene("game", "Map");
            }
        });
        buttonListener = new ButtonListener(win, new Vector2i(1,2));
        keyListener = new KeyListener(win);

    }

    public void update() {
        buttonListener.update();

        int CurrentButton = buttonListener.getButtonId();

        
        if (keyListener.isPressedOnce(GLFW_KEY_ENTER)) {
            Buttons.get(CurrentButton).function();
        }
        if (keyListener.isPressedOnce(GLFW_KEY_ESCAPE)) {
            scmg.switchScene("menu", null);
        }
        for (int i = 0; i < Buttons.size(); i++) {
            Buttons.get(i).update();
            if (i == CurrentButton) {
                Buttons.get(i).setSelected(true);
            } else {
                Buttons.get(i).setSelected(false);
            }
        }
    }
}
