package com.ATTAR.defaultes;






import org.joml.*;
import static org.lwjgl.glfw.GLFW.*;


public class ButtonListener {





    private long win;
    private Vector2i buttons;
    private int ButtonId;
    private KeyListener keyListener;
    private boolean enter;

    public ButtonListener(long win, Vector2i buttons) {
        this.win = win;
        keyListener = new KeyListener(win);
        ButtonId = 0;
        enter = false;
        this.buttons = buttons;
    }
    public int getButtonId(){
        return ButtonId;
    }
    public boolean isPressedEnter(){

        return keyListener.isPressed(GLFW_KEY_ENTER);
    }
    public void update(){
        if (keyListener.isPressedOnce(GLFW_KEY_UP)){

            if (ButtonId+1 <= buttons.y * buttons.x - 1){
                ButtonId++;
            }
            else {
                ButtonId -= buttons.y-1;
            }
        }
        else if (keyListener.isPressedOnce(GLFW_KEY_DOWN)){
            if (ButtonId-1 >= 0){
                ButtonId--;
            }
            else {
                ButtonId += buttons.y-1;
            }
        }




    }


}
