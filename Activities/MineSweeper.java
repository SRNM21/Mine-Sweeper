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
import javax.swing.SwingUtilities;

import Activities.MSComponents.MSButton;
import Activities.MSComponents.MSCell;
import Activities.MSComponents.MSFrame;
import Activities.MSComponents.MSPanel;

public class MineSweeper
{ 
    private final MSComponents component = new MSComponents();
    private final ImageIcon MS_ICON = new ImageIcon(component.MS_HAPPY.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
    private final MSFrame msFrame;
    private final MSButton resetBtn = component.new MSButton(MS_ICON);    
    private final MSCell[][] cellBtn;
    private final MSPanel gamePanel = component.new MSPanel();

    private final JPanel gameMenuPanel = new JPanel();
    private final JPanel headerPanel = new JPanel();
    private final JPanel minePanel = new JPanel();
    private final JPanel resetBtnPanel = new JPanel();
    private final JPanel flagCounterPanel = new JPanel();
    private final JPanel timerPanel = new JPanel();
    private final JLabel flagCounter = new JLabel("000");
    private final JLabel timer = new JLabel("000");
    private final JLabel backToMenu = new JLabel("Menu");

    private int[][] CELL;
    private int flags = 0;
    private int mines = 0;
    
    MineSweeper(GameMode mode)
    {
        flags = mode.getFlag();
        mines = mode.getMine();

        msFrame = component.new MSFrame();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        
        backToMenu.setFont(new Font("Arial", Font.BOLD, 14));
        backToMenu.setAlignmentX(Component.RIGHT_ALIGNMENT);
        backToMenu.setHorizontalAlignment(JLabel.CENTER);
        backToMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToMenu.setToolTipText("Back to menu");    
        backToMenu.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) { confirmExit(); }
        });

        gameMenuPanel.setBackground(component.PRIMARY_COLOR);
        gameMenuPanel.add(backToMenu);

        headerPanel.setBackground(component.PRIMARY_COLOR);
        headerPanel.setLayout(new GridLayout(0, 3));
        headerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        headerPanel.setPreferredSize(new Dimension(0, 65));

        flagCounterPanel.setLayout(new GridBagLayout());
        resetBtnPanel.setLayout(new GridBagLayout());
        timerPanel.setLayout(new GridBagLayout());

        flagCounter.setFont(new Font("Arial", Font.BOLD, 16));
        timer.setFont(new Font("Arial", Font.BOLD, 16));

        flagCounter.setText(String.valueOf(flags));
        flagCounter.setIcon(new ImageIcon(component.MS_FLAG_PATH.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        resetBtn.setPreferredSize(new Dimension(45, 45));
        timer.setIcon(new ImageIcon(component.MS_TIMER));
        
        flagCounterPanel.setBackground(component.PRIMARY_COLOR);
        resetBtnPanel.setBackground(component.PRIMARY_COLOR);
        timerPanel.setBackground(component.PRIMARY_COLOR);

        flagCounterPanel.add(flagCounter, new GridBagConstraints());
        resetBtnPanel.add(resetBtn, new GridBagConstraints());
        timerPanel.add(timer, new GridBagConstraints());

        minePanel.setBackground(component.PRIMARY_COLOR);
        minePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        minePanel.setLayout(new GridLayout(mode.getRow(), mode.getCol()));

        CELL = new int[mode.getRow()][mode.getCol()];
        cellBtn = new MSCell[mode.getRow()][mode.getCol()];
        createCells(mode.getRow(), mode.getCol());

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

    private void createCells(int row, int col)
    {
        MouseAdapter cellMA = new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {   
                MSCell currCell = (MSCell) e.getSource();

                if (SwingUtilities.isRightMouseButton(e) && !currCell.isRevealed())
                {
                    if (currCell.isMarked())
                    {
                        currCell.setIcon(null);
                        currCell.setMarked(false);
                    }
                    else
                    {
                        currCell.setMarked(true);
                        currCell.setIcon(new ImageIcon(component.MS_FLAG));
                    }
                }
            }
        };

        for (int i = 0; i < row; i++) 
        {
            for (int j = 0; j < col; j++) 
            {
                cellBtn[i][j] = component.new MSCell();;
                cellBtn[i][j].setPreferredSize(new Dimension(35, 35));
                cellBtn[i][j].addMouseListener(cellMA);
                minePanel.add(cellBtn[i][j]);

                CELL[i][j] = 0;
            }
        }
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