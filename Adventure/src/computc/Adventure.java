package computc;

import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Adventure extends BasicGame
{
	public Adventure()
	{
		super(GAME_TITLE);
	}
	
	public void init(GameContainer container) throws SlickException
	{
		world = new World("res/world.tmx");
	}
	
	public void update(GameContainer container, int delta) throws SlickException
	{
		Input input = container.getInput();
		world.update(input, delta);
	}
	
	public void render(GameContainer container, Graphics graphics) throws SlickException
	{
		world.render(graphics);
	}
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer container = new AppGameContainer(new Adventure());
			container.setDisplayMode(SCREEN_WIDTH * TILE_SIZE, SCREEN_HEIGHT * TILE_SIZE, false);
			container.start();
		}
		catch(Exception error)
		{
			System.out.println(error.getMessage());
		}
	}
	
	public World world;

	public static final int TILE_SIZE = 64;
	public static final int SCREEN_WIDTH = 11;
	public static final int SCREEN_HEIGHT = 9;
	
	public static final String GAME_TITLE = "We don't need no title.";
}
