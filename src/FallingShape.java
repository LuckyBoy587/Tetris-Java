import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FallingShape {
    protected List<Box> boxList = new ArrayList<>();
    protected final Color color;
    public static final int DROP_INTERVAL = 20;

    protected FallingShape(Color color) {
        this.color = color;
    }

    void drawOn(Graphics2D g2d) {
        g2d.setColor(color);
        for (Box box : boxList) {
            g2d.fill(box.getShape());
        }
    }

    void moveDown1Block() {
        for (Box box : boxList) {
            box.setY(box.getY() + 1);
        }
    }

    void moveRight1Block() {
        for (Box box : boxList) {
            box.setX(box.getX() + 1);
        }
    }

    void moveLeft1Block() {
        for (Box box : boxList) {
            box.setX(box.getX() - 1);
        }
    }

    boolean hasHitBottom() {
        for (Box box : boxList) {
            if (box.getY() + 1 == MainBoard.VERTICAL_BOX_COUNT) return true;
        }
        return false;
    }

    boolean hasHitLeft() {
        for (Box box : boxList) {
            if (box.getX() == 0) return true;
        }

        return false;
    }


    boolean hasHitRight() {
        for (Box box : boxList) {
            if (box.getX() + 1 == MainBoard.HORIZONTAL_BOX_COUNT) return true;
        }

        return false;
    }

    protected Color getShadowColor() {
        float[] hsv = new float[3];
        Color.RGBtoHSB(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), hsv);
        hsv[2] *= 0.8f; // Make it 20% darker
        int alpha = (int) (this.color.getAlpha() * 0.3f); // Make it 20% more translucent
        int r = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
        int red = (r >> 16) & 0xFF;
        int green = (r >> 8) & 0xFF;
        int blue = r & 0xFF;

        return new Color(red, green, blue, alpha);
    }

    public void rotate() {
        int centerX = getCenterX();
        int centerY = getCenterY();
        int leftmostX = getLeftmostX();

        for (Box box : boxList) {
            int newX = centerX - (box.getY() - centerY);
            int newY = centerY + (box.getX() - centerX);

            box.setX(newX);
            box.setY(newY);
        }

        int newLeftmostX = getLeftmostX();
        int deltaX = leftmostX - newLeftmostX;
        for (Box box : boxList) {
            box.setX(box.getX() + deltaX);
        }
    }

    private int getLeftmostX() {
        int leftMostX = Integer.MAX_VALUE;
        for (Box box : boxList) {
            if (box.getX() < leftMostX) leftMostX = box.getX();
        }
        return leftMostX;
    }

    private int getCenterX() {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;

        for (Box box : boxList) {
            minX = Math.min(minX, box.getX());
            maxX = Math.max(maxX, box.getX());
        }

        return (minX + maxX) / 2;
    }

    private int getCenterY() {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Box box : boxList) {
            minY = Math.min(minY, box.getY());
            maxY = Math.max(maxY, box.getY());
        }

        return (minY + maxY) / 2;
    }
}

class VerticalLine extends FallingShape {
    public VerticalLine() {
        super(Color.getHSBColor(240f / 360f, 0.6f, 1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -3));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -4));
    }
}

class HorizontalLine extends FallingShape {
    public HorizontalLine() {
        super(Color.getHSBColor(59f / 360f, 0.61f, 0.64f));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 - 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 - 1, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 + 1, -1));
    }
}

class TwoByTwoBox extends FallingShape {
    public TwoByTwoBox() {
        super(Color.getHSBColor(10 / 360f, 0.61f, 0.64f));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 - 1, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 - 1, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -2));
    }
}

class ZShape extends FallingShape {
    public ZShape() {
        super(Color.getHSBColor(200 / 360f, 0.61f, 0.64f));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 - 1, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 + 1, -1));
    }
}

class ZShapeReverse extends FallingShape {
    public ZShapeReverse() {
        super(Color.getHSBColor(150 / 360f, 0.61f, 0.64f));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 + 1, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 - 1, -1));
    }
}

class TShape extends FallingShape {
    public TShape() {
        super(Color.getHSBColor(50 / 360f, 0.61f, 0.64f));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 - 1, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 + 1, -1));
    }
}

class LShape extends FallingShape {
    protected LShape() {
        super(Color.getHSBColor(85 / 360f, 0.61f, 0.64f));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -3));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 + 1, -1));
    }
}

class JShape extends FallingShape {
    protected JShape() {
        super(Color.getHSBColor(185 / 360f, 0.61f, 0.64f));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -1));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -2));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2, -3));
        boxList.add(new Box(MainBoard.HORIZONTAL_BOX_COUNT / 2 - 1, -1));
    }
}

class FallingShapeShadow extends FallingShape {
    protected FallingShapeShadow(FallingShape other) {
        super(other.getShadowColor());
        this.boxList = new ArrayList<>();
        for (Box box : other.boxList) {
            this.boxList.add(new Box(box.getX(), box.getY()));
        }
    }
}