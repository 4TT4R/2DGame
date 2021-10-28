package com.ATTAR.components;

import org.joml.*;

import com.ATTAR.grafic.Component;

public class CompCollision extends Component {

	private Vector2f pos;
	private boolean Static; 
	
	
	
	

	
	public void Colinit(Vector2f pos, boolean Static) {
		this.pos = pos;
		this.Static = Static;
	}


	public void update() {
		if(!Static) {
			this.pos = GetCamPos();
		}
		
		
	}
	public void SetCamPos(Vector2f pos){
		
	}
	public Vector2f GetCamPos(){
		return pos;
	}

	

}
