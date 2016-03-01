import java.util.ArrayList;
import org.newdawn.slick.SlickException;

public class Elephant extends AIKart
{
	/* Starting Position */
	public final int StartX      = (int) WorldCoordinates.Elephant.getPosX();
	public final int StartY      = (int) WorldCoordinates.Elephant.getPosY();
		
	/** Initializing the bot with the appropriate image sprite */
	public Elephant(String kartAssetLocation, String kartName, ArrayList<Point2D> waypoints) throws SlickException 
	{
		createSprite(kartAssetLocation, kartName);
		updateStartLocation(StartX, StartY);
	}
	
	/** Logic for the kart */
	public void move(World world, int delta)
	{
		update(world, delta);
	}
	
	
	
	
	
	
	
	
	
	/** Method that updates the bot class */
//	@Override
//	public void update(World world, int delta)
//	{
//		// AI specific terms
//		double botMoveDir   = 0;
//		double botRotateDir = 0;
//
//		// Get the waypoints from the world
//		ArrayList<Point2D> waypoints = world.getWaypoints();
//
//		/** Store the previous position before updating */
//		storePreviousPosition(getPosX(), getPosY());
//
//		/** Work out the xComponent length and yComponent length given a length */
//		double xComponent = getAngle().getXComponent(getVelocity() * delta);
//		double yComponent = getAngle().getYComponent(getVelocity() * delta);
//
//		/** Current waypoint position*/
//		if(getCurrentWaypointPosition() < waypoints.size())
//		{
//			setCurrentWaypointX((int) waypoints.get(getCurrentWaypointPosition()).getPosX());
//			setCurrentWaypointY((int) waypoints.get(getCurrentWaypointPosition()).getPosY());
//
//			// Update the movement and rotation
//			botMoveDir += Kart.normalKartSpeed;
//		}
//		else
//		{
//			// When the AI kart crosses the finish line, it will stop moving 
//			setFinishedRace(true);
//			botMoveDir = 0;
//		}
//
//		// Check to see how much the kart needs to rotate
//		botRotateDir = botRotateKart(xComponent, yComponent);
//
//		// Move
//		move(world, botRotateDir, botMoveDir, delta);	
//		
//        if(!getFinishedRace())
//        {
//        	if(this.distanceTo(waypoints.get(getCurrentWaypointPosition())) <= waypointOffset)
//        		setCurrentWaypointPosition(getCurrentWaypointPosition()+1);
//        }
//         
//	}
}
