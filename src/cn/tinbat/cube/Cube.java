package cn.tinbat.cube;

import cn.tinbat.constant.SysConstant;
import cn.tinbat.math.MathUtil;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cube implements Savable, Cloneable, Serializable {

    public final int x;
    public final int y;
    public final int z;

    public Hardness hardness;

    public class Hardness {
        public boolean breakable;
        public int hits;
        public Hardness(){
            this.breakable = true;
            this.hits=1;
        }
    }

    public enum AXIS {
        X, Y, Z;
    }

    public static final Cube ZERO = new Cube(0, 0, 0);

    public static Cube parseName(String name) {
        String[] array = name.substring(1, name.length() - 1).split(",");
        return new Cube(Integer.valueOf(array[0]), Integer.valueOf(array[1]), Integer.valueOf(array[2]));
    }

    public static Cube parseLocation(Vector3f vector3f) {
        int intX, intY, intZ;
        if (vector3f.x == 0) {
            intX = 0;
        } else if (vector3f.x > 0) {
            intX = (int) ((vector3f.x + SysConstant.Public.CUBE_RADIUS) / SysConstant.Public.CUBE_SIZE);
        } else {
            intX = (int) ((vector3f.x - SysConstant.Public.CUBE_RADIUS) / SysConstant.Public.CUBE_SIZE);
        }
        if (vector3f.y == 0) {
            intY = 0;
        } else if (vector3f.y > 0) {
            intY = (int) ((vector3f.y + SysConstant.Public.CUBE_RADIUS) / SysConstant.Public.CUBE_SIZE);
        } else {
            intY = (int) ((vector3f.y - SysConstant.Public.CUBE_RADIUS) / SysConstant.Public.CUBE_SIZE);
        }
        if (vector3f.z == 0) {
            intZ = 0;
        } else if (vector3f.z > 0) {
            intZ = (int) ((vector3f.z + SysConstant.Public.CUBE_RADIUS) / SysConstant.Public.CUBE_SIZE);
        } else {
            intZ = (int) ((vector3f.z - SysConstant.Public.CUBE_RADIUS) / SysConstant.Public.CUBE_SIZE);
        }
        return new Cube(intX, intY, intZ);
    }

    public Cube() {
        x = 0;
        y = 0;
        z = 0;
        hardness=new Hardness();
    }

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        hardness=new Hardness();
    }

    @Override
    public String toString() {
        return "[x=" + x + ",y=" + y + ",z=" + z + "]";
    }

    public String name() {
        return "{" + x + "," + y + "," + z + "}";
    }

    @Override
    public void write(JmeExporter jmeExporter) throws IOException {

    }

    @Override
    public void read(JmeImporter jmeImporter) throws IOException {

    }

    public boolean equals(Cube cube) {
        return (cube.x == this.x) && (cube.y == this.y) && (cube.z == this.z);
    }

    public Cube clone() {
        return new Cube(x, y, z);
    }

    public boolean isOdd() {
        return (x + y + z) % 2 != 0;
    }

    /**
     * 返回中心坐标
     *
     * @return
     */
    public Vector3f toCubeLocation() {
        return new Vector3f(x * SysConstant.Public.CUBE_SIZE, y * SysConstant.Public.CUBE_SIZE, z * SysConstant.Public.CUBE_SIZE);
    }

    public Cube reverseX() {
        return new Cube(-x, y, z);
    }

    public Cube reverseY() {
        return new Cube(x, -y, z);
    }

    public Cube reverseZ() {
        return new Cube(x, y, -z);
    }

    public Cube shift() {
        return new Cube(z, x, y);
    }

    public Cube upCube() {
        return new Cube(x, y + 1, z);
    }

    /**
     * 以原点为中心
     * 返回在this在八个卦限中8个小立方体的3*8个面上对应的向量组
     *
     * @return
     */
    public List<Cube> toCubeVectors() {
        List<Cube> cubeList = new ArrayList<>();

        Cube cube1 = this.clone();
        cubeList.add(cube1);
        cubeList.add(cube1.reverseX());
        cubeList.add(cube1.reverseZ());
        cubeList.add(cube1.reverseX().reverseZ());
        cubeList.add(cube1.reverseY());
        cubeList.add(cube1.reverseX().reverseY());
        cubeList.add(cube1.reverseZ().reverseY());
        cubeList.add(cube1.reverseX().reverseZ().reverseY());

        Cube cube2 = this.shift();
        cubeList.add(cube2);
        cubeList.add(cube2.reverseX());
        cubeList.add(cube2.reverseZ());
        cubeList.add(cube2.reverseX().reverseZ());
        cubeList.add(cube2.reverseY());
        cubeList.add(cube2.reverseX().reverseY());
        cubeList.add(cube2.reverseZ().reverseY());
        cubeList.add(cube2.reverseX().reverseZ().reverseY());

        Cube cube3 = cube2.shift();
        cubeList.add(cube3);
        cubeList.add(cube3.reverseX());
        cubeList.add(cube3.reverseZ());
        cubeList.add(cube3.reverseX().reverseZ());
        cubeList.add(cube3.reverseY());
        cubeList.add(cube3.reverseX().reverseY());
        cubeList.add(cube3.reverseZ().reverseY());
        cubeList.add(cube3.reverseX().reverseZ().reverseY());
        return cubeList;

    }

    /**
     * 创建一个围成立方体表面的向量组
     *
     * @param radius
     * @return
     */
    public List<Cube> toCubeVectors(int radius) {
        List<Cube> cubeList = new ArrayList<>();
        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius; j++) {
                Cube vt = new Cube(i, radius, j);
                cubeList.addAll(vt.toCubeVectors());
            }
        }
        return cubeList;
    }

    /**
     * 根据射线与cube表面交点计算出相邻的cube
     *
     * @param vector3f
     * @return
     */
    public Cube makeAdjacentCube(Vector3f vector3f) {
        System.out.println("makeAdjacentCube" + this.name() + vector3f);
        if (MathUtil.compareFloat(vector3f.x, SysConstant.Public.CUBE_SIZE * x + SysConstant.Public.CUBE_RADIUS) == 0) {
            return new Cube(x + 1, y, z);
        }
        if (MathUtil.compareFloat(vector3f.x, SysConstant.Public.CUBE_SIZE * x - SysConstant.Public.CUBE_RADIUS) == 0) {
            return new Cube(x - 1, y, z);
        }
        if (MathUtil.compareFloat(vector3f.y, SysConstant.Public.CUBE_SIZE * y + SysConstant.Public.CUBE_RADIUS) == 0) {
            return new Cube(x, y + 1, z);
        }
        if (MathUtil.compareFloat(vector3f.y, SysConstant.Public.CUBE_SIZE * y - SysConstant.Public.CUBE_RADIUS) == 0) {
            return new Cube(x, y - 1, z);
        }
        if (MathUtil.compareFloat(vector3f.z, SysConstant.Public.CUBE_SIZE * z + SysConstant.Public.CUBE_RADIUS) == 0) {
            return new Cube(x, y, z + 1);
        }
        if (MathUtil.compareFloat(vector3f.z, SysConstant.Public.CUBE_SIZE * z - SysConstant.Public.CUBE_RADIUS) == 0) {
            return new Cube(x, y, z - 1);
        }
        return this.clone();
    }

    /**
     * 当前cube是否被player占用
     *
     * @param playerPhysicsLocation
     * @return
     */
    public boolean isPlayer(Vector3f playerPhysicsLocation) {
        System.out.println("test point " + playerPhysicsLocation + "isPlayer");
        if (playerPhysicsLocation == null) {
            return false;
        }
        Cube playerCube = parseLocation(playerPhysicsLocation);
        System.out.println("playerCube" + playerCube + ",and this upCube =" + this.upCube());
        return this.equals(playerCube) || this.upCube().equals(playerCube);
    }
}
