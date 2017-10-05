package cn.tinbat.math;

import cn.tinbat.constant.SysConstant;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;

import java.io.IOException;
import java.io.Serializable;

public class Vector implements Savable, Cloneable, Serializable {

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

    public Vector3f toVector3f() {
        return new Vector3f(x, y, z);
    }

    public boolean isOdd() {
        return (x + y + z) % 2 == 1 ? true : false;
    }

    public Vector3f toCubeLocation() {
        return new Vector3f(x * SysConstant.Public.CUBE_SIZE * 2, y * SysConstant.Public.CUBE_SIZE * 2, z * SysConstant.Public.CUBE_SIZE * 2);
    }

    public static Vector3f newCubeVector3f() {
        return new Vector3f(SysConstant.Public.CUBE_SIZE, SysConstant.Public.CUBE_SIZE, SysConstant.Public.CUBE_SIZE);
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
}
