package com.ATTAR.components;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;
import java.nio.*;
import java.util.Arrays;

import com.ATTAR.fonts.Sdf;
import org.joml.*;
import org.lwjgl.BufferUtils;

import com.ATTAR.grafic.*;

public class CompRender  {
	
	

	public Vector4f color;
	Camera cam;
	private Vector2f pos;
	private Vector3f scale;
	private int i;

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

	float[] Verticies;
	public CompRender() {
		i = 0;

		
		float[] Verticies = {

				// Vertices						//Color				//Tex_Vertices
				0, 		100, 	0, 				1f, 0f, 0f, 1f,		0,0,		//0	left_top
				100,	100, 	0,				0f, 1f, 0f, 1f,		1,0,		//1	right_top
				100, 	0,		0,				0f, 0f, 1f, 1f,		1,1,		//2	right_bottom
				0, 		0, 		0,				1f, 1f, 0f, 1f,		0,1			//3	left_bottom
		};
		this.Verticies = Verticies;
	}

	private static int[] Elements = {
		0,1,2,2,3,0
	};
	private boolean animated;
	private Shader shader;
	private Animation an;
	private Texture test_Tex;
	private int vaoID, vboID, eboID;
	private Sdf sdf;
	public void setPos(Vector2f pos) {
		this.pos = pos;
	}
	
	public Vector2f getPos() {
		return this.pos;
	}
	public void SetCamPos(Vector2f pos) {
		cam.setCamPos(pos);
	}
	public Vector2f GetCamPos() {
		return cam.getCamPos();
	}

	public void init(Shader shader, Camera cam, Sdf sdf) {
		this.cam = cam;
		this.sdf = sdf;
		this.shader = shader;

	}
	
	public void init(Shader shader, Camera cam) {
		this.cam = cam;
		this.shader = shader;
		color = new Vector4f(1,0,0,1);






//		generate vertex arrays
		vaoID = glGenVertexArrays();
//		bind  vertex array to vaoID
		glBindVertexArray(vaoID);
//		crate float buffer
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(Verticies.length);
//		put Verticies in to vertexBuffer and flip them
		vertexBuffer.put(Verticies).flip();
//		generate buffer object with name vboID
		vboID = glGenBuffers();
//		bind buffer object vboID
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
//		buffered data to currently bind buffer object
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
//		create integer buffer
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(Elements.length);
//		put elements to elementBuffer and flip them
		elementBuffer.put(Elements).flip();
//		generate buffer object with name eboID
		eboID = glGenBuffers();
//		bind buffer object eboID
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
//		buffered data to currently bind buffer object
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
//		setting up some integers
//		how many numbers have position
		int positionSize = 3;
//		how many numbers have color
		int colorSize = 4;

//		how many numbers have color
		int texSize = 2;
//		how many bytes have float in java
		int floatSizeBytes = Float.BYTES;
//


		int vertexSizeBytes = (positionSize + colorSize + texSize) * floatSizeBytes;

		glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes , 0);
		glEnableVertexAttribArray(0);

		glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes , positionSize*floatSizeBytes);
		glEnableVertexAttribArray(1);

		glVertexAttribPointer(2, texSize, GL_FLOAT, false, vertexSizeBytes , (positionSize + colorSize)*floatSizeBytes);
		glEnableVertexAttribArray(2);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	}
	public void init(Shader shader, String Tex_path, Camera cam, boolean animated) {
		i = 0;
		this.animated = animated;
		this.cam = cam;
		this.shader = shader;
		color = new Vector4f(1,0,0,1);


		try {
			test_Tex = new Texture(Tex_path);
		} catch (IOException e) {
			e.printStackTrace();
		}



//		generate vertex arrays
		vaoID = glGenVertexArrays();
//		bind  vertex array to vaoID
		glBindVertexArray(vaoID);
//		crate float buffer
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(Verticies.length);
//		put Verticies in to vertexBuffer and flip them
		vertexBuffer.put(Verticies).flip();
//		generate buffer object with name vboID
		vboID = glGenBuffers();
//		bind buffer object vboID
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
//		buffered data to currently bind buffer object
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
//		create integer buffer
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(Elements.length);
//		put elements to elementBuffer and flip them
		elementBuffer.put(Elements).flip();
//		generate buffer object with name eboID
		eboID = glGenBuffers();
//		bind buffer object eboID
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
//		buffered data to currently bind buffer object
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
//		setting up some integers
//		how many numbers have position
		int positionSize = 3;
//		how many numbers have color
		int colorSize = 4;

//		how many numbers have color
		int texSize = 2;
//		how many bytes have float in java
		int floatSizeBytes = Float.BYTES;
//		


		int vertexSizeBytes = (positionSize + colorSize + texSize) * floatSizeBytes;
		
		glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes , 0);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes , positionSize*floatSizeBytes);
		glEnableVertexAttribArray(1);
		
		glVertexAttribPointer(2, texSize, GL_FLOAT, false, vertexSizeBytes , (positionSize + colorSize)*floatSizeBytes);
		glEnableVertexAttribArray(2);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	}

	

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public void Update(Vector3f scale) {

		shader.use();
		shader.uploadTexture("TexSampler", 0);
		shader.uploadMat4f("projection", cam.getProjectionMatrix());
		shader.uploadMat4f("wiev", cam.getViewMatrix());
		shader.uploadVec4f("uColor", color);
		shader.uploadVec3f("uScale", scale);
		shader.uploadVec2f("uPos", this.pos);



		glActiveTexture(GL_TEXTURE0);
		if (this.test_Tex != null) {
			test_Tex.bind();
		}
		glBindVertexArray(vaoID);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, Elements.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		glBindVertexArray(0);
		if (this.test_Tex != null) {
			test_Tex.unbind();
		}
		shader.detach();

	}
	public void Update(Animation an, String anName, Vector3f scale, int fps) {
		i++;
		shader.use();
		shader.uploadTexture("TexSampler", 0);
		shader.uploadMat4f("projection", cam.getProjectionMatrix());
		shader.uploadMat4f("wiev", cam.getViewMatrix());
		shader.uploadVec4f("uColor", color);
		shader.uploadVec3f("uScale", scale);
		shader.uploadVec2f("uPos", this.pos);


		glActiveTexture(GL_TEXTURE0);
		if (this.test_Tex != null) {
			an.setTex(anName);
			test_Tex.bind(an.update(fps));
		}

		glBindVertexArray(vaoID);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glDrawElements(GL_TRIANGLES, Elements.length, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);

		glBindVertexArray(0);
		if (this.test_Tex != null) {
			test_Tex.unbind();
		}
		shader.detach();

	}


	public void Update(String msg,Vector2f pos, float scale, Vector4f color,int view) {


        sdf.RenderText(shader,msg,pos.x,pos.y, scale, color, cam.getProjectionMatrix(), cam.getViewMatrix(),view);


	}




}
