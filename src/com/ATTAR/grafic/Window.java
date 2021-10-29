package com.ATTAR.grafic;

import com.ATTAR.defaultes.FrameLimiter;
import com.ATTAR.defaultes.KeyListener;
import org.lwjgl.openal.*;
import org.joml.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import com.ATTAR.scenes.*;
import org.lwjgl.opengl.GLCapabilities;

import java.awt.*;


public class Window {
	private long win;
	private Vector2i size;
	private float playerspeed = 1f;
	private KeyListener keyListener;
	private SceneManager scmg;
	private long audioContext;
	private long audioDevice;

	public Window(String name, Vector2i size, boolean fullscrean, int i) {

		this.size = size;


		
//		getvideomode primary monitor
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
//		inicialize fullscreen
		long fullscreanl = 0;
//		if is fullscreen true set up fullscrean
		if(fullscrean) {
			fullscreanl = glfwGetPrimaryMonitor();
			size = new Vector2i(vid.width(), vid.height());
		}
		
//		set window hint to default
		glfwDefaultWindowHints();
//		set window resizible to false
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
//		creating window
		win = glfwCreateWindow(size.x, size.y, name, fullscreanl, 0);
		keyListener = new KeyListener(win);
//		make context because u must

		glfwMakeContextCurrent(win);
		glfwSwapInterval(1);
//		defines capabilities to openGL context
		GLCapabilities capabilities = GL.createCapabilities();
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		audioDevice = alcOpenDevice(defaultDeviceName);

		int[] attributes = {0};
		audioContext = alcCreateContext(audioDevice, attributes);
		alcMakeContextCurrent(audioContext);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

		if (!alCapabilities.OpenAL10) {
			assert false : "Audio library not supported.";
		}


		scmg = new SceneManager(new Vector2f(size.x, size.y), win, i);
		scmg.switchScene("menu", null);


	} 
	public Vector2i getSize() {
		return this.size;
	}
	public void update() {
		double Startloop = 0;
		double FirstTick = 0;
		double LastTick = FrameLimiter.getTime();
		double PassedTick = 0;
		double UnprocessedTick = 0;
		double dt = FrameLimiter.secsPerFrame;
		int fps =0;
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		while(!glfwWindowShouldClose(win)) {
			if (Startloop >=1) {
				System.out.println(Math.round(fps/Startloop));

				Startloop = 0;
				fps = 0;
			}
			FirstTick = FrameLimiter.getTime();
			PassedTick = FirstTick - LastTick;
			LastTick = FirstTick;
			UnprocessedTick += PassedTick;
			Startloop += PassedTick;



			while (UnprocessedTick>= dt) {
				//			thats must by here
				glfwPollEvents();
				glClear(GL_COLOR_BUFFER_BIT);
				glClearColor(1,0,0,1);
				scmg.update(dt);
				UnprocessedTick -=dt;

				fps++;
				glfwSwapBuffers(win);
			}
//			swap buffers

			
		}
		alcDestroyContext(audioContext);
		alcCloseDevice(audioDevice);

		// Free the memory
		glfwFreeCallbacks(win);
		glfwDestroyWindow(win);
		
	}
}
