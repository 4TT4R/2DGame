package com.ATTAR.objects;

import java.util.HashMap;

import com.ATTAR.grafic.*;
import org.joml.*;

import com.ATTAR.components.*;

public class Tiles {


	private Camera cam;
	private Vector3f Scale;
	private Vector2f Size;

	private CompRender render;
	private HashMap<String, Boolean> properties = new HashMap<>();
    private HashMap<String, Boolean> Killing = new HashMap<>();

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
	private String Texture, type;
	private boolean Solid, animated, triger;
	private int ID, fps;

	public boolean isAnimated() {
		return animated;
	}

	public boolean isKilling(String key) {
		return Killing.get(key);
	}

	public HashMap<String, Boolean> getKilling() {
        return Killing;
    }

	public boolean isSolid() {
		return Solid;
	}

	public boolean isTriger() {
		return triger;
	}

	public String getTexture() {
		return Texture;
	}

	public String getType() {
		return type;
	}

	public Vector4f getAABB() {
		return AABB;
	}

	public Tiles(Tiles tile, Camera cam) {
		this.cam = cam;
		this.AABB = tile.getAABB();
		this.Texture = tile.getTexture();
		this.Killing = tile.getKilling();
		this.Solid = tile.isSolid();
		this.type = tile.getType();
		this.animated = tile.isAnimated();
		this.triger = tile.isTriger();
	}
	public Tiles(Vector4f AABB, String Texture,Vector2f size, boolean Solid, boolean triger,  HashMap<String, Boolean> Killing, boolean animated, String type, int fps, int ID) {
		this.AABB = AABB;
		this.Texture = Texture;
		this.Killing = Killing;
		this.Solid = Solid;
		this.animated = animated;
		this.type = type;
		this.triger = triger;
		this.ID = ID;
		this.fps = fps;
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






