package com.ATTAR.objects;

import java.util.HashMap;

import com.ATTAR.grafic.*;
import org.joml.*;

import com.ATTAR.components.*;

public class Tiles {


	private final Camera cam;
	private Vector3f Scale;
	private Vector2f Size;
	private int type;
	private CompRender render;
	private HashMap<String, Boolean> properties = new HashMap<>();
	public HashMap<String, Boolean> GetInfo() {
		return properties;
	}
	public void SetProperties(int type) {
		switch (type) {
		case 1:

			
		break;
		case 2: 
			
		break;
		case 3: 
			
		break;
		default: 
			System.out.println("Type: " + type + " is not definited");
		break;
		}
	}

	public void setScale(Vector3f Scale) {
		this.Scale = Scale;
	}
	public Vector3f getScale() {
		return this.Scale;
	}
	
	
	public Tiles(Camera cam) {
		this.cam = cam;
	}
	private Shader shader = new Shader("Shaders/Default.glsl");
	public void init( int Type){
		Scale = new Vector3f(1);
		this.type =Type;
		SetProperties(type);
		setSize(new Vector2f(100));
		render = new CompRender();
		render.init(shader,"./Assets/Tiles/Brick.png", cam);

	}
	
	public void update() {
		render.Update(Scale);
	}
	
	public void setPos(Vector2f pos) {
		
		render.setPos(pos);
	
	}
	public Vector2f getPos() {
		
		return render.getPos();
	
	}
	public Vector2f getSize() {
		return Size;
	}
	public void setSize(Vector2f size) {
		Size = size;
	}
	
	
	
}






