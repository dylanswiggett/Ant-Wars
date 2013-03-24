package render;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import util.TextureFactory;
import util.Vector;

public class RectTextureSprite2D extends Sprite2D{
	
	String textureFilePath, textureFileType;
	Texture texture;

	public RectTextureSprite2D(Vector position, Vector dimension, double verticalOffset, String textureFilePath, String textureFileType) {
		super(position, dimension, verticalOffset);
		this.textureFilePath = textureFilePath;
		this.textureFileType = textureFileType;
	}

	public void draw() {
		if (texture == null)
			texture = TextureFactory.getTexture(textureFilePath, textureFileType);
		texture.bind();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPushMatrix();
		GL11.glTranslated(position.x, position.y, verticalOffset);
		
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
		
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glNormal3d(0, 0, 1);
			
			GL11.glTexCoord2d(0, 1);
			GL11.glVertex3d(0, 0, 0);
			
			GL11.glTexCoord2d(1, 1);
			GL11.glVertex3d(dimension.x, 0, 0);
			
			GL11.glTexCoord2d(1, 0);
			GL11.glVertex3d(dimension.x, dimension.y, 0);
			
			GL11.glTexCoord2d(0, 0);
			GL11.glVertex3d(0, dimension.y, 0);
		}
		GL11.glEnd();
		
		GL11.glPopMatrix();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

}
