package Activities;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import Activities.MSComponents.MSButton;
import Activities.MSComponents.MSFrame;
import Activities.MSComponents.MSPanel;

public class MainMenu
{
    private final MSComponents component = new MSComponents();
    private final MSFrame msFrame = component.new MSFrame();
    private final MSButton begginerBtn = component.new MSButton("Beginner");
    private final MSButton intermediateBtn = component.new MSButton("Intermediate");
    private final MSButton expertBtn = component.new MSButton("Expert");
    private final MSPanel mainmenuPanel = component.new MSPanel();

    private final JLabel titleIcon = new JLabel();
    private final JLabel copyright = new JLabel("Developed by: GreggyBoi (SRNM21) @2023");

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new MainMenu()); }

    MainMenu()
    {
        mainmenuPanel.setLayout(new BoxLayout(mainmenuPanel, BoxLayout.Y_AXIS));

        titleIcon.setIcon(new ImageIcon(component.MS_HAPPY));
        titleIcon.setPreferredSize(new Dimension(200, 200));
        titleIcon.setAlignmentX(Component.CENTER_ALIGNMENT);        
        titleIcon.setHorizontalAlignment(JLabel.CENTER);
        copyright.setAlignmentX(Component.CENTER_ALIGNMENT);        
        copyright.setHorizontalAlignment(JLabel.CENTER);

        begginerBtn.setPreferredSize(new Dimension(200, 35));
        intermediateBtn.setPreferredSize(new Dimension(200, 35));
        expertBtn.setPreferredSize(new Dimension(200, 35));

        begginerBtn.addActionListener(e -> startGame(GameMode.BEGINNER));
        intermediateBtn.addActionListener(e -> startGame(GameMode.INTERMEDIATE));
        expertBtn.addActionListener(e -> startGame(GameMode.EXPERT));

        mainmenuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainmenuPanel.add(titleIcon);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainmenuPanel.add(begginerBtn);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainmenuPanel.add(intermediateBtn);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainmenuPanel.add(expertBtn);
        mainmenuPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainmenuPanel.add(copyright);

        msFrame.add(mainmenuPanel);
        msFrame.pack();
        msFrame.showFrame();
    }

    private void startGame(GameMode mode)
    {
        new MineSweeper(mode);
        msFrame.dispose();
    }
}

enum GameMode
{
    BEGINNER        (8, 8, 10, 10),
    INTERMEDIATE    (16, 16, 40, 40),
    EXPERT          (16, 30, 99, 99);
    
    private final int ROW;
    private final int COL;
    private final int MINE;
    private final int FLAG;

    GameMode(int row, int col, int mine, int flag)
    {
        this.ROW = row;
        this.COL = col;
        this.MINE = mine;
        this.FLAG = flag;
    }

    int getMine() { return this.MINE; }
    int getFlag() { return this.FLAG; }
    int getRow() { return this.ROW; }
    int getCol() { return this.COL; }
}