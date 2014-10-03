package computc.entities;

import org.newdawn.slick.SlickException;

import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public abstract class Enemy extends Entity
{
	public Enemy(Dungeon dungeon, Room room, int x, int y)
	{
		super(dungeon, room, x, y);
	}

	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean blinkTimer;
	protected int blinkCooldown;
	
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    
    protected boolean attacking;
	
	public boolean isDead()
	{
		return dead;
	}
	
	public int getDamage() 
	{
		return damage;
	}
	
	public void hit(int damage)
	{
		if(dead || blinking)
		{
			return;
		}
		
		health -= damage;
		if(health <= 0)
		{
			dead = true;
		}
		
		blinking = true;
		blinkCooldown = 50;
	}
	
	public int getHealth()
	{
		return health;
	}
}