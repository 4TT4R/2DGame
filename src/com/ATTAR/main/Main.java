package com.ATTAR.main;

import com.ATTAR.grafic.*;
import org.joml.*;
import javax.swing.*;
import java.awt.event.*;
import static org.lwjgl.glfw.GLFW.*;


public class Main {


	public static void main(String[] args) {
		if(!glfwInit()) {
			throw new IllegalStateException("glfw initialization fail");
		}
		Settings settings = new Settings();

	}
	public static void createWindow(boolean fullscreen, int x, int y, JFrame frame, int i) {
		Window window = new Window("Game Name", new Vector2i(x,y), fullscreen, i);
		window.update();
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}
