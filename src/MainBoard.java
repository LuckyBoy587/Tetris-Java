import javax.swing.*;
import java.awt.*;

public class MainBoard {
    public static final int BOX_SIZE = 35;
    public static final int HORIZONTAL_BOX_COUNT = 16;
    public static final int VERTICAL_BOX_COUNT = 20;
    public static final Dimension BOARD_SIZE = new Dimension(HORIZONTAL_BOX_COUNT * BOX_SIZE, VERTICAL_BOX_COUNT * BOX_SIZE);

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Tetris");
        frame.add(new MainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
