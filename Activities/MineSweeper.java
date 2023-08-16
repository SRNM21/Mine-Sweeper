package Activities;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Activities.MSComponents.MSBoxButton;
import Activities.MSComponents.MSFrame;
import Activities.MSComponents.MSPanel;

public class MineSweeper
{ 
    private final MSComponents component = new MSComponents();
    private final ImageIcon MS_ICON = new ImageIcon(component.MS_ICON.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    private final MSFrame msFrame;
    private final MSBoxButton resetBtn = component.new MSBoxButton(MS_ICON);    
    private final MSBoxButton[][] cell;
    private final MSPanel gamePanel = component.new MSPanel();

    private final JPanel gameMenuPanel = new JPanel();
    private final JPanel headerPanel = new JPanel();
    private final JPanel minePanel = new JPanel();
    private final JPanel resetBtnPanel = new JPanel();
    private final JPanel flagCounterPanel = new JPanel();
    private final JPanel timerPanel = new JPanel();
    private final JLabel flagCounter = new JLabel("000");
    private final JLabel timer = new JLabel("000");
    private final JLabel backToMenu = new JLabel("< Menu");

    MineSweeper(GameMode mode)
    {
        msFrame = component.new MSFrame();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        
        backToMenu.setFont(new Font("Arial", Font.BOLD, 14));
        backToMenu.setAlignmentX(Component.RIGHT_ALIGNMENT);
        backToMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToMenu.setToolTipText("Back to menu");
        backToMenu.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) { confirmExit(); }
        });

        gameMenuPanel.setLayout(new BoxLayout(gameMenuPanel, BoxLayout.X_AXIS));
        gameMenuPanel.setBackground(component.PRIMARY_COLOR);
        gameMenuPanel.add(backToMenu);

        headerPanel.setBackground(component.PRIMARY_COLOR);
        headerPanel.setLayout(new GridLayout(0, 3));
        headerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        headerPanel.setPreferredSize(new Dimension(0, 65));

        resetBtnPanel.setLayout(new GridBagLayout());
        resetBtn.setMaximumSize(new Dimension(30, 30));
        
        flagCounterPanel.setBackground(component.PRIMARY_COLOR);
        resetBtnPanel.setBackground(component.PRIMARY_COLOR);
        timerPanel.setBackground(component.PRIMARY_COLOR);

        flagCounterPanel.add(flagCounter);
        resetBtnPanel.add(resetBtn, new GridBagConstraints());
        timerPanel.add(timer);

        minePanel.setBackground(component.PRIMARY_COLOR);
        minePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        minePanel.setLayout(new GridLayout(mode.getRow(), mode.getCol()));

        cell = new MSBoxButton[mode.getRow()][mode.getCol()];

        for (int i = 0; i < mode.getRow(); i++) {
            for (int j = 0; j < mode.getCol(); j++) {
                cell[i][j] = component.new MSBoxButton();
                cell[i][j].setPreferredSize(new Dimension(35, 35));
                cell[i][j].setCellStatus(null);
                minePanel.add(cell[i][j]);
            }
        }

        headerPanel.add(flagCounterPanel);
        headerPanel.add(resetBtnPanel);
        headerPanel.add(timerPanel);

        gamePanel.add(gameMenuPanel);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gamePanel.add(headerPanel);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gamePanel.add(minePanel);
        
        msFrame.add(gamePanel);
        msFrame.pack();
        msFrame.showFrame();
    }

    private void confirmExit()
    {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to back to menu?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) 
        {             
            new MainMenu();
            msFrame.dispose();
        }
    }
}