package util;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.newdawn.slick.util.ResourceLoader;

public class TextureProcessor {
	private static boolean[][] getAlphaData(BufferedImage img){
		boolean[][] alphas = new boolean[img.getWidth()][img.getHeight()];
		
		Raster alpha = img.getAlphaRaster();
		
		for (int x = 0; x < img.getWidth(); x++)
			for (int y = 0; y < img.getHeight(); y++){

			}
		
		return alphas;
	}
	
	public static ArrayList<Vector> getMinimalVertices(String textureFilePath){
		ArrayList<Vector> vertices = new ArrayList<>();
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(ResourceLoader.getResourceAsStream(textureFilePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		boolean[][] alpha = getAlphaData(img);
		
		return vertices;
	}
}
