package com.ATTAR.scenes;

import com.ATTAR.components.CompRender;
import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.defaultes.ButtonListener;
import com.ATTAR.defaultes.Collector;
import com.ATTAR.defaultes.KeyListener;
import com.ATTAR.fonts.Sdf;
import com.ATTAR.grafic.Camera;
import com.ATTAR.grafic.*;
import com.ATTAR.maps.TileSetLoad;
import com.ATTAR.objects.Button;

import org.joml.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class MainMenu extends Scene {





    private Camera cam;
    private Button Start, Quit;
    private ButtonListener buttonListener;
    private List<Button> Buttons = new ArrayList<>();
    private KeyListener keyListener;
    private SceneManager scmg;
    private TileSetLoad tileSetLoad;
    private Shader sdfShader;
    private CompRender fontRender;
    private Sdf sdf;
    private String name;
    private boolean needload, needFill;
    public void destroy() {
        cam = null;
        sdf = null;
        fontRender = null;
        scmg = null;
        tileSetLoad = null;
        sdfShader =null;
        name = null;
        keyListener = null;
        buttonListener = null;

        Start = null;
        Quit = null;
    }

    public MainMenu(long win, SceneManager scmg, int i) {
        needload = true;
        needFill = true;
        tileSetLoad = new TileSetLoad();
        this.scmg = scmg;
        keyListener = new KeyListener(win);
        sdf = new Sdf();
        cam = new Camera(new Vector2f(0), i);

        sdf.generateBitmap("C:/Windows/Fonts/arial.ttf", 1024);
        sdfShader = new Shader("./Shaders/sdfShader.glsl");

        fontRender = new CompRender();
        fontRender.init(sdfShader, cam, sdf);

        buttonListener = new ButtonListener(win, new Vector2i(1,2));

        Buttons.add(Start = new Button(cam,new Vector2f(200,100),new Vector2f(50,160), win){
            @Override
            public void function() {

                fontRender.Update("Loading...", new Vector2f(300, 185), 0.0875f, new Vector4f(0, 1, 0, 1),0);

                    for (int j = 0; j < AssetsPool.Tiles.size(); j++) {
                        name = AssetsPool.Tiles.get(j).getTexture();

                        if (!Collector.getTextures().containsKey(name)) {
                            try {

                                Collector.addTexture(name, new Texture("./Assets/Tiles/" + name));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
//                cam = null;
                scmg.switchScene("game", "Map");
            }
        });
        Buttons.add(Quit = new Button(cam,new Vector2f(200,100),new Vector2f(50,50), win){
            @Override
            public void function() {
                glfwSetWindowShouldClose(win, true);

            }
        });

    }


    public void update(){
        if (needload) {
            tileSetLoad.interpretData();
            needload =false;
        }

        if (needFill && tileSetLoad.isTileLoaded()) {
            tileSetLoad.fillAsPool();
            needFill = false;
            tileSetLoad.clear();
        }
        else {


            buttonListener.update();

            int CurrentButton = buttonListener.getButtonId();

            if (keyListener.isPressedOnce(GLFW_KEY_ENTER)) {
                Buttons.get(CurrentButton).function();
            }
            for (int i = 0; i < Buttons.size(); i++) {
                Buttons.get(i).update();
                if (i == CurrentButton) {
                    Buttons.get(i).setSelected(true);
                } else {
                    Buttons.get(i).setSelected(false);
                }
            }
            fontRender.Update("Game Name", new Vector2f(300, 400), 0.05f, new Vector4f(0, 1, 0, 1),0);
            fontRender.Update("By ATTAR", new Vector2f(300, 50), 0.025f, new Vector4f(0, 1, 0, 1),0);
            fontRender.Update("Play", new Vector2f(60, 185), 0.09375f, new Vector4f(0, 1, 0, 1),0);
            fontRender.Update("Exit", new Vector2f(60, 70), 0.09375f, new Vector4f(0, 1, 0, 1),0);

        }
    }


}
