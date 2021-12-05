package com.ATTAR.grafic;

import org.joml.*;

public class Camera {
    private Matrix4f projectionMatrix, viewMatrix;
    private Vector2f position;
    private Vector2f size;
    
    public Vector2f getCamPos() {
    	return position;
    }
    
    public void setCamPos(Vector2f pos) {
    	position = pos;
    }
    
    
    
    public Vector2f getSize() {
		return size;
	}



	public Camera(Vector2f position, int i) {
        this.position = position;
        if (i<4){

            this.size = new Vector2f(1920,1080
            );
        }
        else {
            this.size = new Vector2f(1024, 768);
        }
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();

    }

    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, size.x, 0.0f, size.y, 1.0f, 100.0f);

    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                                        cameraFront.add(position.x, position.y, 0.0f),
                                        cameraUp);

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
	
	
	
	

}
