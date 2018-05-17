package top.minecode.domain.tag;

import java.io.Serializable;

/**
 * Created on 2018/4/2.
 * Description:
 * @author Liao
 */
public class Point implements Serializable {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
