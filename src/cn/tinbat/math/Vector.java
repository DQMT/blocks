package cn.tinbat.math;

import cn.tinbat.constant.SysConstant;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vector implements Savable, Cloneable, Serializable {

    enum AXIS {
      X,Y,Z;
    }

    public static final Vector ZERO = new Vector(0, 0, 0);

    public int x;
    public int y;
    public int z;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Vector() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "[x=" + x + ",y=" + y + ",z=" + z + "]";
    }

    public String name() {
        return x + "," + y + "," + z;
    }

    @Override
    public void write(JmeExporter jmeExporter) throws IOException {

    }

    @Override
    public void read(JmeImporter jmeImporter) throws IOException {

    }

    public Vector clone() {
        return new Vector(x, y, z);
    }

    public Vector3f toVector3f() {
        return new Vector3f(x, y, z);
    }

    public boolean isOdd() {
        return (x + y + z) % 2 != 0;
    }

    public Vector3f toCubeLocation() {
        return new Vector3f(x * SysConstant.Public.CUBE_SIZE * 2, y * SysConstant.Public.CUBE_SIZE * 2, z * SysConstant.Public.CUBE_SIZE * 2);
    }

    public static Vector3f newCubeVector3f() {
        return new Vector3f(SysConstant.Public.CUBE_SIZE, SysConstant.Public.CUBE_SIZE, SysConstant.Public.CUBE_SIZE);
    }

    public Vector reverseX() {
        return new Vector(-x, y, z);
    }

    public Vector reverseY() {
        return new Vector(x, -y, z);
    }

    public Vector reverseZ() {
        return new Vector(x, y, -z);
    }

    public Vector shift(){
        return new Vector(z, x, y);
    }

    /**
     * 以原点为中心
     * 返回在this在八个卦限中8个小立方体的3*8个面上对应的向量组
     * @return
     */
    public List<Vector> toCubeVectors() {
        List<Vector> vectorList = new ArrayList<>();

        Vector vector1 = this.clone();
        vectorList.add(vector1);
        vectorList.add(vector1.reverseX());
        vectorList.add(vector1.reverseZ());
        vectorList.add(vector1.reverseX().reverseZ());
        vectorList.add(vector1.reverseY());
        vectorList.add(vector1.reverseX().reverseY());
        vectorList.add(vector1.reverseZ().reverseY());
        vectorList.add(vector1.reverseX().reverseZ().reverseY());

        Vector vector2 = this.shift();
        vectorList.add(vector2);
        vectorList.add(vector2.reverseX());
        vectorList.add(vector2.reverseZ());
        vectorList.add(vector2.reverseX().reverseZ());
        vectorList.add(vector2.reverseY());
        vectorList.add(vector2.reverseX().reverseY());
        vectorList.add(vector2.reverseZ().reverseY());
        vectorList.add(vector2.reverseX().reverseZ().reverseY());

        Vector vector3 = vector2.shift();
        vectorList.add(vector3);
        vectorList.add(vector3.reverseX());
        vectorList.add(vector3.reverseZ());
        vectorList.add(vector3.reverseX().reverseZ());
        vectorList.add(vector3.reverseY());
        vectorList.add(vector3.reverseX().reverseY());
        vectorList.add(vector3.reverseZ().reverseY());
        vectorList.add(vector3.reverseX().reverseZ().reverseY());
       return vectorList;

    }

    /**
     * 创建一个围成立方体表面的向量组
     * @param radius
     * @return
     */
    public List<Vector> toCubeVectors(int radius) {
        List<Vector> vectorList = new ArrayList<>();
        for (int i = 0; i < radius; i++) {
            for (int j = 0; j < radius; j++) {
                Vector vt = new Vector(i, radius, j);
                    vectorList.addAll(vt.toCubeVectors());
            }
        }
        return vectorList;
    }
}
