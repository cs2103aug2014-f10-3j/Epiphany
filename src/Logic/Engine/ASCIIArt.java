//@author A0119264E-reused
package Logic.Engine;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import Logic.Interpreter.UIHandler;

public class ASCIIArt {

	// Constructors
	public ASCIIArt() {

	}

	// Main art
	public void generateArt(String input) {

		int width = 100;
		int height = 30;

		// BufferedImage image = ImageIO.read(new
		// File("/Users/mkyong/Desktop/logo.jpg"));
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("SansSerif", Font.BOLD, 12));

		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString(input, 10, 20);

		// save this image
		// ImageIO.write(image, "png", new File("/users/mkyong/ascii-art.png"));

		for (int y = 0; y < height; y++) {
			String s = "";
			for (int x = 0; x < width; x++) {

				if(image.getRGB(x, y) == -16777216){
					s += " ";
				}else{
					s += "0";
				}

			}

			if (s.trim().isEmpty()) {
				continue;
			}

			UIHandler.getInstance().printHeader(s);
			//System.out.println(s);
		}
	}
}
