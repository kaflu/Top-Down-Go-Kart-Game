import org.newdawn.slick.SlickException;

public class OilslickHazard extends Hazard
{	
	protected final int    spinDuration = 700;
	protected final double spinRotation = 0.008;
	private final int	   orientation  = 1; 	// Clockwise

	// Invoke base class method declaration
	public OilslickHazard(String assetLocation, String assetName, Kart player) throws SlickException
	{
		// Create the appropriate sprite
		createSprite(assetLocation, assetName);
		
		// Update the hazard attributes
		this.setSpinDuration(spinDuration);
		this.setSpinRotation(spinRotation);
		this.setOrientation(orientation);
	}

	// Checks the conditions to to see if the hazard can be deployed
	@Override
	public void update(World world) 
	{
		if(!destroyed)
		{
			if(world.blockedAt(getPosX(), getPosY()) || world.didKartCollide(this))
				destroyed = true;
		}	
	}	
	
	// Lets the Kart know which hazard hit it
	@Override
	public void affect(Kart kart)
	{
		kart.setKartHazard(this);
	}	
}
