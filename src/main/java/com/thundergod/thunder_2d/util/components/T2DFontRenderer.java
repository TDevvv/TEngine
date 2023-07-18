package com.thundergod.thunder_2d.util.components;

import com.thundergod.ITPart;
import com.thundergod.thunder_2d.util.other.CharI;
import com.thundergod.tobjectsystem.util.TOSComponent;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class T2DFontRenderer extends TOSComponent implements ITPart {
    private String path;
    private int size;

    private int width,height,line_height;
    private Map<Integer, CharI> map = new HashMap<>();
    public T2DFontRenderer(String path,int size){
     this.path = path;
     this.size = size;
     genBitmap();
    }

    private void genBitmap() {
        Font font = new Font(this.path,Font.PLAIN,size);

        BufferedImage img = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();

        int imitated_width = (int)Math.sqrt(font.getNumGlyphs())*font.getSize()+1;
         width = 0;
         height = 0;
         line_height = metrics.getHeight();
         int x = 0;
         int y = (int)(metrics.getHeight()*1.4f);

        for (int i = 0; i < font.getNumGlyphs(); i++) {
            if (font.canDisplay(i)){
                CharI chari = new CharI(x,y,metrics.charWidth(i),metrics.getHeight());
                map.put(i,chari);
                width = Math.max(x+metrics.charWidth(i),width);
                x+= chari.w;
                if (x>imitated_width){
                    x = 0;
                    y+=metrics.getHeight()*1.4f;
                    height += metrics.getHeight()*1.4f;
                }
            }
        }
        height+=metrics.getHeight()*1.4f;
        g.dispose();
        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(Color.WHITE);
        for (int i = 0; i < font.getNumGlyphs(); i++) {
            if (font.canDisplay(i)){
                map.get(i).calcCoords(width,height);
                CharI inf = map.get(i);
                g.drawString(""+(char)i,inf.sX,inf.sY);
            }
        }
        g.dispose();
        try {
            File file = new File("tmp.png");
            ImageIO.write(img,"png",file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void loop(float dt) {

    }
}
