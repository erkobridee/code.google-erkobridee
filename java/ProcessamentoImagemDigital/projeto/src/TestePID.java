/*
 * Created on 29/09/2005
 */
import java.awt.*;
import java.applet.Applet;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * @author Erko Bridee de Almeida Cabrera
 * 
 * <br><br>
 * <b>Descrição:</b><br>
 */
public class TestePID extends Applet {

    private BufferedImage bi;
    
    
    float[] elements = { -1.0f, -1.0f, -1.0f,
                         -1.0f,   9.f, -1.0f,
                         -1.0f, -1.0f, -1.0f};
                         
    
    public TestePID() {
            setBackground(Color.white);
            Image img = getToolkit().getImage("c:/img.jpg");
            try {
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(img, 0);
                tracker.waitForID(0);
            } catch (Exception e) {}

            int iw = img.getWidth(this);
            int ih = img.getHeight(this);
            bi = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.drawImage(img,0,0,this);

    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY);
        int w = getSize().width;
        int h = getSize().height;
        int bw = bi.getWidth(this);
        int bh = bi.getHeight(this);

        AffineTransform at = new AffineTransform();
        at.scale(w/2.0/bw, h/1.0/bh);

        //----        
        long iniTime = System.currentTimeMillis();
        
        BufferedImageOp biop = null;
        BufferedImage bimg = new BufferedImage(bw,bh,BufferedImage.TYPE_INT_RGB);

        Kernel kernel = new Kernel(3, 3, elements);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(bi,bimg);
        
        this.saveImage( bimg, "C:/resultado.jpg" );
        
        biop = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        
        long endTime = System.currentTimeMillis();
        
        System.out.println( "Tempo Processamento: " + ( endTime - iniTime ) );
        //-----
        
        g2.drawImage(bi, biop, 0, 0);
        g2.drawImage(bimg, biop, w/2+3,0);

    }

	public void saveImage( BufferedImage image, String arquivo  ) {
		try { 
			String sufixo = arquivo.substring(arquivo.lastIndexOf('.') + 1);
			ImageIO.write( image, sufixo, new File(arquivo));
		}catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao salvar imagem: " + arquivo );
		}
	}
    
    public static void main(String s[]) {
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        };
        Frame f = new Frame("Edge");
        f.addWindowListener(l);
        f.add("Center", new TestePID());
        f.pack();
        f.setSize(new Dimension(600, 300));
        f.show();
    }
 }
