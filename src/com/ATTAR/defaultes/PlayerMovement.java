package com.ATTAR.defaultes;

import static org.lwjgl.glfw.GLFW.*;

import com.ATTAR.Sound.Sound;
import org.joml.Vector2f;

public class PlayerMovement {
    long win;
    private Vector2f PlayerVector = new Vector2f();

    private float DefaultPlayerSpeed, PlayerSpeed, PlayerRun, jump_power;
    private Sound run;
    public boolean collideX,collideY,can_jump, jump;

    public void setPlayerVector(Vector2f playerVector) {
        this.PlayerVector = playerVector;
    }
    KeyListener listener;
    public PlayerMovement(long win, Vector2f PlayerPos) {
        this.win = win;
        can_jump = false;
        jump_power = 25;
        jump = false;
        listener = new KeyListener(win);
        DefaultPlayerSpeed = 10f;
        PlayerRun = 17f;
        collideY = false;
        collideX = false;
        AssetsPool.addSound("./Assets/Sounds/SFX/walk.ogg", true);
        run = AssetsPool.getSound("./Assets/Sounds/SFX/walk.ogg");
        run.setVolume(15);
    }

    public Vector2f GetVector() {

            if (listener.isPressed(GLFW_KEY_LEFT_SHIFT)) {
                PlayerSpeed = PlayerRun;
            }
            else if (!listener.isPressed(GLFW_KEY_LEFT_SHIFT)) {
                PlayerSpeed = DefaultPlayerSpeed;
            }

            if (listener.isPressed(GLFW_KEY_W) || listener.isPressed(GLFW_KEY_UP) ){
                if (can_jump) {
                    jump = true;
                    PlayerVector = new Vector2f(PlayerVector.x, jump_power);
                    can_jump= false;
                }
            }
//            else if (listener.isPressed(GLFW_KEY_S) || listener.isPressed(GLFW_KEY_DOWN) ){
//                if (!run.isPlaying()) {
//                    run.play();
//                }
//                PlayerVector = new Vector2f(PlayerVector.x, PlayerSpeed*(-1));
//            }
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
            if (!listener.isPressed(GLFW_KEY_D) && !listener.isPressed(GLFW_KEY_RIGHT) && !listener.isPressed(GLFW_KEY_A) && !listener.isPressed(GLFW_KEY_LEFT)) {
                if (run.isPlaying()){
                    run.stop();
                }
                PlayerVector = new Vector2f(0);
            }
        if (collideX) {
            PlayerVector = new Vector2f(0,PlayerVector.y);
        }
        if (jump) {
            PlayerVector = new Vector2f(PlayerVector.x,jump_power);
        }

        return PlayerVector;
    }
}
