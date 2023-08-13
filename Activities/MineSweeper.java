package Activities;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Activities.MSComponents.MSButton;
import Activities.MSComponents.MSFrame;

public class MineSweeper
{
    private static final int HEIGHT = 800;
    private static final int WIDTH = 800;

    private static final MSComponents component = new MSComponents();
    private static final MSButton resetBtn = component.new MSButton(new ImageIcon(component.MS_ICON));

    private static final JPanel gamePanel = new JPanel();
    private static final JPanel headerPanel = new JPanel();
    private static final JPanel minePanel = new JPanel();
    private static final JPanel bombCounterPanel = new JPanel();
    private static final JPanel timerPanel = new JPanel();
    
    MineSweeper(int r, int c)
    {
        MSFrame msFrame = component.new MSFrame(WIDTH, HEIGHT);
        
        gamePanel.setBackground(component.PRIMARY_COLOR);
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        resetBtn.setMaximumSize(new Dimension(50, 50));
        
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(resetBtn);


        msFrame.add(gamePanel);
    }
}
