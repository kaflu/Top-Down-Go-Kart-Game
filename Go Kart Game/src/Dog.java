import java.util.ArrayList;
import org.newdawn.slick.SlickException;

public class Dog extends AIKart
{
	/* Starting Position */
	public final int StartX      = (int) WorldCoordinates.Dog.getPosX();
	public final int StartY      = (int) WorldCoordinates.Dog.getPosY();

	/** Constants specific to dog */
	private final double aheadAccelerationRate  = 0.00045;
	private final double behindAccelerationRate = 0.00055;
	private final double normalAccelerationRate = 0.0005;
		
	/** Initializing the bot with the appropriate image sprite */
	public Dog(String kartAssetLocation, String kartName, ArrayList<Point2D> waypoints) throws SlickException 
	{
		createSprite(kartAssetLocation, kartName);
		updateStartLocation(StartX, StartY);
	}

	/** Logic for the kart */
	public void move(World world, int delta)
	{
		dogStrategy(world);
		update(world, delta);
	}
	
	/** Logic for the strategy */
	public void dogStrategy(World world)
	{
      // Dog Specific Strategy
      if(world.getPlayer().getPosY() > this.getPosY())
          this.setVelocityConstant(aheadAccelerationRate);
   
      else if(world.getPlayer().getPosY() < this.getPosX())
    	  this.setVelocityConstant(behindAccelerationRate);
      
      else
    	  this.setVelocityConstant(normalAccelerationRate); 
	}
}
	
