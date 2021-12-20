package com.ATTAR.grafic;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ATTAR.defaultes.AssetsPool;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;


public class Texture {

	
	private int TexID;
	private String FilePath;
	private Vector2i TexSize = new Vector2i();
	private List<Integer> TexIDList = new ArrayList<>();

	public void genAnimation(String FilePath) throws IOException {
		this.FilePath = FilePath;
		BufferedImage ImageF = ImageIO.read(new File(FilePath));
		BufferedImage image;
		IntBuffer Width = BufferUtils.createIntBuffer(1);
		IntBuffer Height = BufferUtils.createIntBuffer(1);
		IntBuffer Chanels = BufferUtils.createIntBuffer(1);

		ByteBuffer byteImage = stbi_load(FilePath, Width, Height, Chanels, 4);

		ByteBuffer buffer = BufferUtils.createByteBuffer(32 * Chanels.get() * ImageF.getHeight()); //4 for RGBA, 3 for RGB

		TexSize = new Vector2i(ImageF.getWidth(), ImageF.getHeight());

			for (int j = 0; j < TexSize.y / 32; j++) {

				for (int i = 0; i < TexSize.x / 32; i++) {
					image = ImageF.getSubimage(i*32,j*32,32,32);

					int[] pixels = new int[image.getWidth() * image.getHeight()];
					image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());


					for(int y = 0; y < image.getHeight(); y++){
						for(int x = 0; x < image.getWidth(); x++){
							int pixel = pixels[y * image.getWidth() + x];
							buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
							buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
							buffer.put((byte) (pixel & 0xFF));	            // Blue component
							buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
						}
					}

					buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

					// You now have a ByteBuffer filled with the color data of each pixel.
					// Now just create a texture ID and bind it. Then you can load it using
					// whatever OpenGL method you want, for example:
					TexID = glGenTextures();


					glBindTexture(GL_TEXTURE_2D, TexID);

					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);

					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
					if (byteImage != null) {
						if (Chanels.get(0) == 4) {

							glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
						} else if (Chanels.get(0) == 3) {
							glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image.getWidth(), image.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
						}

					}
					TexIDList.add(TexID);
				}
			}
			AssetsPool.addTexList(TexIDList,FilePath.split("/")[FilePath.split("/").length-1].replace(".png",""));

	}
	public Texture(String FilePath) throws IOException {


		this.FilePath = FilePath;
		BufferedImage ImageF = ImageIO.read(new File(FilePath));
		BufferedImage image;
		IntBuffer Width = BufferUtils.createIntBuffer(1);
		IntBuffer Height = BufferUtils.createIntBuffer(1);
		IntBuffer Chanels = BufferUtils.createIntBuffer(1);

		ByteBuffer byteImage = stbi_load(FilePath, Width, Height, Chanels, 4);

		ByteBuffer buffer = BufferUtils.createByteBuffer(32 * Chanels.get() * ImageF.getHeight()); //4 for RGBA, 3 for RGB

		TexSize = new Vector2i(ImageF.getWidth(), ImageF.getHeight());
		if (TexSize.x>32) {
			for (int j = 0; j < TexSize.y / 32; j++) {

				for (int i = 0; i < TexSize.x / 32; i++) {
					image = ImageF.getSubimage(i*32,j*32,32,32);

					int[] pixels = new int[image.getWidth() * image.getHeight()];
					image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());


					for(int y = 0; y < image.getHeight(); y++){
						for(int x = 0; x < image.getWidth(); x++){
							int pixel = pixels[y * image.getWidth() + x];
							buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
							buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
							buffer.put((byte) (pixel & 0xFF));	            // Blue component
							buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
						}
					}

					buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

					// You now have a ByteBuffer filled with the color data of each pixel.
					// Now just create a texture ID and bind it. Then you can load it using
					// whatever OpenGL method you want, for example:
					TexID = glGenTextures();


					glBindTexture(GL_TEXTURE_2D, TexID);

					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);

					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

					glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
					if (byteImage != null) {
						if (Chanels.get(0) == 4) {

							glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
						} else if (Chanels.get(0) == 3) {
							glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, image.getWidth(), image.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
						}

					}
					TexIDList.add(TexID);
				}
			}
		AssetsPool.addTexList(TexIDList,FilePath.split("/")[FilePath.split("/").length-1].replace(".png",""));
		}
		else {


			TexID = glGenTextures();


			glBindTexture(GL_TEXTURE_2D, TexID);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			if (byteImage != null) {
				if (Chanels.get(0) == 4) {

					glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, TexSize.x, TexSize.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteImage);
				} else if (Chanels.get(0) == 3) {
					glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, TexSize.x, TexSize.y, 0, GL_RGB, GL_UNSIGNED_BYTE, byteImage);
				}

			}


		}
		unbind();
	}

	
	

	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, TexID);
	}
	public void bind(int TexID) {
		glBindTexture(GL_TEXTURE_2D, TexID);
	}
	public int getTexID() {
		return TexID;
	}
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}
