package com.ATTAR.defaultes;

import static org.lwjgl.glfw.GLFW.*;

import com.ATTAR.Sound.Sound;
import org.joml.Vector2f;

public class PlayerMovement {
    long win;
    private Vector2f PlayerPos = new Vector2f();
    private Vector2f PlayerVector = new Vector2f();

    private float DefautPlayerSpeed, PlayerSpeed, PlayerRun;
    private Sound run;
    public boolean colidex,colidey;

    public void setPlayerPos(Vector2f playerPos) {
        PlayerPos = playerPos;
    }

    KeyListener listener;
    public PlayerMovement(long win, Vector2f PlayerPos) {
        this.win = win;
        this.PlayerPos = PlayerPos;
        listener = new KeyListener(win);
        DefautPlayerSpeed = 10f;
        PlayerRun = 17f;
        colidey = false;
        colidex = false;
        AssetsPool.addSound("./Assets/Sounds/SFX/walk.ogg", true);
        run= AssetsPool.getSound("./Assets/Sounds/SFX/walk.ogg");
        run.setVolume(15);
    }
    public Vector2f GetVector() {

            if (listener.isPressed(GLFW_KEY_LEFT_SHIFT)) {
                PlayerSpeed = PlayerRun;
            }
            else if (!listener.isPressed(GLFW_KEY_LEFT_SHIFT)) {
                PlayerSpeed = DefautPlayerSpeed;
            }

            if (listener.isPressed(GLFW_KEY_W) || listener.isPressed(GLFW_KEY_UP) ){
                if (!run.isPlaying()) {
                    run.play();
                }
                PlayerVector = new Vector2f(PlayerVector.x, PlayerSpeed);
            }
            else if (listener.isPressed(GLFW_KEY_S) || listener.isPressed(GLFW_KEY_DOWN) ){
                if (!run.isPlaying()) {
                    run.play();
                }
                PlayerVector = new Vector2f(PlayerVector.x, PlayerSpeed*(-1));
            }
            else{
                PlayerVector = new Vector2f(PlayerVector.x, 0);
            }

            if (listener.isPressed(GLFW_KEY_A) || listener.isPressed(GLFW_KEY_LEFT) ){
                if (!run.isPlaying()) {
                    run.play();
                }
                PlayerVector = new Vector2f(PlayerSpeed*(-1), PlayerVector.y);
            }
            else if(listener.isPressed(GLFW_KEY_D) || listener.isPressed(GLFW_KEY_RIGHT)){
                if (!run.isPlaying()) {
                    run.play();
                }
                PlayerVector = new Vector2f(PlayerSpeed, PlayerVector.y);
            }
            else {
                PlayerVector = new Vector2f(0, PlayerVector.y);
            }
            if (!listener.isPressed(GLFW_KEY_D) && !listener.isPressed(GLFW_KEY_RIGHT) && !listener.isPressed(GLFW_KEY_A) && !listener.isPressed(GLFW_KEY_LEFT) && !listener.isPressed(GLFW_KEY_S) && !listener.isPressed(GLFW_KEY_DOWN) && !listener.isPressed(GLFW_KEY_W) && !listener.isPressed(GLFW_KEY_UP)) {
                if (run.isPlaying()){
                    run.stop();
                }
                PlayerVector = new Vector2f(0);
            }
        if (colidex) {
            PlayerVector = new Vector2f(0,PlayerVector.y);
        }
        if (colidey) {
            PlayerVector = new Vector2f(PlayerVector.x,0);
        }

        return PlayerVector;
    }
}
