package cn.tinbat.blocks.creature;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;

public abstract class Creature {

    public RigidBodyControl getRigidBody() {
        return rigidBody;
    }

    protected RigidBodyControl rigidBody;

    protected Geometry geometry;

    protected Vector3f location;

    public Vector3f getLocation() {
        return location;
    }

    public Geometry getGeometry() {

        return geometry;
    }

    public Creature(Vector3f location){
        this.location = location;
    }

    protected Geometry shape;

    protected Material material;

    protected Texture texture;

    public abstract void init(AssetManager assetManager);

    public abstract void sense();

    public abstract void attack();

}
