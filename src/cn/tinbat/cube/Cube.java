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

    public enum AXIS {
      X,Y,Z;
    }

    public static final Cube ZERO = new Cube(0, 0, 0);

    public static Cube parseName(String name){
        String[] array = name.substring(1,name.length()-1).split(",");
        return new Cube(Integer.valueOf(array[0]),Integer.valueOf(array[1]),Integer.valueOf(array[2]));
    }

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

    public Cube() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Cube(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "[x=" + x + ",y=" + y + ",z=" + z + "]";
    }

    public String name() {
        return "{"+x + "," + y + "," + z+"}";
    }

    @Override
    public void write(JmeExporter jmeExporter) throws IOException {

    }

    @Override
    public void read(JmeImporter jmeImporter) throws IOException {

    }

    public Cube clone() {
        return new Cube(x, y, z);
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

    public Cube reverseX() {
        return new Cube(-x, y, z);
    }

    public Cube reverseY() {
        return new Cube(x, -y, z);
    }

    public Cube reverseZ() {
        return new Cube(x, y, -z);
    }

    public Cube shift(){
        return new Cube(z, x, y);
    }

    /**
     * 以原点为中心
     * 返回在this在八个卦限中8个小立方体的3*8个面上对应的向量组
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

    public Cube makeAdjacentCube(Vector3f vector3f){
        System.out.print("makeAdjacentCube"+this.name()+vector3f);
        if(MathUtil.compareFloat(vector3f.x,2*x+1)==0){
            System.out.println("x+1");
            return new Cube(x+1,y,z);
        }
        if(MathUtil.compareFloat(vector3f.x,2*x-1)==0){
            System.out.println("x-1");
            return new Cube(x-1,y,z);
        }
        if(MathUtil.compareFloat(vector3f.y,2*y+1)==0){
            System.out.println("y+1");
            return new Cube(x,y+1,z);
        }
        if(MathUtil.compareFloat(vector3f.y,2*y-1)==0){
            System.out.println("y-1");
            return new Cube(x,y-1,z);
        }
        if(MathUtil.compareFloat(vector3f.z,2*z+1)==0){
            System.out.println("z+1");
            return new Cube(x,y,z+1);
        }
        if(MathUtil.compareFloat(vector3f.z,2*z+1)==0){
            System.out.println("z-1");
            return new Cube(x,y,z-1);
        }
        return this.clone();
    }
}
