import org.newdawn.slick.SlickException;

public class TomatoProjectileHazard extends Hazard
{
	private final double flyingVelocity = 1.7;
	private final int    spinDuration   = 700;
	private final double spinRotation   = 0.008;
	private final int	 orientation    = 1; 	// Clockwise
	
	private Angle tomatoAngle;
	
	// Invoke base class method declaration
	public TomatoProjectileHazard(String assetLocation, String assetName, Kart player) throws SlickException
	{
		// Create the appropriate sprite
		createSprite(assetLocation, assetName);
		
		// Update the hazard attributes
		this.setSpinDuration(spinDuration);
		this.setSpinRotation(spinRotation);
		this.setOrientation(orientation);
		
		// Gets the tomato angle based on the player's angle
		this.tomatoAngle = player.getAngle();
	}

	/** Update the tomato hazard */
	@Override
	public void update(World world) 
	{	
		// Work out the trajectory for the tomato
		double xComponent = tomatoAngle.getXComponent(flyingVelocity);
		double yComponent = tomatoAngle.getYComponent(flyingVelocity);
		
		setPosX((float) (xComponent + getPosX()));
		setPosY((float) (yComponent + getPosY()));
		
		if(!destroyed)
		{
			if(world.blockedAt(getPosX(), getPosY()) || world.didKartCollide(this))
			{
				destroyed = true;
			}
		}		
	}
	
	/** Lets the kart know which hazard hit it */
	@Override
	public void affect(Kart kart)
	{
		kart.setKartHazard(this);
	}
}
