package Activities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

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
    private int row = 0;
    private int col = 0;
    private int flags = 0;
    private int mines = 0;
    
    MineSweeper(GameMode mode)
    {
        row = mode.getRow();
        col = mode.getCol();
        flags = mode.getFlag();
        mines = mode.getMine();
        
        CELL = new int[row][col];
        cellBtn = new MSCell[row][col];
        createCells(row, col);
        placeMines();

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

        headerPanel.add(flagCounterPanel);
        headerPanel.add(resetBtnPanel);
        headerPanel.add(timerPanel);

        gamePanel.add(gameMenuPanel);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gamePanel.add(headerPanel);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        gamePanel.add(minePanel);
        debugCell();
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
                        flagCounter.setText(String.valueOf(++flags));
                    }
                    else if (flags > 0)
                    {
                        currCell.setMarked(true);
                        currCell.setIcon(new ImageIcon(component.MS_FLAG));
                        flagCounter.setText(String.valueOf(--flags));
                    }
                }
            }
        };

        ActionListener cellAC = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                MSCell currCell = (MSCell) e.getSource();
                String[] nameCell = currCell.getName().split("-");

                int r = Integer.valueOf(nameCell[0]);
                int c = Integer.valueOf(nameCell[1]);

                if (!currCell.isRevealed() && !currCell.isMarked())
                {
                    currCell.setRevealed(true);
                    currCell.revealCell();

                    if (currCell.isMine()) currCell.revealMine();
                    if (CELL[r][c] > 0)
                    {
                        switch (CELL[r][c]) 
                        {
                            case 1: currCell.setForeground(new Color(0, 0, 255));       break;
                            case 2: currCell.setForeground(new Color(0, 128, 0));       break;
                            case 3: currCell.setForeground(new Color(255, 0, 0));       break;
                            case 4: currCell.setForeground(new Color(0, 0, 128));       break;
                            case 5: currCell.setForeground(new Color(128, 0, 0));       break;
                            case 6: currCell.setForeground(new Color(0, 128, 128));     break;
                            case 7: currCell.setForeground(new Color(0, 0, 0));         break;
                            case 8: currCell.setForeground(new Color(128, 128, 128));   break;
                        }

                        currCell.setText(String.valueOf(CELL[r][c]));
                    }
                }
            }
        };

        for (int i = 0; i < row; i++) 
        {
            for (int j = 0; j < col; j++) 
            {
                cellBtn[i][j] = component.new MSCell();
                cellBtn[i][j].setPreferredSize(new Dimension(35, 35));
                cellBtn[i][j].addMouseListener(cellMA);
                cellBtn[i][j].addActionListener(cellAC);
                cellBtn[i][j].setName(i + "-" + j);
                minePanel.add(cellBtn[i][j]);

                CELL[i][j] = 0;
            }
        }
    }

    private void placeMines()
    {
        Random rand = new Random();
        int i = 0;

        while (i < mines)
        {
            int r = rand.nextInt(row);
            int c = rand.nextInt(col);

            if (!cellBtn[r][c].isMine())
            {
                cellBtn[r][c].setMine(true);
                i++;
            }
        }

        setMineAdjacent();
    }

    private void setMineAdjacent()
    {
        for (int r = 0; r < row; r++) 
        {
            for (int c = 0; c < col; c++) 
            {
                if (cellBtn[r][c].isMine())
                {
                    if (inBoundCell(r - 1, c - 1))  CELL[r - 1][c - 1]++;
                    if (inBoundCell(r - 1, c))      CELL[r - 1][c]++;
                    if (inBoundCell(r - 1, c + 1))  CELL[r - 1][c + 1]++;
                    if (inBoundCell(r, c - 1))      CELL[r][c - 1]++;
                    if (inBoundCell(r, c + 1))      CELL[r][c + 1]++;
                    if (inBoundCell(r + 1, c - 1))  CELL[r + 1][c - 1]++;
                    if (inBoundCell(r + 1, c))      CELL[r + 1][c]++;
                    if (inBoundCell(r + 1, c + 1))  CELL[r + 1][c + 1]++;
                }
            }
        }
    }

    private boolean inBoundCell(int r, int c) { return (r >= 0 && r < row) && (c >= 0 && c < col) && (!cellBtn[r][c].isMine()); }

    private void confirmExit()
    {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to back to menu?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) 
        {             
            new MainMenu();
            msFrame.dispose();
        }
    }

    private void debugCell()
    {
        for (int i = 0; i < row; i++) 
        {
            for (int j = 0; j < col; j++) 
            {
                MSCell currCell = cellBtn[i][j];
                if (!currCell.isRevealed() && !currCell.isMarked())
                {
                    currCell.setRevealed(true);
                    currCell.revealCell();

                    if (currCell.isMine()) currCell.revealMine();
                    if (CELL[i][j] > 0)
                    {
                        switch (CELL[i][j]) 
                        {
                            case 1: currCell.setForeground(new Color(0, 0, 255));       break;
                            case 2: currCell.setForeground(new Color(0, 128, 0));       break;
                            case 3: currCell.setForeground(new Color(255, 0, 0));       break;
                            case 4: currCell.setForeground(new Color(0, 0, 128));       break;
                            case 5: currCell.setForeground(new Color(128, 0, 0));       break;
                            case 6: currCell.setForeground(new Color(0, 128, 128));     break;
                            case 7: currCell.setForeground(new Color(0, 0, 0));         break;
                            case 8: currCell.setForeground(new Color(128, 128, 128));   break;
                        }

                        currCell.setText(String.valueOf(CELL[i][j]));
                    }
                }
            }
        }
    }
}