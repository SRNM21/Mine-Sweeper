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
import javax.swing.JLabel;
import javax.swing.JPanel;
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
        MSFrame(int width, int height)
        {
            super(TITLE);
            this.setIconImage(Toolkit.getDefaultToolkit().getImage("Images\\Bomb.png"));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.setSize(width, height);

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
            this.setFont(BTN_FONT);
            this.setUI(new BasicButtonUI());
            this.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mousePressed(MouseEvent e) {
                    setBorder(BorderFactory.createLoweredBevelBorder());
                }
    
                @Override
                public void mouseReleased(MouseEvent e) {
                    setBorder(BorderFactory.createRaisedBevelBorder());
                }
            });
        }
    }

    public class MSDisplay extends JPanel 
    {
        private static final Dimension DSIPLAY_DIMENSION = new Dimension(52, 100);
        private static final JLabel displayHolder = new JLabel();

        MSDisplay(int x) 
        {
            ImageIcon MS_SEVEN_SEGMENT_PATH = new ImageIcon("Images\\D_" + x + ".png");
            Image MS_SEVEN_SEGMENT = MS_SEVEN_SEGMENT_PATH.getImage().getScaledInstance(52, 100, Image.SCALE_SMOOTH);

            displayHolder.setIcon(new ImageIcon(MS_SEVEN_SEGMENT));
            displayHolder.setMaximumSize(DSIPLAY_DIMENSION);
            displayHolder.setAlignmentX(Component.CENTER_ALIGNMENT);        
            displayHolder.setHorizontalAlignment(JLabel.CENTER);  

            this.setMaximumSize(DSIPLAY_DIMENSION);
            this.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(displayHolder); 
        }
    }
    
}