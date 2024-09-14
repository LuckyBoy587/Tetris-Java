import java.awt.*;

public class BoxMatrix {
    private static final int m = MainBoard.VERTICAL_BOX_COUNT;
    private static final int n = MainBoard.HORIZONTAL_BOX_COUNT;
    private final Box[][] boxes = new Box[m][n];

    public BoxMatrix() {
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                boxes[y][x] = new Box(x, y);
            }
        }
    }

    public void drawOn(Graphics2D g2d) {
        for (Box[] boxRow : boxes) {
            for (Box box : boxRow) {
                g2d.setColor(box.color);
                g2d.fill(box.getShape());
            }
        }
    }

    public void addShape(FallingShape shape) {
        for (Box box: shape.boxList) {
            boxes[box.getY()][box.getX()].isEmpty = false;
            boxes[box.getY()][box.getX()].color = shape.color;
        }
    }

    public void addShape(FallingShape shape, Color color) {
        for (Box box: shape.boxList) {
            boxes[box.getY()][box.getX()].isEmpty = false;
            boxes[box.getY()][box.getX()].color = color;
        }
    }

    public void deleteLine() {
        for (int y = 0; y < m; y++) {
            if (isCompleteLine(boxes[y])) {
                for (int i = y; i > 0; i--) {
                    boxes[i] = boxes[i - 1];
                    for (int x = 0; x < n; x++) {
                        boxes[i][x].setY(boxes[i][x].getY() + 1);
                    }
                }
                boxes[0] = new Box[n];
                for (int x = 0; x < n; x++) {
                    boxes[0][x] = new Box(x, 0);
                }
                deleteLine();
                break;
            }
        }
    }

    private static boolean isCompleteLine(Box[] boxRow) {
        for (Box box : boxRow) {
            if (box.isEmpty) return false;
        }
        return true;
    }

    public boolean hasCollidedBottomWith(FallingShape shape) {
        for (Box box: shape.boxList) {
            if (box.isVisible() && !boxes[box.getY() + 1][box.getX()].isEmpty) return true;
        }

        return false;
    }

    public boolean hasCollidedLeftWith(FallingShape shape) {
        for (Box box: shape.boxList) {
            if (box.isVisible() && !boxes[box.getY()][box.getX() - 1].isEmpty) return true;
        }

        return false;
    }

    public boolean hasCollidedRightWith(FallingShape shape) {
        for (Box box: shape.boxList) {
            if (box.isVisible() && !boxes[box.getY()][box.getX() + 1].isEmpty) return true;
        }

        return false;
    }
}
