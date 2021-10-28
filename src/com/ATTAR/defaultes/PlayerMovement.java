package com.ATTAR.defaultes;

import static org.lwjgl.glfw.GLFW.*;
import org.joml.Vector2f;

public class PlayerMovement {
    long win;
    private Vector2f PlayerPos = new Vector2f();
    private float DefautPlayerSpeed, PlayerSpeed, PlayerRun;


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
    }
    public Vector2f update() {

        if (listener.isPressed(GLFW_KEY_LEFT_SHIFT)) {
            PlayerSpeed = PlayerRun;
        }
        else if (!listener.isPressed(GLFW_KEY_LEFT_SHIFT)) {
            PlayerSpeed = DefautPlayerSpeed;
        }

        if (listener.isPressed(GLFW_KEY_W) || listener.isPressed(GLFW_KEY_UP) ){

            PlayerPos = new Vector2f(PlayerPos.x, PlayerPos.y+PlayerSpeed);
        }
        else if (listener.isPressed(GLFW_KEY_S) || listener.isPressed(GLFW_KEY_DOWN) ){

            PlayerPos = new Vector2f(PlayerPos.x, PlayerPos.y-PlayerSpeed);
        }
        if (listener.isPressed(GLFW_KEY_A) || listener.isPressed(GLFW_KEY_LEFT) ){

            PlayerPos = new Vector2f(PlayerPos.x-PlayerSpeed, PlayerPos.y);
        }
        else if(listener.isPressed(GLFW_KEY_D) || listener.isPressed(GLFW_KEY_RIGHT)){

            PlayerPos = new Vector2f(PlayerPos.x+PlayerSpeed, PlayerPos.y);
        }
        return PlayerPos;
    }
}
