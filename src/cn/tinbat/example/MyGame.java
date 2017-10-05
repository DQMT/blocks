package cn.tinbat.example;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;


public class MyGame extends SimpleApplication{
	private Spatial sceneModel;
	private BulletAppState bulletAppState;
	private RigidBodyControl landscape;
	private CharacterControl player;
	private Vector3f walkDirection = new Vector3f();
	private boolean left = false, right = false, up = false, down = false;

	@Override
	public void simpleInitApp() {
		bulletAppState = new BulletAppState();
//		Mesh box = new Box(1,1,1);
		Sphere sphere = new Sphere(12, 20, 1);
		Geometry geometry = new Geometry("box",sphere);
		//Material material = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
		Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
		//material.setColor("Color", new ColorRGBA(1, 0, 0, 1));
		material.setColor("Ambient",ColorRGBA.Red);
		material.setColor("Diffuse",ColorRGBA.Blue);
//		material.getAdditionalRenderState().setWireframe(true);
		geometry.setMaterial(material);
		rootNode.attachChild(geometry);

		AudioNode audioNode = new AudioNode(assetManager,"Sound/Environment/Nature.ogg", AudioData.DataType.Buffer);
		audioNode.setPositional(false);
		audioNode.play();
		rootNode.attachChild(audioNode);



		AmbientLight ambientLight = new AmbientLight();
		//rootNode.addLight(ambientLight);

		DirectionalLight sun = new DirectionalLight(new Vector3f(1, 1, 1));
		rootNode.addLight(sun);

		flyCam.setMoveSpeed(10);

	}
	
	public static void main(String[] args) {

		MyGame game = new MyGame();
		game.start();
	}

	
}
