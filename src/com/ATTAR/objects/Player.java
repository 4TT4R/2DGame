package com.ATTAR.objects;

import com.ATTAR.grafic.*;
import org.joml.*;
import com.ATTAR.components.CompRender;
public class Player {
	private Vector3f Scale;
	private Vector2f Size;
	private int hp;
	
	private CompRender render;
	public Player(Camera cam) {
		setSize(new Vector2f(38,50));
		Scale = new Vector3f(1);
		hp = 3;
		render = new CompRender();
		render.init(shader,"./Assets/Tiles/PLA2.png", cam);
	}
	private Shader shader = new Shader("Shaders/Default.glsl");

	
	public void update() {
		render.Update(Scale);

		
	}
	public void setPos(Vector2f pos) {
		render.setPos(pos);
		
	}
	public void setHp(int HP) {
		this.hp = HP;
	}
	public int getHp() {
		return this.hp;
	}
	public Vector2f getPos() {
		return render.getPos();
	}
	public void setScale(Vector3f Scale) {
		this.Scale = Scale;
	}
	public Vector3f getScale() {
		return this.Scale;
	}

	public Vector2f getSize() {
		return Size;
	}

	public void setSize(Vector2f size) {
		Size = size;
	}
	
}
