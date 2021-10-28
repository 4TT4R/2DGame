package com.ATTAR.grafic;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.*;

import org.lwjgl.BufferUtils;


public class Texture {

	
	private int TexID;
	private String FilePath;
	
	
	public Texture(String FilePath) {
	this.FilePath = FilePath;
	
	TexID = glGenTextures();
	
	glBindTexture(GL_TEXTURE_2D, TexID);
	
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
	
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	
	IntBuffer Width = BufferUtils.createIntBuffer(1);
	IntBuffer Height = BufferUtils.createIntBuffer(1);
	IntBuffer Chanels = BufferUtils.createIntBuffer(1);
	
	ByteBuffer image = stbi_load(FilePath, Width, Height, Chanels, 4);
	
	if(image != null) {
		if (Chanels.get(0)==4) {
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, Width.get(), Height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
		}
		
		else if (Chanels.get(0)==3) {
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, Width.get(), Height.get(), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
		}

		
	}
	
//	stbi_image_free(image);
	
	
	
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, TexID);
	}
	public void bind(int TexID) {
		glBindTexture(GL_TEXTURE_2D, TexID);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
