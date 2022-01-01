package com.ATTAR.objects;

import java.util.HashMap;

import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.grafic.*;
import org.joml.*;

import com.ATTAR.components.*;

public class Tiles {


	private Camera cam;
	private Vector3f Scale;
	private Vector2f Size;
	private Animation an;

	public CompRender getRender() {
		return render;
	}

	public void setRender(CompRender render) {
		this.render = render;
	}

	private CompRender render;
	private HashMap<String, Boolean> properties = new HashMap<>();
    private HashMap<String, Boolean> Killing = new HashMap<>();


	public void setScale(Vector3f Scale) {
		this.Scale = Scale;
	}
	public Vector3f getScale() {
		return this.Scale;
	}
	private Vector4f AABB;
	private String Texture, type;
	private boolean Solid, animated, triger, startAn;
	private int ID, fps;

	public void setStartAn(boolean startAn) {
		this.startAn = startAn;
	}

	public int getID() {return  ID;}

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

    public Camera getCam() {
        return cam;
    }

	public void setCam(Camera cam) {
		this.cam = cam;
	}

	public void destroy() {
		if (render != null) {
			render.destroy();
		}
		cam = null;
		render = null;
		AABB = null;
		Texture = null;
		type = null;
		properties = null;
		Killing = null;
		an = null;
		Size = null;
		Scale = null;
		shader = null;
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
		this.ID = tile.ID;
		this.fps = tile.fps;
		startAn =true;
		an = new Animation(false);


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
		AssetsPool.addFPS(getTexture(),fps);

	}
	private Shader shader = new Shader("Shaders/Default.glsl");
	public void init(){
		Scale = new Vector3f(1);
		setSize(new Vector2f(100));
		render = new CompRender();
		render.init(shader,Texture, cam, animated);

	}
	
	public void update() {
		if (!animated){
			render.Update(Scale);
		}
		if (animated && startAn) {
			render.Update(an,getTexture().replace(".png", ""), Scale, AssetsPool.getFPS(getTexture()),AssetsPool.getTexList(getTexture().replace(".png","")).size());
		}
		else if(animated && !startAn) {
			render.Update(Scale);
		}
	}
	
	public void setPos(float x , float y) {

		render.setPos(x,y);

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






