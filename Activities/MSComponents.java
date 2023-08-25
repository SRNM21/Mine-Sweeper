package Activities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;

public class MSComponents 
{
    protected final String TITLE = "Mine Sweeper";
    protected final Color PRIMARY_COLOR = new Color(198, 198, 198);
    protected final Color SECONDARY_COLOR = new Color(170, 170, 170);

    protected final ImageIcon MS_HAPPY_PATH = new ImageIcon("Images\\HappyFace.png");
    protected final Image MS_HAPPY = MS_HAPPY_PATH.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    
    protected final ImageIcon MS_SAD_PATH = new ImageIcon("Images\\SadFace.png");
    protected final Image MS_SAD = MS_SAD_PATH.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    
    protected final ImageIcon MS_COOL_PATH = new ImageIcon("Images\\CoolFace.png");
    protected final Image MS_COOL = MS_COOL_PATH.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    
    protected final ImageIcon MS_TIMER_PATH = new ImageIcon("Images\\Timer.png");
    protected final Image MS_TIMER = MS_TIMER_PATH.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    
    protected final ImageIcon MS_FLAG_PATH = new ImageIcon("Images\\Flag.png");
    protected final Image MS_FLAG = MS_FLAG_PATH.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        
    protected final ImageIcon MS_MINE_PATH = new ImageIcon("Images\\Mine.png");
    protected final Image MS_MINE = MS_MINE_PATH.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    private Font BTN_FONT;

    MSComponents()
    {
        try
        {
            BTN_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("Font\\mine-sweeper.ttf")).deriveFont(14f);  
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Font\\mine-sweeper.ttf")));
        }
        catch (IOException | FontFormatException e) { e.printStackTrace(); }
    }

    public class MSFrame extends JFrame
    {
        MSFrame()
        {
            super(TITLE);
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\Mine.png"));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
        }

        public void showFrame()
        {
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
            this.setLocation(x, y);
            this.setVisible(true);
        }
    }

    public class MSButton extends JButton 
    { 
        private static final Dimension BTN_DIMENSION = new Dimension(200, 50);

        MSButton(String text) 
        {
            super(text);
            initialize();
        }

        MSButton(ImageIcon image)
        {
            this.setIcon(image);
            initialize();
        }

        private void initialize()
        {
            this.setMaximumSize(BTN_DIMENSION);
            this.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.setBackground(PRIMARY_COLOR);
            this.setBorder(BorderFactory.createRaisedBevelBorder());
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false);
            this.setFont(BTN_FONT.deriveFont(12f));
            this.setUI(new BasicButtonUI());
            this.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e) {
                    setBorder(BorderFactory.createLoweredBevelBorder());
                    setFont(BTN_FONT.deriveFont(11f));
                }
    
                @Override
                public void mouseReleased(MouseEvent e) {
                    setBorder(BorderFactory.createRaisedBevelBorder());
                    setFont(BTN_FONT.deriveFont(12f));
                }
            });
        }
    }

    public class MSCell extends JButton 
    { 
        private final Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        private final Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        private final Dimension BTN_DIMENSION = new Dimension(35, 35);

        private boolean MARKED_CELL = false;
        private boolean REVEALED_CELL = false;
        private boolean IS_MINE = false;
        private int MINE_ADJACENT = 0;

        MSCell()
        {
            this.setMaximumSize(BTN_DIMENSION);            
            this.setPreferredSize(new Dimension(35, 35));
            this.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.setBackground(PRIMARY_COLOR);
            this.setBorder(BorderFactory.createCompoundBorder(raisedBevel, padding));
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false);
            this.setFont(BTN_FONT.deriveFont(18f));
            this.setUI(new BasicButtonUI());
        }
        
        public void setMine(boolean x) { this.IS_MINE = x; }
        public boolean isMine() { return this.IS_MINE; }
        
        public void setMineAdjacent(int x) { this.MINE_ADJACENT = x; }
        public int getMineAdjacent() { return this.MINE_ADJACENT; }

        public boolean isMarked() { return this.MARKED_CELL; }

        public void markCell() 
        { 
            this.MARKED_CELL = true; 
            this.setIcon(new ImageIcon(MS_FLAG));
        }

        public void unmarkCell() 
        { 
            this.MARKED_CELL = false; 
            this.setIcon(null);
        }

        public boolean isRevealed() { return this.REVEALED_CELL; }

        public void revealCell() 
        {            
            this.REVEALED_CELL = true;
            this.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 1)); 

            if (this.isMine()) this.setIcon(new ImageIcon(MS_MINE));
            if (MINE_ADJACENT > 0)
            {
                switch (MINE_ADJACENT) 
                {
                    case 1: this.setForeground(new Color(0, 0, 255));       break;
                    case 2: this.setForeground(new Color(0, 128, 0));       break;
                    case 3: this.setForeground(new Color(255, 0, 0));       break;
                    case 4: this.setForeground(new Color(0, 0, 128));       break;
                    case 5: this.setForeground(new Color(128, 0, 0));       break;
                    case 6: this.setForeground(new Color(0, 128, 128));     break;
                    case 7: this.setForeground(new Color(0, 0, 0));         break;
                    case 8: this.setForeground(new Color(128, 128, 128));   break;
                }
                
                this.setText(String.valueOf(MINE_ADJACENT));
            }
        }

        public void unrevealCell() 
        {             
            this.REVEALED_CELL = false;
            this.setBorder(BorderFactory.createCompoundBorder(raisedBevel, padding));
            this.setIcon(null);
            this.setText(null);
        }
    }

    public class MSPanel extends JPanel 
    {
        MSPanel() 
        {
            Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
            Border raisedBevel = BorderFactory.createRaisedBevelBorder();
            this.setBorder(BorderFactory.createCompoundBorder(raisedBevel, padding));
            this.setBackground(PRIMARY_COLOR);
        }
    }
}