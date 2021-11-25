package com.ATTAR.objects;

import java.util.HashMap;

import com.ATTAR.grafic.*;
import org.joml.*;

import com.ATTAR.components.*;

public class Tiles {


	private Camera cam;
	private Vector3f Scale;
	private Vector2f Size;
	private int type;
	private CompRender render;
	private HashMap<String, Boolean> properties = new HashMap<>();
	public HashMap<String, Boolean> GetInfo() {
		return properties;
	}


	public void setScale(Vector3f Scale) {
		this.Scale = Scale;
	}
	public Vector3f getScale() {
		return this.Scale;
	}
	private Vector4f AABB;
	private String Texture;
	private boolean Killing, Solid, animated;

	public boolean isAnimated() {
		return animated;
	}

	public boolean isKilling() {
		return Killing;
	}

	public boolean isSolid() {
		return Solid;
	}

	public String getTexture() {
		return Texture;
	}

	public Vector4f getAABB() {
		return AABB;
	}

	public Tiles(Tiles tile, Camera cam) {
		this.cam = cam;
		this.AABB = tile.getAABB();
		this.Texture = tile.getTexture();
		this.Killing = tile.isKilling();
		this.Solid = tile.isSolid();
		System.out.println(isSolid());
		this.animated = tile.isAnimated();
	}
	public Tiles(Vector4f AABB, String Texture,Vector2f size, boolean Solid, boolean Killing, boolean animated) {
		this.AABB = AABB;
		this.Texture = Texture;
		this.Killing = Killing;
		this.Solid = Solid;
		this.animated = animated;

	}
	private Shader shader = new Shader("Shaders/Default.glsl");
	public void init(){
		Scale = new Vector3f(1);
		setSize(new Vector2f(100));
		render = new CompRender();
		render.init(shader,"./Assets/Tiles/"+Texture, cam, animated);

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






