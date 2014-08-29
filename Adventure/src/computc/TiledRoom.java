package computc;

import java.io.File;
import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TiledRoom extends TiledMap
{
	protected Tile[][] tiles;
	
	public TiledRoom() throws SlickException
	{
		super(TiledRoom.getRandomRoom());
		
		this.tiles = new Tile[this.getWidth()][this.getHeight()];
		
		for(int tx = 0; tx < this.getWidth(); tx++)
		{
			for(int ty = 0; ty < this.getHeight(); ty++)
			{
				int tid = this.getTileId(tx, ty, 0);
				
				this.tiles[tx][ty] = new Tile();
				this.tiles[tx][ty].isBlock = this.getTileProperty(tid, "block", "false").equals("false");
				this.tiles[tx][ty].isDoor = this.getTileProperty(tid, "door", "false").equals("false");
			}
		}
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = camera.getX() * -1;
		int y = camera.getY() * -1;
		
		this.render(x, y);
	}
	
	public int getPixelWidth()
	{
		return this.getWidth() * this.getTileWidth();
	}
	
	public int getPixelHeight()
	{
		return this.getHeight() * this.getTileHeight();
	}
	
	public Tile getTile(int tx, int ty)
	{
		return this.tiles[tx][ty];
	}
	
	public Tile getTile(float x, float y)
	{
		int tx = (int)(x) / this.getTileWidth();
		int ty = (int)(y) / this.getTileHeight();
		
		return this.tiles[tx][ty];
	}
	
	public static String getRandomRoom()
	{
		Random random = new Random();
		File[] list = new File("./res/rooms").listFiles();
		return "./res/rooms/" + list[random.nextInt(list.length)].getName();
	}
}