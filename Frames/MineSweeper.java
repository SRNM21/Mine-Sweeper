package Frames;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MineSweeper extends JFrame
{
    private static final String TITLE = "Mine Sweeper";
    private static final int HEIGHT = 800;
    private static final int WIDTH = 800;

    MineSweeper()
    {
        super(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        this.setVisible(true);
    }
}
