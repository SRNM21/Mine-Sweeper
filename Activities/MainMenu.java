package Activities;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Activities.MSComponents.MSButton;
import Activities.MSComponents.MSFrame;

public class MainMenu
{
    private final int HEIGHT = 500;
    private final int WIDTH = 300;

    private final MSComponents component = new MSComponents();
    private final MSButton begginerBtn = component.new MSButton("Beginner");
    private final MSButton intermediateBtn = component.new MSButton("Intermediate");
    private final MSButton expertBtn = component.new MSButton("Expert");
    
    private final JPanel mainmenuPanel = new JPanel();
    private final JLabel titleIcon = new JLabel();

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new MainMenu()); }

    MainMenu()
    {
        MSFrame msFrame = component.new MSFrame(WIDTH, HEIGHT);
        mainmenuPanel.setBackground(component.PRIMARY_COLOR);
        mainmenuPanel.setLayout(new BoxLayout(mainmenuPanel, BoxLayout.Y_AXIS));
        mainmenuPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        titleIcon.setIcon(new ImageIcon(component.MS_ICON));
        titleIcon.setMaximumSize(new Dimension(200, 200));
        titleIcon.setAlignmentX(Component.CENTER_ALIGNMENT);        
        titleIcon.setHorizontalAlignment(JLabel.CENTER);  

        begginerBtn.addActionListener(e -> 
        {
            new MineSweeper(8, 8);
            msFrame.setVisible(false);
        });

        intermediateBtn.addActionListener(e -> 
        {
            new MineSweeper(16, 16);
            msFrame.setVisible(false);
        });

        expertBtn.addActionListener(e -> 
        {
            new MineSweeper(16, 30);
            msFrame.setVisible(false);
        });


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
}
