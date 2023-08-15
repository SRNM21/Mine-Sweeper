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
    protected final Color SECONDARY_COLOR = new Color(173, 181, 189);

    protected final ImageIcon MS_ICON_PATH = new ImageIcon("Images\\HappyIcon.png");
    protected final Image MS_ICON = MS_ICON_PATH.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    private Font BTN_FONT;

    MSComponents()
    {
        try
        {
            BTN_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("Font\\mine-sweeper.ttf")).deriveFont(12f);  
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Font\\mine-sweeper.ttf")));
        }
        catch (IOException | FontFormatException e) { e.printStackTrace(); }
    }

    public class MSFrame extends JFrame
    {
        MSFrame(Dimension size)
        {
            super(TITLE);
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\Bomb.png"));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.setSize(size);

            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
            this.setLocation(x, y);
            this.setVisible(true);
        }
    }

    public class MSRectangleButton extends JButton 
    { 
        private static final Dimension BTN_DIMENSION = new Dimension(200, 50);

        MSRectangleButton(String text) 
        {
            super(text);
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
            this.setFont(BTN_FONT);
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

    public class MSBoxButton extends JButton 
    { 
        private final Dimension BTN_DIMENSION = new Dimension(30, 30);
        private String STATUS = null;

        MSBoxButton(ImageIcon image) 
        { 
            this.setIcon(image);
            initialize(); 
        }

        MSBoxButton() 
        { 
            initialize(); 
        }

        MSBoxButton(String status) 
        {
            setCellStatus(status);
            initialize(); 
        }

        protected void setCellStatus(String status) { this.STATUS = status; }
        protected String getCellStatus() { return this.STATUS; }

        private void initialize()
        {
            Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
            Border raisedBevel = BorderFactory.createRaisedBevelBorder();
            Border loweredBevel = BorderFactory.createLoweredBevelBorder();

            this.setMaximumSize(BTN_DIMENSION);
            this.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.setBackground(PRIMARY_COLOR);
            this.setBorder(BorderFactory.createCompoundBorder(raisedBevel, padding));
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false);
            this.setFont(BTN_FONT);
            this.setUI(new BasicButtonUI());
            this.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(loweredBevel, padding));
                }
    
                @Override
                public void mouseReleased(MouseEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(raisedBevel, padding));
                }
            });
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