package com.ATTAR.defaultes;

import org.joml.Vector2f;
import org.lwjgl.glfw.*;




import org.lwjgl.glfw.*;

public class CursorInput extends GLFWCursorPosCallback{

    private static long win;
    private Vector2f pos = new Vector2f();

    @Override
    public void invoke(long window, double x, double y) {
        this.win = window;
        pos.set((float)x, Collector.getWinSize().y-(float)y);
        Collector.setCursorPos(pos);

    }
    public static boolean isPressed(int key){
        return GLFW.glfwGetMouseButton(win, key) == GLFW.GLFW_TRUE;
    }

}
