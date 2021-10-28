package com.ATTAR.defaultes;

import static org.lwjgl.glfw.GLFW.*;
import java.util.HashMap;

public class KeyListener {





    private long win;
    private int[] alreadyPressed = new int[300];
    public KeyListener(long win) {
        this.win = win;
    }

    private void setAlreadyPressed(int Key) {
        if (glfwGetKey(win, Key ) == GLFW_TRUE) {
            alreadyPressed[Key] = GLFW_TRUE;

        }
        else {
            alreadyPressed[Key] = GLFW_FALSE;

        }
    }

    public boolean isPressed(int Key){
        return glfwGetKey(win,Key) == GLFW_TRUE;
    }

    public boolean isPressedOnce(int Key) {

        if (alreadyPressed[Key] == GLFW_FALSE && isPressed(Key)){
            setAlreadyPressed(Key);
            return false;
        }

        else if (alreadyPressed[Key] == GLFW_TRUE && isReleased(Key)){
            setAlreadyPressed(Key);
            return true;
        }
        else {
            return false;
        }

    }
    public boolean isReleased(int Key) {

        return glfwGetKey(win,Key) == GLFW_FALSE;
    }


}
