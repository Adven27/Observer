package com.urban.testes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

import com.example.prototype.dao.DAO;
import com.urban.entity.Image;

/**
 * Read and show images from db
 * @author victor
 *
 */
public class ReadImgFromDB {
	
	public static void main(String[] args) {
		
	    Image image = DAO.get(Image.class, 1);
		try {
		   final BufferedImage img = ImageIO.read(image.getAsStream());
	       int w = img.getWidth(null);
	       int h = img.getHeight(null);
	       BufferedImage bi = new
	           BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	       Graphics g = bi.getGraphics();
	       g.drawImage(img, 0, 0, null);

		
	    JFrame window = new JFrame();
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setBounds(30, 30, 300, 300);
	    window.getContentPane().add(new JComponent(){    	
	    	  public void paint(Graphics g) {
	    		    Graphics2D g2 = (Graphics2D) g;
	    		    g2.drawImage(img, 10, 10, this);
	    		    g2.finalize();
	    	  }
	    });
	    
	    window.setVisible(true);
	    
		}catch(Exception e){
			e.printStackTrace();
		}
    }

}
