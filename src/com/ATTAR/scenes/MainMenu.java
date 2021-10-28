package com.ATTAR.scenes;

import com.ATTAR.components.CompRender;
import com.ATTAR.defaultes.ButtonListener;
import com.ATTAR.defaultes.KeyListener;
import com.ATTAR.fonts.Sdf;
import com.ATTAR.grafic.Camera;
import com.ATTAR.grafic.*;
import com.ATTAR.objects.Button;

import org.joml.*;

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

    private Shader sdfShader;
    private CompRender fontRender;
    private Sdf sdf;
    Random random = new Random();
    String message;
    public MainMenu(long win, SceneManager scmg, int i) {
        this.scmg = scmg;
        keyListener = new KeyListener(win);
        sdf = new Sdf();
        cam = new Camera(new Vector2f(0), i);
//        sdf.generateBitmap("./Assets/Fonts/press-start.ttf", 128);
        sdf.generateBitmap("C:/Windows/Fonts/arial.ttf", 128);
        sdfShader = new Shader("./Shaders/sdfShader.glsl");

        fontRender = new CompRender();
        fontRender.init(sdfShader, cam, sdf);

        buttonListener = new ButtonListener(win, new Vector2i(1,2));

        Buttons.add(Start = new Button(cam,new Vector2f(200,100),new Vector2f(50,160), win){
            @Override
            public void function() {

                scmg.switchScene("levels", null);
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

        buttonListener.update();

        int CurrentButton = buttonListener.getButtonId();

        if (keyListener.isPressedOnce(GLFW_KEY_ENTER)){
            Buttons.get(CurrentButton).function();
        }
        for (int i = 0; i<Buttons.size(); i++) {
            Buttons.get(i).update();
            if (i == CurrentButton) {
                Buttons.get(i).setSelected(true);
            }
            else {
                Buttons.get(i).setSelected(false);
            }
        }
        fontRender.Update("Game Name",new Vector2f(300,400), 0.4f, new Vector4f(0,1,0,1));
        fontRender.Update("By ATTAR",new Vector2f(300,50), 0.2f, new Vector4f(0,1,0,1));
        message = "";
        for (int i=0; i < 10; i++) {

            message += (char)(random.nextInt('z' - 'a') + 'a');
        }

        fontRender.Update(message,new Vector2f(300,300), 0.4f, new Vector4f(0,1,0,1));

    }


}
