import org.newdawn.slick.SlickException;
import java.util.ArrayList;
import java.util.Arrays;

// Player Class
public class Player extends Kart 
{
	// Starting position
	public static final int StartX        = (int) WorldCoordinates.Donkey.getPosX();
	public static final int StartY        = (int) WorldCoordinates.Donkey.getPosY();

	// Starting item
	private Item item = null;
	
	// See if items were used
	private boolean itemUsed = false;
	private boolean usedBoostItem  = false;
	
	// Timer
	private int boostTimer = 0;
	
	/** Initializing the player with the appropriate image sprite */
	public Player(String kartAssetLocation, String kartName) throws SlickException 
	{
		createSprite(kartAssetLocation, kartName);
		updateStartLocation(StartX, StartY);
	}

	/** Method that updates the player class 
	 * @throws SlickException */
	public void update(World world, double rotateDir, double moveDir, int delta, boolean useItem) throws SlickException
	{			
		checkItems(useItem, world, delta);

		handleItems(world);
		
		handleHazards(world);
		
		checkMove(world, rotateDir, moveDir, delta);
	}

	/** Check to see if the player used a item 
	 * @throws SlickException */
	public void checkItems(boolean useItem, World world, int delta) throws SlickException
	{
		if(useItem && this.item != null)
		{
			this.item.use(this, world, delta);
			this.itemUsed = true;  				
			this.item = null;
		}
	}

	/** Check the item effects on the player Kart */
	public void checkMove(World world, double rotateDir, double moveDir, int delta)
	{
		// If a boost item was used
		if(usedBoostItem)
			moveDir = handleKartSpeed();
		
		
		if(this.isAffectedByHazard() && spinTimer < this.getKartHazard().getSpinDuration())
		{
			spinTimer++;
			this.hazardMove(world, this.spinRotateDir, this.spinMoveDir, delta);
		}
		else
		{
			spinTimer = 0;
			this.setAffectedByHazard(false);
			this.move(world, rotateDir, moveDir, delta);
		}
	}
	
	/** Method for the player if a boost item is used */
	public double handleKartSpeed()
	{
		/** Counting to boost duration */
		if(boostTimer < BoostItem.boostDuration)
		{
			boostTimer++;
			setVelocityConstant(BoostItem.boostAccelerationRate);
			return 1;
		}
		else
		{
			setVelocityConstant(Kart.defaultVelocityConstant);
			boostTimer = 0;
			usedBoostItem = false;
			return 0;
		}
	}

	/** Method for the player to handle items in the world */
	public void handleItems(World world)
	{		
		/** Add the collected items to a arraylist */
		ArrayList<Item> toRemove = new ArrayList<Item>();
		
		for(Item item : world.getItemlist())
		{
			if(this.collides(item))
			{
				this.item = item;
				toRemove.add(item);
			}
		}
		/** Remove the arraylist from the world */
		world.getItemlist().removeAll(toRemove);
	}
		
	/** Method to update the player's current rank */
	public int updateRank(float playerPosY, float bot1PosY, float bot2PosY, float bot3PosY, int ranking)
	{
		int rank = 1;
		float player = playerPosY;

		float[] kartRankings = {playerPosY, bot1PosY, bot2PosY, bot3PosY};
		Arrays.sort(kartRankings);

		for(int i = 0; i < kartRankings.length; i++)
		{
			if(player == kartRankings[i])
			{
				ranking = rank;
				return ranking;
			}
			rank++;
		}	
		return ranking;
	}
	
	public void Boost()
	{
		this.usedBoostItem = true;
	}
	
	
	//----------------------------------------------------GETTERS AND SETTERS--------------------------------------------------------------//
	/** Return the player's item to the world */
	public Item getPlayerItem()
	{
		return this.item;
	}
	
	/** Returns if the player used a item */
	public Boolean getItemUsed()
	{
		return this.itemUsed;
	}
	
	/** Check to see if the player is used boost */
	public Boolean getUsedBoostItem()
	{
		return this.usedBoostItem;
	}
}
