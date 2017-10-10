package cn.tinbat.math;

import cn.tinbat.cube.Cube;

/**
 * Created by tincat on 2017/10/5.
 */
public class MathUtil {
    public static boolean getParity(Cube cube) {
        return getParity(cube.getX() + cube.getY() + cube.getZ());
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
