package top.minecode.domain.tag;

import java.io.Serializable;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class Frame implements Serializable {

    private Point leftTop;

    private Point rightDown;

    private String label;

    public Frame() {
    }

    public Frame(Point leftTop, Point rightDown, String label) {
        this.leftTop = leftTop;
        this.rightDown = rightDown;
        this.label = label;
    }

    public Point getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(Point leftTop) {
        this.leftTop = leftTop;
    }

    public Point getRightDown() {
        return rightDown;
    }

    public void setRightDown(Point rightDown) {
        this.rightDown = rightDown;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
