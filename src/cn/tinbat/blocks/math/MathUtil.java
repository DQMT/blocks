package cn.tinbat.blocks.math;

import cn.tinbat.blocks.cube.Cube;

public class MathUtil {
    public static boolean getParity(Cube cube) {
        return getParity(cube.x + cube.y + cube.z);
    }

    static boolean getParity(int n) {
        return (n % 2 == 0) ? true : false;
    }

    public static int compareFloat(float x, float y) {
        float precision = 0.0001f;
        if (x - y > -precision && x - y < precision) {
            return 0;
        } else {
            return x > y ? 1 : -1;
        }
    }

}
