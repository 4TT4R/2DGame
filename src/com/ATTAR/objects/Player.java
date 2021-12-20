package com.ATTAR.objects;

import com.ATTAR.grafic.*;
import org.joml.*;
import com.ATTAR.components.CompRender;

import java.io.IOException;
import java.util.HashMap;

public class Player {
	private Vector3f Scale;
	private Vector2f Size;
	private float hp;
	private Animation an;
	private HashMap<String, Integer> Animations;
	private CompRender render;
	private Texture tx;
	int idleID;
	public Player(Camera cam) {
		an = new Animation(true);

		setSize(new Vector2f(38,50));
		Scale = new Vector3f(1);
		hp = 3;
		render = new CompRender();
		try {
			tx = new Texture("./Assets/Tiles/Player_idle.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		idleID = tx.getTexID();
		try {
			tx.genAnimation("./Assets/Tiles/Player_Walk_R.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		render.init(shader,tx, cam, true);
	}
	private Shader shader = new Shader("Shaders/Default.glsl");

	
	public void update(Vector2f Vector) {
		if (Vector.x != 0 || Vector.y > 0 || Vector.y < -0.4167f) {
			render.Update(an,"Player_Walk_R", Scale, 7);
		}
		else {
			render.Update(Scale, idleID);
		}
		setPos(new Vector2f(getPos().x+Vector.x, getPos().y+Vector.y));
		
	}
	public void setPos(Vector2f pos) {
		render.setPos(pos);
		
	}
	public void setHp(float HP) {
		this.hp = HP;
	}
	public float getHp() {
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
