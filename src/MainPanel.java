import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainPanel extends JPanel {
    BoxMatrix matrix;
    public FallingShape fallingShape, fallingShapeShadow;
    private Timer timer;
    private final Random random = new Random();
    private static final int FPS = 60;
    private int frameCount = 0;

    public MainPanel() {
        matrix = new BoxMatrix();
        setLayout(null);
        createFallingShape();

        setPreferredSize(MainBoard.BOARD_SIZE);
        addKeyListener(new KeyHandler());
        setFocusable(true);
        timer = new Timer(1000 / FPS, _ -> {
            if (frameCount == 0) {
                fallingShape.moveDown1Block();
                if (fallingShape.hasHitBottom() || matrix.hasCollidedBottomWith(fallingShape)) {
                    try {
                        matrix.addShape(fallingShape);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Game Over");
                        timer.stop();
                    }
                    createFallingShape();
                }
            }
            if (KeyHandler.leftPressed) {
                if (!(fallingShape.hasHitLeft() || matrix.hasCollidedLeftWith(fallingShape))) {
                    fallingShape.moveLeft1Block();
                    createFallingShapeShadow();
                }
                KeyHandler.leftPressed = false;
            }

            if (KeyHandler.rightPressed) {
                if (!(fallingShape.hasHitRight() || matrix.hasCollidedRightWith(fallingShape))) {
                    fallingShape.moveRight1Block();
                    createFallingShapeShadow();
                }
                KeyHandler.rightPressed = false;
            }

            if (KeyHandler.downPressed) {
                try {
                    matrix.addShape(fallingShapeShadow, fallingShape.color);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Game Over");
                    timer.stop();
                }
                createFallingShape();
                KeyHandler.downPressed = false;
            }

            if (KeyHandler.spacePressed) {
                fallingShape.rotate();
                createFallingShapeShadow();
                KeyHandler.spacePressed = false;
            }
            repaint();

            frameCount++;
            if (frameCount == FallingShape.DROP_INTERVAL) {
                frameCount = 0;
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        matrix.drawOn(g2d);
        fallingShape.drawOn(g2d);
        fallingShapeShadow.drawOn(g2d);
        g2d.setColor(Color.black);
        drawGrid(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
        for (int y = 0; y <= getHeight(); y += MainBoard.BOX_SIZE) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        for (int x = 0; x <= getWidth(); x += MainBoard.BOX_SIZE) {
            g2d.drawLine(x, 0, x, getHeight());
        }
    }

    private FallingShape getRandomFallingShape(int id) {
        return switch (id) {
            case 1 -> new HorizontalLine();
            case 2 -> new VerticalLine();
            case 3 -> new ZShape();
            case 4 -> new TShape();
            case 5 -> new ZShapeReverse();
            case 6 -> new LShape();
            case 7 -> new JShape();
            default -> new TwoByTwoBox();
        };
    }

    private void createFallingShape() {
        fallingShape = getRandomFallingShape(random.nextInt(8) + 1);
        createFallingShapeShadow();
    }

    private void createFallingShapeShadow() {
        matrix.deleteLine();
        fallingShapeShadow = new FallingShapeShadow(fallingShape);
        while (!(fallingShapeShadow.hasHitBottom() || matrix.hasCollidedBottomWith(fallingShapeShadow))) {
            fallingShapeShadow.moveDown1Block();
        }
    }
}
