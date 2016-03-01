import java.util.ArrayList;
import org.newdawn.slick.SlickException;

public class Octopus extends AIKart
{
	/* Starting Position */
	public final int StartX      = (int) WorldCoordinates.Octopus.getPosX();
	public final int StartY      = (int) WorldCoordinates.Octopus.getPosY();
	
	/** Constants specific to octopus */
	public final int octopusStartChase = 100;
	public final int octopusEndChase   = 250;
	
	/** Initializing the bot with the appropriate image sprite */
	public Octopus(String kartAssetLocation, String kartName, ArrayList<Point2D> waypoints) throws SlickException 
	{
		createSprite(kartAssetLocation, kartName);
		updateStartLocation(StartX, StartY);
	}
	
	/** Logic for the kart */
	public void move(World world, int delta)
	{
		octopusStrategy(world);
		update(world, delta);
	}

	/** Logic for the strategy */
	public void octopusStrategy(World world)
	{
		if(this.distanceTo(world.getPlayer()) >= octopusStartChase && this.distanceTo(world.getPlayer()) <= octopusEndChase)
			this.setDontFollowWaypoint(true);

		else
			this.setDontFollowWaypoint(false);
	}
}
