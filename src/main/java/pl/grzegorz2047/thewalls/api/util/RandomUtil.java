package pl.grzegorz2047.thewalls.api.util;

import java.util.Random;

/**
 * Created by Grzegorz2047. 24.09.2015.
 */
public class RandomUtil {

    private static Random r = new Random();

    public static Random get() {
        return r;
    }
}
