import java.awt.*;

public class Box {
    public boolean isEmpty = true;
    public Color color = Color.getHSBColor(0, 0, 30);
    public static final int size = MainBoard.BOX_SIZE;

    private int x;
    private int y;

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

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Shape getShape() {
        return new Rectangle(x * size, y * size, size, size);
    }

    public boolean isVisible() {
        return -1 <= x && x < MainBoard.HORIZONTAL_BOX_COUNT && 0 <= y && y < MainBoard.VERTICAL_BOX_COUNT;
    }
}
