package Activities;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Activities.MSComponents.MSBoxButton;
import Activities.MSComponents.MSFrame;

public class MineSweeper
{
    private final MSComponents component = new MSComponents();
    private final ImageIcon MS_ICON = new ImageIcon(component.MS_ICON.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    private final MSBoxButton resetBtn = component.new MSBoxButton(MS_ICON);

    private final JPanel gamePanel = new JPanel();
    private final JPanel headerPanel = new JPanel();
    private final JPanel minePanel = new JPanel();
    private final JLabel flagCounter = new JLabel("000");
    private final JLabel timer = new JLabel("000");
    
    MineSweeper(GameMode mode)
    {   
        MSFrame msFrame = component.new MSFrame(mode.getDimension());
        gamePanel.setBackground(component.PRIMARY_COLOR);
        gamePanel.setBorder(BorderFactory.createRaisedBevelBorder());

        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        gamePanel.setBorder(BorderFactory.createCompoundBorder(raisedBevel, padding));
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        headerPanel.setBackground(component.PRIMARY_COLOR);
        headerPanel.setLayout(new GridLayout(0, 3));
        headerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        headerPanel.setMaximumSize(new Dimension(((int) mode.getDimension().getWidth()), 50));

        //TODO: ADD PANEL TO CENTER GRID TO CENTER THE RESET BUTTON
        resetBtn.setMaximumSize(new Dimension(30, 30));

        minePanel.setBackground(component.PRIMARY_COLOR);
        minePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        minePanel.setLayout(new GridLayout(mode.getRow(), mode.getCol()));
        
        headerPanel.add(flagCounter);
        headerPanel.add(resetBtn);
        headerPanel.add(timer);
        
        gamePanel.add(headerPanel);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gamePanel.add(minePanel);
        
        msFrame.add(gamePanel);
        //msFrame.pack();
    }
}
