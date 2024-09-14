import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean downPressed = false;
    public static boolean spacePressed = false;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        } else if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        } else if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
