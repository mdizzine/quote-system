import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.Dimension;
import javax.swing.SwingConstants;

/**
 * 
 */
public class Title extends JLabel{
    private String text;
    private int size;
    private int fontType;
    
    public Title(String text, int size, int fontType, int height, int width){
        this.text = text;
        this.size = size;
        this.fontType = fontType;
        this.setPreferredSize(new Dimension(width, height)); 
    }
    
    public void paint(Graphics g){
        int x = 10;
        int y = 30;
        
        Font font = new Font("Sans Serif", fontType, size);
        Graphics2D g1 = (Graphics2D) g;
        
        TextLayout tl = new TextLayout(text, font, g1.getFontRenderContext());
        /*g1.setPaint(new Color(160,175,191));
        g1.setPaint(Color.WHITE);
        tl.draw(g1, x-1, y-1);
        tl.draw(g1, x-1, y+1);
        tl.draw(g1, x+1, y-1);
        tl.draw(g1, x+1, y+1);        
        g1.setPaint(new Color(14,93,171));
        tl.draw(g1,x,y);*/
        
        Color top_color = new Color(160,175,191);
        Color side_color = new Color(65,96,127);
        for(int i = 0; i < 5; i++){
            g1.setPaint(top_color);
            tl.draw(g1, x+i-1, y+i-1);
            g1.setColor(side_color);
            tl.draw(g1, x+i-1, y+i+1);
        }
        g1.setColor(Color.WHITE);
        tl.draw(g1,x+4,y+5);
     }
    
    public int shiftNorthWest(int p, int distance){
        return (p - distance);
    }
    
    public int shiftSouthEast(int p, int distance){
        return (p + distance);
    }
}