package Frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JFrame
{
    private static final String TITLE = "Mine Sweeper";
    private static final int HEIGHT = 500;
    private static final int WIDTH = 500;
    private static final Color PRIMARY_COLOR = new Color(198, 198, 198);
    private static final Dimension BTN_DIMENSION = new Dimension(200, 50);

    private static final JPanel mainmenuPanel = new JPanel();
    private static final JButton begginerBtn = new JButton("Beginner");
    private static final JButton intermediateBtn = new JButton("Intermediate");
    private static final JButton expertBtn = new JButton("Expert");

    public static void main(String[] args) 
    {
        new MainMenu();
    }

    MainMenu()
    {
        super(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        mainmenuPanel.setBackground(PRIMARY_COLOR);
        mainmenuPanel.setLayout(new BoxLayout(mainmenuPanel, BoxLayout.Y_AXIS));

        begginerBtn.setMaximumSize(BTN_DIMENSION);
        begginerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        begginerBtn.setBackground(PRIMARY_COLOR);
        begginerBtn.setBorder(BorderFactory.createRaisedBevelBorder());

        intermediateBtn.setMaximumSize(BTN_DIMENSION);
        intermediateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        intermediateBtn.setBackground(PRIMARY_COLOR);
        intermediateBtn.setBorder(BorderFactory.createRaisedBevelBorder());

        expertBtn.setMaximumSize(BTN_DIMENSION);
        expertBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        expertBtn.setBackground(PRIMARY_COLOR);
        expertBtn.setBorder(BorderFactory.createRaisedBevelBorder());

        mainmenuPanel.add(begginerBtn);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(WIDTH, 50)));
        mainmenuPanel.add(intermediateBtn);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(WIDTH, 50)));
        mainmenuPanel.add(expertBtn);

        this.add(mainmenuPanel);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        this.setVisible(true);
    }
}
