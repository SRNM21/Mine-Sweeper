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

    private MSCell[][] cellBtn;
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
        cellBtn = new MSCell[row][col];   

        msFrame = component.new MSFrame();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        startGame(false);
        resetBtn.addActionListener(e -> startGame(true));
        
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
        flagCounter.setName("flagCounter");
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

        msFrame.add(gamePanel);
        msFrame.pack();
        msFrame.showFrame();
    }
    
    private void startGame(boolean reset)
    {
        createCells(row, col, reset);
        placeMines();
        setMineAdjacent();
        debugCell();
    }

    private void createCells(int row, int col, boolean reset)
    {        
        for (int i = 0; i < row; i++) 
        {
            for (int j = 0; j < col; j++) 
            {   
                if (!reset) cellBtn[i][j] = component.new MSCell();      

                cellBtn[i][j].setFlag(flags);      
                cellBtn[i][j].setName(i + "-" + j);      
                cellBtn[i][j].setParentLabel(flagCounter);  
                cellBtn[i][j].setRevealed(false);
                cellBtn[i][j].setMarked(false);      
                cellBtn[i][j].setMine(false);
                cellBtn[i][j].setMineAdjacent(0);
                cellBtn[i][j].unrevealCell();
                
                if (reset) 
                {           
                    cellBtn[i][j].revalidate();
                    cellBtn[i][j].repaint();
                    flagCounter.setText(String.valueOf(flags));
                }
                else 
                {
                    minePanel.add(cellBtn[i][j]);
                }
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
    }

    private void setMineAdjacent()
    {
        for (int r = 0; r < row; r++) 
        {
            for (int c = 0; c < col; c++) 
            {
                if (cellBtn[r][c].isMine())
                {      
                    if (inBoundCell(r - 1, c - 1))  cellBtn[r - 1][c - 1].setMineAdjacent(1 + cellBtn[r - 1][c - 1].getMineAdjacent());
                    if (inBoundCell(r - 1, c))      cellBtn[r - 1][c]    .setMineAdjacent(1 + cellBtn[r - 1][c]    .getMineAdjacent());
                    if (inBoundCell(r - 1, c + 1))  cellBtn[r - 1][c + 1].setMineAdjacent(1 + cellBtn[r - 1][c + 1].getMineAdjacent());
                    if (inBoundCell(r, c - 1))      cellBtn[r][c - 1]    .setMineAdjacent(1 + cellBtn[r][c - 1]    .getMineAdjacent());
                    if (inBoundCell(r, c + 1))      cellBtn[r][c + 1]    .setMineAdjacent(1 + cellBtn[r][c + 1]    .getMineAdjacent());
                    if (inBoundCell(r + 1, c - 1))  cellBtn[r + 1][c - 1].setMineAdjacent(1 + cellBtn[r + 1][c - 1].getMineAdjacent());
                    if (inBoundCell(r + 1, c))      cellBtn[r + 1][c]    .setMineAdjacent(1 + cellBtn[r + 1][c]    .getMineAdjacent());
                    if (inBoundCell(r + 1, c + 1))  cellBtn[r + 1][c + 1].setMineAdjacent(1 + cellBtn[r + 1][c + 1].getMineAdjacent());
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
            for (int j = 0; j < col; j++) 
                cellBtn[i][j].revealCell();
    }
}