package com.ATTAR.physic.components;

import com.ATTAR.physic.enums.BodyType;
import org.jbox2d.dynamics.Body;
import org.joml.Vector2f;

public class RigidBody2D {


    private Vector2f bodyPosition = new Vector2f();
    private Vector2f velocity = new Vector2f();
    private float angularDamping = 0.8f;
    private float linearDamping = 0.9f;
    private float mass = 0.0f;
    private BodyType bodyType = BodyType.Dynamic;
    private boolean fixedRotation = false;
    private boolean continuesCollision = true;
    private Body rawBody = null;

    public void update(float dt) {
        if (rawBody != null) {
            setBodyPosition(new Vector2f(rawBody.getPosition().x, rawBody.getPosition().y));
        }
    }
    public Vector2f getBodyPosition() {
        return bodyPosition;
    }

    public void setBodyPosition(Vector2f bodyPosition) {
        this.bodyPosition = bodyPosition;
    }
    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public float getAngularDamping() {
        return angularDamping;
    }

    public void setAngularDamping(float angularDamping) {
        this.angularDamping = angularDamping;
    }

    public float getLinearDamping() {
        return linearDamping;
    }

    public void setLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public boolean isFixedRotation() {
        return fixedRotation;
    }

    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    public boolean isContinuesCollision() {
        return continuesCollision;
    }

    public void setContinuesCollision(boolean continuesCollision) {
        this.continuesCollision = continuesCollision;
    }

    public Body getRawBody() {
        return rawBody;
    }

    public void setRawBody(Body rawBody) {
        this.rawBody = rawBody;
    }
}
