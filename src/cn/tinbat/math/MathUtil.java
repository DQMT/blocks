package cn.tinbat.math;

import com.jme3.math.Vector3f;

/**
 * Created by tincat on 2017/10/5.
 */
public class MathUtil {
    public static boolean getParity(Vector vector) {
        return getParity(vector.getX() + vector.getY() + vector.getZ());
    }

    static boolean getParity(int n) {
        return (n % 2 == 0) ? true : false;
    }

}
