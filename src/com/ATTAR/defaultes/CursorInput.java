package com.ATTAR.defaultes;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.*;




import org.lwjgl.glfw.*;

public class CursorInput extends GLFWCursorPosCallback{

    private static long win;
    private Vector2f pos = new Vector2f();

    @Override
    public void invoke(long window, double x, double y) {
        this.win = window;
        pos.set((float) x,(float) y);
//        pos.set(Collector.getCamSize().x/Collector.getWinSize().x*(float)x+Collector.getCamPos().x,
//                Collector.getCamPos().y+Collector.getCamSize().y-Collector.getCamSize().y/Collector.getWinSize().y*(float)y);
        Collector.setCursorPos(pos);


    }

    public static boolean isPressed(int key){

        try {

            return glfwGetMouseButton(win, key) == GLFW_TRUE;
        }
        catch (Exception e) {
        }
        return false;
    }

}
