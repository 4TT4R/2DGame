package com.ATTAR.scenes;

import com.ATTAR.defaultes.Collector;
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

        this.scene = Collector.getScenes().get(scene);




    }
    public void update(double dt) {
        if (scene != null) {

            scene.update();

        }

    }

}
