package cn.tinbat.blocks;

import cn.tinbat.constant.SysConstant;
import cn.tinbat.math.Vector;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 * Created by tincat on 2017/10/5.
 */
public class Blocks extends SimpleApplication implements ActionListener {
    private BulletAppState bulletAppState;
    private RigidBodyControl landscape;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;
    //Temporary vectors used on each frame.
    //They here to avoid instanciating new vectors on each frame
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private double time = 0;
    boolean delFlag = false;

    @Override
    public void simpleInitApp() {
        //初始化相机
        cam.setLocation(new Vector3f(0, 4f, 6f));
        cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        // 初始化光照
        initLight();

        // 初始化场景
        initScene();
        flyCam.setMoveSpeed(10);

        //初始化玩家
        initPlayer();

        setUpKeys();

        // 开启调试模式，这样能够可视化观察物体之间的运动。
        //bulletAppState.setDebugEnabled(true);
    }

    private void initPlayer() {

        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setGravity(30);
        player.setPhysicsLocation(new Vector3f(1, 10, 1));
        bulletAppState.getPhysicsSpace().add(player);
    }

    /**
     * 初始化光照
     */
    private void initLight() {
        // 环境光
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.3f, 0.3f, 0.3f, 1f));

        // 阳光
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-2, -2, -3).normalizeLocal());

        rootNode.addLight(ambient);
        rootNode.addLight(sun);
    }

    /**
     * 初始化场景
     */
    private void initScene() {
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        makeFloor();
        makeCage();
    }

    /**
     * 在指定位置放置一个物理方块
     *
     * @param vector 方块的位置
     */
    private void makeCube(Vector vector) {
        System.out.println("makeCube:" + vector.toString());
        // 网格
        Box box = new Box(SysConstant.Public.CUBE_SIZE, SysConstant.Public.CUBE_SIZE, SysConstant.Public.CUBE_SIZE);
        box.scaleTextureCoordinates(new Vector2f(1f, .5f));

        // 材质
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture("Textures/Terrain/Pond/Pond.jpg");

        // 间隔材质便于观察
        if (vector.isOdd()) {
            mat.setTexture("ColorMap", tex);
        } else {
            mat.setColor("Color", ColorRGBA.White);
        }

        // 几何体
        Geometry geom = new Geometry("cube" + vector.name(), box);
        geom.setMaterial(mat);
        Vector3f loc = vector.toCubeLocation();
        geom.setLocalTranslation(loc);// 把方块放在指定位置

        // 刚体
        RigidBodyControl rigidBody = new RigidBodyControl(0f);
        geom.addControl(rigidBody);
        rigidBody.setCollisionShape(new BoxCollisionShape(new Vector3f(SysConstant.Public.CUBE_SIZE, SysConstant.Public.CUBE_SIZE, SysConstant.Public.CUBE_SIZE)));

        rootNode.attachChild(geom);
        bulletAppState.getPhysicsSpace().add(rigidBody);
    }

    private void deleteCube(Vector vector) {
        System.out.println("deleteCube:" + vector.toString());
        Geometry geom = (Geometry) rootNode.getChild("cube" + vector.name());
        bulletAppState.getPhysicsSpace().remove(geom);
        rootNode.detachChildNamed("cube" + vector.name());
    }

    /**
     * 建造地板
     */
    void makeFloor() {
        makeCube(new Vector(0, 0, 0));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
//                for (int k = 0; k < 10; k++) {
//                    Vector vt = new Vector(i, -k, j);
//                    makeCube(vt);
//                }
                Vector vt = new Vector(i, 0, j);
                makeCube(vt);
                makeCube(vt.reverseX());
                makeCube(vt.reverseZ());
                makeCube(vt.reverseZ().reverseX());

            }
        }
    }

    void makeCage() {
        Vector vector = new Vector();
        for (Vector v : vector.toCubeVectors(16)
                ) {
            makeCube(v);
        }
    }

    /**
     * We over-write some navigational key mappings here, so we can
     * add physics-controlled walking and jumping:
     */
    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
    }

    /**
     * These are our custom actions triggered by key presses.
     * We do not walk yet, we just keep track of the direction the user pressed.
     */
    public void onAction(String binding, boolean isPressed, float tpf) {
        if (binding.equals("Left")) {
            left = isPressed;
        } else if (binding.equals("Right")) {
            right = isPressed;
        } else if (binding.equals("Up")) {
            up = isPressed;
        } else if (binding.equals("Down")) {
            down = isPressed;
        } else if (binding.equals("Jump")) {
            if (isPressed) {
                player.jump();
            }
        }
    }

    /**
     * This is the main event loop--walking happens here.
     * We check in which direction the player is walking by interpreting
     * the camera direction forward (camDir) and to the side (camLeft).
     * The setWalkDirection() command is what lets a physics-controlled player walk.
     * We also make sure here that the camera moves with player.
     */
    @Override
    public void simpleUpdate(float tpf) {
        time += tpf;
        if (!delFlag && time > 10f) {
            deleteCube(new Vector(1, 0, 1));
            deleteCube(new Vector(1, 0, -1));
            deleteCube(new Vector(-1, 0, 1));
            deleteCube(new Vector(-1, 0, -1));
            delFlag = true;
        }
        camDir.set(cam.getDirection()).multLocal(0.6f);
        camLeft.set(cam.getLeft()).multLocal(0.4f);
        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        player.setWalkDirection(walkDirection);
        cam.setLocation(player.getPhysicsLocation());
    }

    public static void main(String[] args) {
        Blocks app = new Blocks();
        app.start();
    }
}
