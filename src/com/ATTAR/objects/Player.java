package com.ATTAR.objects;

import com.ATTAR.defaultes.AssetsPool;
import com.ATTAR.grafic.*;
import org.joml.*;
import com.ATTAR.components.CompRender;

import java.io.IOException;
import java.util.HashMap;

public class Player {
	private Vector3f Scale;
	private Vector4f Size;
	private float hp;
	private Animation an;
	private HashMap<String, Integer> Animations;
	private CompRender render,render2;
	private Texture tx,tx2;

	int idleID;
	public Player(Camera cam) {
		an = new Animation(true);

		setSize(new Vector4f((float) Math.floor(28d/32d*100d),(float) Math.floor(31d/32d*100d), (float) Math.floor(4d/32d*100d),0));
		Scale = new Vector3f(1);
		hp = 3;
		render = new CompRender();
		render2 = new CompRender();
		try {
			tx = new Texture("./Assets/Tiles/Player_idle.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			tx2 = new Texture("./Assets/Tiles/Player_idle.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		idleID = tx.getTexID();
		try {
			tx.genAnimation("./Assets/Tiles/Player_Walk_R.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			tx2.genAnimation("./Assets/Tiles/Player_Walk_L.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		render.init(shader,tx, cam, true);
		render2.init(shader,tx2, cam, true);

	}
	private Shader shader = new Shader("Shaders/Default.glsl");

	
	public void update(Vector2f Vector) {
		if (Vector.x > 0 ) {
			render.Update(an,"Player_Walk_R", Scale, 24, AssetsPool.getTexList("Player_Walk_R").size() );
		}
		else if(Vector.x < 0){
			render2.Update(an,"Player_Walk_L", Scale, 24, AssetsPool.getTexList("Player_Walk_L").size() );
		}
//		else if (Vector.y !=0) {
//			render2.Update(an,"Player_Walk_L", Scale, 24, AssetsPool.getTexList("Player_Walk_L").size() );
//
//		}
		else {
			render.Update(Scale, idleID);
		}

		setPos(getPos().x+Vector.x, getPos().y+Vector.y);
	}
	public void setPos(float x , float y) {
		render.setPos(x,y);
		render2.setPos(x,y);

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

	public Vector4f getSize() {
		return Size;
	}

	public void setSize(Vector4f size) {
		Size = size;
	}
	
}
