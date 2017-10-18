package cn.tinbat.blocks.creature;

import cn.tinbat.blocks.constant.SysConstant;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class EvilCube extends Creature{

    public EvilCube(Vector3f location) {
        super(location);
    }

    @Override
    public void init(AssetManager assetManager) {
        Box box = new Box(SysConstant.Public.CUBE_RADIUS, SysConstant.Public.CUBE_RADIUS, SysConstant.Public.CUBE_RADIUS);
        box.scaleTextureCoordinates(new Vector2f(1f, .5f));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        Texture texture = assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg");
        mat.setColor("Color", ColorRGBA.Black);
        geometry = new Geometry("EvilCube", box);
        geometry.setMaterial(mat);
        geometry.setLocalTranslation(location);
        CollisionShape creatureShape =
                CollisionShapeFactory.createMeshShape(geometry);
        rigidBody = new RigidBodyControl(creatureShape,0f);
        geometry.addControl(rigidBody);
    }

    @Override
    public void sense() {

    }

    @Override
    public void attack() {

    }
}
