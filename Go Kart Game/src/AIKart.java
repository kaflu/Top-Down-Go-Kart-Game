import java.util.ArrayList;

public abstract class AIKart extends Kart
{
	// Variables only the AI kart should know	
	private ArrayList<Point2D> waypoints;	
	private boolean finishedRace = false;
	private boolean dontFollowWaypoint = false;
	
	private int currentWaypointPosition = 0;
	private int currentWaypointX;
	private int currentWaypointY;
	
	/** Method that moves the bot classes */
	public void update(World world, int delta)
	{
		// Handle the hazards for the kart
		handleHazards(world);
		
		// Check what type of move they should perform
		checkMove(world, delta);
	}
	
	/** Check the item effects on the AI Kart */
	public void checkMove(World world, int delta)
	{
		if(this.isAffectedByHazard() && spinTimer < this.getKartHazard().getSpinDuration())
		{
			spinTimer++;
			this.hazardMove(world, this.spinRotateDir, this.spinMoveDir, delta);
		}
		else
		{
			spinTimer = 0;
			this.setAffectedByHazard(false);
			moveAI(world, delta);
		}
	}

	/** Move method for the AI */
	public void moveAI(World world, int delta)
	{
		double botMoveDir   = 0;
		double botRotateDir = 0;
		
		// Get the waypoints from the world
		this.waypoints = world.getWaypoints();

		/** Store the previous position before updating */
		storePreviousPosition(getPosX(), getPosY());

		// Work out the xComponent length and yComponent length given a length
		double xComponent = getAngle().getXComponent(getVelocity() * delta);
		double yComponent = getAngle().getYComponent(getVelocity() * delta);

		// Check the current waypoint and if we are following waypoints
		if(getCurrentWaypointPosition() < waypoints.size() && !dontFollowWaypoint)
		{
			setCurrentWaypointX((int) waypoints.get(getCurrentWaypointPosition()).getPosX());
			setCurrentWaypointY((int) waypoints.get(getCurrentWaypointPosition()).getPosY());

			// The kart rotates toward the waypoint
			botRotateDir = botRotateKart(xComponent, yComponent);
			botMoveDir  += Kart.normalKartSpeed;
		}
		// We are not following a waypoint but a Kart
		else if(dontFollowWaypoint)
		{
			// Set the waypoint to the a kart so the selected kart will follow it 
			setCurrentWaypointX((int) world.getPlayer().getPosX());
			setCurrentWaypointY((int) world.getPlayer().getPosY());
			
			botRotateDir = botRotateKart(xComponent, yComponent);		
			botMoveDir  += Kart.normalKartSpeed;	
		}
		// No more waypoints left, indicating the race has finished
		else
		{
			// When the AI kart crosses the finish line, it will stop moving 
			setFinishedRace(true);
			botMoveDir = 0;
		}
		
		/** Calls the move method inherent to all karts */
		move(world, botRotateDir, botMoveDir, delta);
			
		/** If we haven't finished the race, get the next waypoint */
        if(!getFinishedRace())
        {
        	if(this.distanceTo(waypoints.get(getCurrentWaypointPosition())) <= waypointOffset)
        		setCurrentWaypointPosition(getCurrentWaypointPosition()+1);
        }	
	}

	/** Each ai Kart will have its own special move abilities */
	public abstract void move(World world, int delta);
	
	/** Rotate AI kart function */
	public double botRotateKart(double xComponent, double yComponent)
	{
		// Position vector
        float wayX = currentWaypointX - getPosX();
        float wayY = currentWaypointY - getPosY();	
        
        /** Angle between the 2 vectors */
		double waypointAngle = Math.toDegrees(Math.atan2(xComponent * wayY - yComponent * wayX, xComponent * wayX + yComponent * wayY));
		
		return calculateRotationAngle(waypointAngle);
	}
	
	/** Calculate the waypoint angle */
	public double calculateRotationAngle(double waypointAngle)
	{		
		/** If angle is > 0 then turn right, angle < 0 then turn left, angle = 0 then don't turn*/
        if(waypointAngle > 0)
            return 1;
        else if(waypointAngle < 0)
            return -1;
        else
            return 0;
	}
	
	/** Rotate AI kart to specified kart */
	public double botRotateToKart(Kart kart, double xComponent, double yComponent)
	{
		// Position vector
        float kartX = kart.getPosX() - getPosX();
        float kartY = kart.getPosY() - getPosY();	
        
        /** Angle between the 2 vectors */
		double kartAngle = Math.toDegrees(Math.atan2(xComponent * kartY - yComponent * kartX, xComponent * kartX + yComponent * kartY));
		
		return calculateRotationAngle(kartAngle);
	}	
	
	//----------------------------------------------------GETTERS AND SETTERS--------------------------------------------------------------//
	public ArrayList<Point2D> getWaypoints()
	{
		return this.waypoints;
	}
	
	public int getCurrentWaypointX()
	{
		return this.currentWaypointX;
	}
	
	public int getCurrentWaypointPosition()
	{
		return this.currentWaypointPosition;
	}
	
	public void setCurrentWaypointPosition(int currentWaypointPosition)
	{
		this.currentWaypointPosition = currentWaypointPosition;
	}
	
	public void setCurrentWaypointX(int currentWaypointX)
	{
		this.currentWaypointX = currentWaypointX;
	}
	
	public int getCurrentWaypointY()
	{
		return this.currentWaypointY;
	}
	
	public void setCurrentWaypointY(int currentWaypointY)
	{
		this.currentWaypointY = currentWaypointY;
	}
	
	public boolean getFinishedRace()
	{
		return this.finishedRace;
	}
	
	public void setFinishedRace(Boolean status)
	{
		this.finishedRace = status;
	}

	public boolean isDontFollowWaypoint()
	{
		return this.dontFollowWaypoint;
	}

	public void setDontFollowWaypoint(boolean state)
	{
		this.dontFollowWaypoint = state;
	}		
}
