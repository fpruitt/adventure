package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import computc.Direction;
import computc.Game;
import computc.cameras.Camera;
import computc.cameras.RoomFollowingCamera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Maniac extends Enemy
{
	
	public static boolean hit = false;
	
	
	private float bullRushVelocity;
	private float maxBullRushVelocity;
	private int bullRushCoolDown;
	public Direction direction;
	
	private Image spriteSheet = Game.assets.getImage("res/Maniac.png");
	private Image walkDown = spriteSheet.getSubImage(1, 1, 68, 136);
	private Image walkUp = spriteSheet.getSubImage(69, 1, 68, 136);
	private Image walkLeft = spriteSheet.getSubImage(137, 1, 68, 136);
	private Image walkRight = spriteSheet.getSubImage(204, 1, 68, 136);
	
	private Image steamSpriteSheet = Game.assets.getImage("res/blueFlameSpriteSheet.png");
	
	Animation sprite, walkingDown, walkingUp, walkingLeft, walkingRight, flame;
	
	
	private boolean bullRush;

	private boolean filterSwitch = false;
	
	private Color flameFilter;

	private boolean alreadySmashed;
	private boolean smash;

	
	private Sound alerted;
	
	public Maniac(Dungeon dungeon, Room room, int x, int  y) throws SlickException
	{
		super(dungeon, room, x, y);
		
		this.dungeon = dungeon;
		
		this.alerted = new Sound("res/audio/enemyAlert.wav");
		
		this.image = Game.assets.getImage("res/Maniac.png").getSubImage(1, 1, 64, 64);
		
		walkingDown = new Animation(new SpriteSheet(walkDown, 68, 68), 400);
		walkingUp = new Animation(new SpriteSheet(walkUp, 68, 68), 400);
		walkingLeft = new Animation(new SpriteSheet(walkLeft, 68, 68), 400);
		walkingRight = new Animation(new SpriteSheet(walkRight, 68, 68), 400);
		flame = new Animation(new SpriteSheet(steamSpriteSheet, 44, 64), 100);
		
		this.damage = 1;
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		this.bullRushVelocity = 0.5f;
		this.maxBullRushVelocity = 1.1f;
		
		this.health = this.maximumHealth = 5;
		
		this.myFilter = new Color(redFilter, greenFilter, blueFilter, 1f);
		this.flameFilter = new Color(1f, 1f, 1f, .6f);
		
		
		double a = Math.random();
		
		if(a > 0.5)
			{
				right = true; 
				this.direction = Direction.EAST;
			}
			else
			{
				down = true; 
				this.direction = Direction.SOUTH;
			}
		
		bullRush = false;
		alreadySmashed = false;
		bullRushCoolDown = 0;
		
	}
	
	public void update(int delta)
	{
		
		getNextPosition(delta);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// if hits wall change direction
		if(!bullRush && bullRushCoolDown == 0)
		{
			this.maximumVelocity = 0.03f;
			if(right && dx == 0)
			{
				right = false;
				left = true;
			}
			else if(left && dx == 0) 
			{
				right = true;
				left = false;
			}
		
			if(up && dy == 0)
			{
				up = false;
				down = true;
			}
			else if(down && dy == 0) 
			{
				up = true;
				down = false;
			}
		}
		
		myFilter = new Color(redFilter, greenFilter, blueFilter, this.filterAlpha);
			
		// check blinking
		if (blinkCooldown > 0)
		{
			blinkCooldown --;
		}
		
		if(blinkCooldown == 0)
		{
			blinking = false;
		}
		
		// check bullRush
		if(bullRushCoolDown > 0)
		{
			bullRushCoolDown -= delta;
		}
		
		if(bullRushCoolDown <= 0)
		{
			bullRushCoolDown = 0;
			bullRush = false;
			alreadySmashed = false;
		}
		
		if(bullRushCoolDown > 0)
		{
			
			if(!filterSwitch)
			{
				this.greenFilter -= .1f;
				this.blueFilter -= .1f;
				
				if(greenFilter < .2f || blueFilter < .2f)
				{
					filterSwitch = true;
				}
			}
			
			if(filterSwitch)
			{
				this.greenFilter += .1f;
				this.blueFilter += .1f;
				
				if(this.greenFilter > .9f || this.blueFilter > .9f)
				{
					filterSwitch = false;
				}
			}
			 
		}
		else 
		{
			this.greenFilter = 1f; this.blueFilter = 1f;
		}
			
		if(bullRush && bullRushCoolDown > 0 && bullRushCoolDown < 2000 && dx == 0 || bullRush && bullRushCoolDown > 0 && bullRushCoolDown < 2000 && dy == 0)
		{
			smash = true;
		}
		else smash = false;
		
		
		if(this.getRoom() == this.dungeon.gamedata.hero.getRoom())
			{
				if(up && this.dungeon.gamedata.hero.getX() > this.x - this.getHalfWidth() && this.dungeon.gamedata.hero.getX() < this.x + this.getHalfWidth() && bullRushCoolDown == 0)
				{
					if(this.dungeon.gamedata.hero.getY() < this.y - this.getHalfHeight())
					{
						bullRush = true;
						bullRushCoolDown = 2800;
						alerted.play();
						startBullRush();
					}
				}
				else if(down && this.dungeon.gamedata.hero.getX() > this.x - this.getHalfWidth() && this.dungeon.gamedata.hero.getX() < this.x + this.getHalfWidth() && bullRushCoolDown == 0)
				{
					if(this.dungeon.gamedata.hero.getY() > this.y + this.getHalfHeight())
					{

						bullRush = true;
						bullRushCoolDown = 2800;
						alerted.play();
						startBullRush();

					}
				}
				
				if(left && this.dungeon.gamedata.hero.getY() > this.y - this.getHalfHeight() && this.dungeon.gamedata.hero.getY() < this.y + this.getHalfHeight() && bullRushCoolDown == 0)
				{
					if(this.dungeon.gamedata.hero.getX() < this.x - this.getHalfWidth())
					{
						bullRush = true;
						bullRushCoolDown = 2800;
						alerted.play();
						startBullRush();
					}
				}
				else if(right && this.dungeon.gamedata.hero.getY() > this.y - this.getHalfHeight() && this.dungeon.gamedata.hero.getY() < this.y + this.getHalfHeight() && bullRushCoolDown == 0)
				{
					if(this.dungeon.gamedata.hero.getX() > this.x + this.getHalfWidth())
					{
						bullRush = true;
						bullRushCoolDown = 2800;
						alerted.play();
						startBullRush();
					}
				}
			}
	
	}
	
	private void startBullRush()
	{
		bullRush = true;
		bullRushCoolDown = 2800;
	}
	
	private void getNextPosition(int delta) 
	{
		if(!bullRush)
		{
			if(left)
			{
				this.direction = Direction.WEST;
				sprite = walkingLeft;
				dx -= acceleration;
				if(dx < -maximumVelocity)
				{
					dx = -maximumVelocity;
				}
				dx *= delta;
			}
		
			else if(right) 
			{
				this.direction = Direction.EAST;
				sprite = walkingRight;
				dx += acceleration;
				if(dx > maximumVelocity)
				{
					dx = maximumVelocity;
				}
			
				dx *= delta;
			}
		
			if(up) 
			{
				this.direction = Direction.NORTH;
				sprite = walkingUp;
				dy -= acceleration;
				if(dy < -maximumVelocity)
				{
					dy = -maximumVelocity;
				}
				dy *= delta;
			}
			else if(down) 
			{
				this.direction = Direction.SOUTH;
				sprite = walkingDown;
				dy += acceleration;
			if(dy > maximumVelocity)
				{
				dy = maximumVelocity;
				}
			dy *= delta;
			}	
		}
		else if(bullRush && bullRushCoolDown < 2000 && bullRushCoolDown > 0)
			{
				bullRush(delta);
			}
		else if(bullRush && bullRushCoolDown > 2000)
		{
			dx = .00001f;
			dy = .00001f;
		}
	}
	
	public void bullRush(int delta)
	{
		if(left)
		{
			this.direction = Direction.WEST;
			dx -= bullRushVelocity;
			if(dx < -maxBullRushVelocity)
			{
				dx = -maxBullRushVelocity;
			}
			dx *= delta;
		}
		else if(right) 
		{
			this.direction = Direction.EAST;
			dx += bullRushVelocity;
			if(dx > maxBullRushVelocity)
			{
				dx = maxBullRushVelocity;
			}
		
			dx *= delta;
		}
	
		if(up) 
		{
			this.direction = Direction.NORTH;
			dy -= bullRushVelocity;
			if(dy < -maxBullRushVelocity)
			{
				dy = -maxBullRushVelocity;
			}
			dy *= delta;
		}
		else if(down) 
		{
			this.direction = Direction.SOUTH;
			dy += bullRushVelocity;
		if(dy > maxBullRushVelocity)
			{
			dy = maxBullRushVelocity;
			}
		dy *= delta;
		}	
	}
	
	public void render(Graphics graphics, Camera camera)
	{			
		if(blinking) 
		{
			if(blinkCooldown % 4 == 0) 
			{
				return;
			}
		}
		
		if(smash && !alreadySmashed)
		{
			RoomFollowingCamera cam = (RoomFollowingCamera)camera;
			cam.setShaking(this.direction, 50);
			alreadySmashed = true;
			Game.assets.playSoundEffectWithoutRepeat("wallsShaking");
		}
		
//		super.render(graphics, camera);
		if(bullRushCoolDown > 2000 && this.direction == Direction.SOUTH)
		{
			flame.draw(this.getX() - this.getHalfWidth() - camera.getX(), (this.getY() - this.getHalfHeight() - camera.getY()) - this.getHalfHeight(), flameFilter);
		}
		
		if(this.direction == Direction.NORTH)
		{
			walkingUp.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY(), myFilter);
		}
		else if (this.direction == Direction.SOUTH)
		{
			walkingDown.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY(), myFilter);
		}
		else if (this.direction == Direction.EAST)
		{
			walkingRight.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY(), myFilter);
		}
		else if (this.direction == Direction.WEST)
		{
			walkingLeft.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY(), myFilter);
		}
		
		if(bullRushCoolDown > 2000 && (this.direction == Direction.EAST || this.direction == Direction.NORTH))
		{
			flame.draw(this.getX() - this.getHalfWidth() - camera.getX(), (this.getY() - this.getHalfHeight() - camera.getY()) - this.getHalfHeight(), flameFilter);
		}
		else if(bullRushCoolDown > 2000 && this.direction == Direction.WEST)
		{
			flame.draw(this.getX() - this.getHalfWidth() - camera.getX() + 20, (this.getY() - this.getHalfHeight() - camera.getY()) - this.getHalfHeight(), flameFilter);
		}
	}
}


