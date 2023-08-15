package Activities;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Activities.MSComponents.MSBoxButton;
import Activities.MSComponents.MSFrame;
import Activities.MSComponents.MSPanel;

public class MineSweeper
{ 
    private final MSComponents component = new MSComponents();
    private final ImageIcon MS_ICON = new ImageIcon(component.MS_ICON.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    private final MSBoxButton resetBtn = component.new MSBoxButton(MS_ICON);
    private final MSBoxButton[][] cell = new MSBoxButton[][] {};
    private final MSPanel gamePanel = component.new MSPanel();
    private final String[] modes = new String[] { "Beginner", "Intermediate", "Expert" };

    private final JPanel gameMenuPanel = new JPanel();
    private final JPanel headerPanel = new JPanel();
    private final JPanel minePanel = new JPanel();
    private final JPanel resetBtnPanel = new JPanel();
    private final JPanel flagCounterPanel = new JPanel();
    private final JPanel timerPanel = new JPanel();
    private final JLabel flagCounter = new JLabel("000");
    private final JLabel timer = new JLabel("000");
    private final JComboBox<String> menu = new JComboBox<>(modes);

    MineSweeper(GameMode mode)
    {   
        MSFrame msFrame = component.new MSFrame(mode.getDimension());
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        headerPanel.setBackground(component.PRIMARY_COLOR);
        headerPanel.setLayout(new GridLayout(0, 3));
        headerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        headerPanel.setMaximumSize(new Dimension(((int) mode.getDimension().getWidth()), 65));

        resetBtn.setMaximumSize(new Dimension(30, 30));
        
        flagCounterPanel.setBackground(component.PRIMARY_COLOR);
        resetBtnPanel.setBackground(component.PRIMARY_COLOR);
        timerPanel.setBackground(component.PRIMARY_COLOR);

        flagCounterPanel.add(flagCounter);
        resetBtnPanel.add(resetBtn);
        timerPanel.add(timer);

        minePanel.setBackground(component.PRIMARY_COLOR);
        minePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        minePanel.setLayout(new GridLayout(mode.getRow(), mode.getCol()));

        headerPanel.add(flagCounterPanel);
        headerPanel.add(resetBtnPanel);
        headerPanel.add(timerPanel);

        gameMenuPanel.setMaximumSize(new Dimension(((int) mode.getDimension().getWidth()), 50));
        gameMenuPanel.setBackground(component.PRIMARY_COLOR);
        gameMenuPanel.add(menu);

        gamePanel.add(gameMenuPanel);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gamePanel.add(headerPanel);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gamePanel.add(minePanel);
        
        msFrame.add(gamePanel);
    }
}
