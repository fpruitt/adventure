package computc;

import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.DungeonException;
import computc.worlds.dungeons.FiveRoomDungeon;
import computc.worlds.dungeons.OneRoomDungeon;
import computc.worlds.dungeons.RandomDungeon;

public class GameData
{
	public Hero hero;
	public Dungeon dungeon;
	
	public int level = 0;
	public String[] tilesets = {
		"./res/tilesets/dirty.tileset.xml",
		"./res/tilesets/stoney.tileset.xml",
		"./res/tilesets/snowy.tileset.xml",
	};
	
	public void instantiate() throws SlickException
	{
		this.dungeon = null;
		
		while(this.dungeon == null)
		{
			try
			{
				this.dungeon = new RandomDungeon(this, this.getTileset());
			}
			catch(DungeonException exception)
			{
				//code goes here.
			}
		}
		
		this.dungeon.initiate();
		
		this.hero = new Hero(this.dungeon, 5, 4);
	}
	
	public String getTileset()
	{
		return this.tilesets[this.level % this.tilesets.length];
	}
}
