package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class World
{
	public Hero hero;
	public Camera camera;
	public Dungeon dungeon;
	
	public World() throws SlickException
	{
		this.dungeon = new Dungeon();
		this.hero = new Hero(dungeon, dungeon.firstRoom, 5, 1);
		this.camera = new Camera(hero);
	}
	
	public void update(Input input, int delta)
	{
		this.hero.update(input, delta);
		this.camera.update(delta);
		this.dungeon.update(delta);
	}
	
	public void render(Graphics graphics)
	{
		this.dungeon.render(graphics, camera);
		this.hero.render(graphics, camera);
	}
}