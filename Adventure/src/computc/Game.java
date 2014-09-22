package computc;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import computc.states.DungeonMapGameState;
import computc.states.MainGameState;
import computc.worlds.rooms.Room;

public class Game extends StateBasedGame
{
	public static boolean reset;
	
	public Game()
	{
		super(Game.TITLE + " " + Game.VERSION);
	}
	
	public void initStatesList(GameContainer container) throws SlickException
	{
		GameData gamedata = new GameData();
		
        this.addState(new MainGameState(gamedata));
        this.addState(new DungeonMapGameState(gamedata));
	}
	
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer container = new AppGameContainer(new Game());
		container.setDisplayMode(Room.WIDTH, Room.HEIGHT, false);
		container.setTargetFrameRate(60);
		container.start();
	}
	
	public static final String TITLE = "Game";
	public static final String VERSION = "v0.2.0";
	public static final int WIDTH = Room.WIDTH;
	public static final int HEIGHT = Room.HEIGHT;
	
	public static Random randomness = new Random();
}