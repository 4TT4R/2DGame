package com.ATTAR.scenes;

import org.joml.*;

public class SceneManager {




    private Scene scene;
    private Vector2f SceneSize;
    private long win;



    private int i;


    public SceneManager(Vector2f SceneSize, long win, int i) {
        this.SceneSize = SceneSize;
        this.win = win;
        this.i = i;

    }
    public void switchScene(String scene, String MapName) {
        if (scene.equals("game")) {
            this.scene = null;

            this.scene = new GameScene(SceneSize, MapName, win, i, this);

        }
        else if (scene.equals("menu")) {
            this.scene = null;
            this.scene = new MainMenu( win, this, i);


        }
        else if (scene.equals("levels")) {
            this.scene = null;
            this.scene = new Levels(SceneSize, win, this, i);

        }
    }
    public void update(double dt) {
        if (scene != null) {

            scene.update();

        }

    }

}
