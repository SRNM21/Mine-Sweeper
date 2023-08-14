package Activities;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import Activities.MSComponents.MSRectangleButton;
import Activities.MSComponents.MSFrame;
import Activities.MSComponents.MSPanel;

public class MainMenu
{
    private final int HEIGHT = 500;
    private final int WIDTH = 300;

    private final MSComponents component = new MSComponents();
    private final MSFrame msFrame = component.new MSFrame(new Dimension(WIDTH, HEIGHT));
    private final MSRectangleButton begginerBtn = component.new MSRectangleButton("Beginner");
    private final MSRectangleButton intermediateBtn = component.new MSRectangleButton("Intermediate");
    private final MSRectangleButton expertBtn = component.new MSRectangleButton("Expert");
    private final MSPanel mainmenuPanel = component.new MSPanel();

    private final JLabel titleIcon = new JLabel();

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new MainMenu()); }

    MainMenu()
    {
        mainmenuPanel.setLayout(new BoxLayout(mainmenuPanel, BoxLayout.Y_AXIS));

        titleIcon.setIcon(new ImageIcon(component.MS_ICON));
        titleIcon.setMaximumSize(new Dimension(200, 200));
        titleIcon.setAlignmentX(Component.CENTER_ALIGNMENT);        
        titleIcon.setHorizontalAlignment(JLabel.CENTER);

        begginerBtn.addActionListener(e -> startGame(GameMode.BEGINNER));
        intermediateBtn.addActionListener(e -> startGame(GameMode.INTERMEDIATE));
        expertBtn.addActionListener(e -> startGame(GameMode.EXPERT));

        mainmenuPanel.add(Box.createRigidArea(new Dimension(WIDTH, 20)));
        mainmenuPanel.add(titleIcon);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(WIDTH, 20)));
        mainmenuPanel.add(begginerBtn);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(WIDTH, 20)));
        mainmenuPanel.add(intermediateBtn);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(WIDTH, 20)));
        mainmenuPanel.add(expertBtn);

        msFrame.add(mainmenuPanel);
    }

    private void startGame(GameMode mode)
    {
        new MineSweeper(mode);
        msFrame.setVisible(false);
    }
}

enum GameMode
{
    BEGINNER        (8, 8, 10, 10, 350, 280),
    INTERMEDIATE    (16, 16, 40, 40, 590, 520),
    EXPERT          (16, 30, 99, 99, 590, 1000);
    
    private final int ROW;
    private final int COL;
    private final int BOMB;
    private final int FLAG;
    private final int HEIGHT;
    private final int WIDTH;

    GameMode(int row, int col, int bomb, int flag, int height, int width)
    {
        this.ROW = row;
        this.COL = col;
        this.BOMB = bomb;
        this.FLAG = flag;
        this.HEIGHT = height;
        this.WIDTH = width;
    }

    Dimension getDimension() { return new Dimension(WIDTH, HEIGHT); }
    int getBomb() { return this.BOMB; }
    int getFlag() { return this.FLAG; }
    int getRow() { return this.ROW; }
    int getCol() { return this.COL; }
}