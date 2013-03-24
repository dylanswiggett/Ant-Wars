package util;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureFactory {
	private static final HashMap<String, Texture>  textures = new HashMap<>();
	
	public static Texture getTexture(String filePath, String resourceType){
		Texture tex = textures.get(filePath);
		if (tex == null)
			try {
				tex = TextureLoader.getTexture(resourceType, ResourceLoader.getResourceAsStream(filePath));
				textures.put(filePath, tex);
			} catch (IOException e) {
				System.err.println("Error: Couldn't load the requested texture " + filePath +"\nReturning null.");
				e.printStackTrace();
				return null;
			}
		return tex;
	}
	
	public static Texture getTexture(String filePath){
		return getTexture(filePath, "PNG");
	}
}
