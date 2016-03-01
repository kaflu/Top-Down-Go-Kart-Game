import java.util.ArrayList;

public class Kart extends GameObject
{			
	/** Constants inherent to all karts*/
	public static int    waypointOffset  = 250; 
	public static double normalKartSpeed  = 1; 
	
	public static double defaultVelocityConstant = 0.0005;
	public static double defaultRotationConstant = 0.004;
	
	// These will be modified by the karts, so a copy is necessary 
	private double velocityConstant = defaultVelocityConstant;
	private double rotationConstant = defaultRotationConstant;
	
	// Kart physics variables
	private double velocity = 0;
	private double rotation = 0;
	private double friction = 0;
		
	/** Variables inherent to all karts */
	
	// Initialized local position variables for kart classes 
	private float posX;
	private float posY;
	
	// Previous local position before position is updated 
	private float prevPosX;
	private float prevPosY;
	
	// Vector Length
	private double xComponent;
	private double yComponent;
	
	// Next position before update
	private float nextPosX;
	private float nextPosY;
	
	// Time
	protected int spinTimer = 0;
	
	// Results of spinning
	protected double spinMoveDir = 1;
	protected double spinRotateDir = 1;
	
	// Specific hazard and check
	private Hazard kartHazard;
	private boolean affectedByHazard = false;
	
	/** Update the starting locations of all the karts */
	public void updateStartLocation(int startX, int startY)
	{
		this.posX = startX;
		this.posY = startY;
	}
				
	/** Handle the hazards */
	public void handleHazards(World world)
	{
		/** Add the collected hazard to a arraylist */
		ArrayList<Hazard> toRemove = new ArrayList<Hazard>();	
		
		for(Hazard hazard: world.getHazardlist())
		{
			// If a kart hits a hazard
			if(hazard.collides(this))
			{
				// The hazard will affect the kart
				hazard.affect(this);
				// The kart can only be affected by a hazard once
				affectedByHazard = true;
				// Hazard to be removed from the world
				toRemove.add(hazard);
			}
		}
		/** Remove the arraylist from the world */
		world.getHazardlist().removeAll(toRemove);		
	}	
	
	/** Method that moves the player class
	 * @param world the World class is passed in
	 * @param rotateDir the direction the player will turn to depending on the key pressed
	 * @param moveDir the xy direction the player will move depending on the key pressed 
	 * @param delta delta = 1
	 */
	public void move(World world, double rotateDir, double moveDir, int delta) 
	{	
		/** If the kart crosses the finish line, it stops moving */
		if(world.getCrossLine())
		{
			moveDir = 0;
			rotateDir = 0;
		}
				
		/** Store the previous position before updating */
		storePreviousPosition(this.posX, this.posY);
		
		this.friction = world.getFriction(this.posX, this.posY);
		
		/** Update velocity and rotation */		
		velocity = (velocity + velocityConstant * moveDir   * delta) * Math.pow((1 - friction), delta);
		rotation =  rotation + rotationConstant * rotateDir * delta;
		
		/** Work out the xComponent length and yComponent length given a length */
		double xComponent = getAngle().getXComponent(velocity * delta);
		double yComponent = getAngle().getYComponent(velocity * delta);
		
		/** Update the angle based on rotation */
		setAngle(rotation);
		
		/** The next position the kart will move to */
		this.nextPosX = (float) (posX + xComponent);
		this.nextPosY = (float) (posY + yComponent);
		
		// Check if we are blocked
		if(world.blockedAt(nextPosX, nextPosY))
		{
			this.velocity = 0;
		} 
		else 
		{
			/** Update player position on xComponent and yComponent length */
			this.posX = this.nextPosX;
			this.posY = this.nextPosY;
			
			/** Check if moving caused a collision **/
			if (world.didCrash(this))
			{
				this.velocity = 0;
				this.posX = this.prevPosX;
				this.posY = this.prevPosY;
			}
		}
	}
	
	/** Karts use this move method when affected by a hazard 
	 * @param spinMoveDir movement is set to 1
	 * @param spinRotateDir rotation is set to clockwise */
	public void hazardMove(World world, double spinRotateDir, double spinMoveDir, int delta)
	{		
		/** Store the previous position before updating */
		storePreviousPosition(this.posX, this.posY);
		
		// Get the friction from the world
		this.friction = world.getFriction(this.posX, this.posY);
		
		// See how much the kart should spin
		Angle spinValue = new Angle(kartHazard.getSpinRotation());
		this.setAngle(this.getAngle().add(spinValue));
		
		// Get the direction to spin
		spinRotateDir = this.kartHazard.getOrientation();
	
		// Update velocity	
		velocity = (velocity + velocityConstant * spinMoveDir   * delta) * Math.pow((1 - friction), delta);
		
		/** Work out the xComponent length and yComponent length given a length */
		double xComponent = getAngle().getXComponent(velocity * delta);
		double yComponent = getAngle().getYComponent(velocity * delta);
		
		/** The next position the kart will move to */
		this.nextPosX = (float) (posX + xComponent);
		this.nextPosY = (float) (posY + yComponent);

		// Check if we are blocked
		if(world.blockedAt(nextPosX, nextPosY))
		{
			this.velocity = 0;
		} 
		else 
		{
			/** Update player position on xComponent and yComponent length */
			this.posX = this.nextPosX;
			this.posY = this.nextPosY;
			
			/** Check if moving caused a collision **/
			if (world.didCrash(this))
			{
				this.velocity = 0;
				this.posX = this.prevPosX;
				this.posY = this.prevPosY;
			}
		}
	}
	
	/** Update the friction */
	public void updateFriction(float prevposX, float prevposY)
	{
		this.posX = prevposX;
		this.posY = prevposY;
		this.velocity = 0;
	}
	
	/** Store the previous position */
	public void storePreviousPosition(float prevposX, float prevposY)
	{
		this.prevPosX = prevposX;
		this.prevPosY = prevposY;
	}	
			
//----------------------------------------------------GETTERS AND SETTERS--------------------------------------------------------------//
	public float getPosX()
	{
		return posX;
	}
	
	public void setPosX(float posX)
	{
		this.posX = posX;
	}
	
	public double getVelocity()
	{
		return this.velocity;
	}
	
	public void setVelocity(double velocity)
	{
		this.velocity = velocity;
	}
	
	public double getRotation()
	{
		return this.rotation;
	}
	
	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}

	public float getPosY()
	{
		return this.posY;
	}
	
	public void setPosY(float posY)
	{
		this.posY = posY;
	}

	public double getXComponent()
	{
		return this.xComponent;
	}
	
	public double getYComponent()
	{
		return this.yComponent;
	}
	
	
	public float getPrevPosX()
	{
		return prevPosX;
	}
	
	public void setPrevPosX(float prevPosX)
	{
		this.prevPosX = prevPosX;
	}
	
	public float getPrevPosY()
	{
		return prevPosY;
	}
	
	public void setPrevPosY(float prevPosY)
	{
		this.prevPosY = prevPosY;
	}
	
	public float getNextPosX()
	{
		return this.nextPosX;
	}
	
	public void setNextPosX(float nextPosX)
	{
		this.nextPosX = nextPosX;
	}
	
	public float getNextPosY()
	{
		return this.nextPosY;
	}
	
	public void setNextPosY(float nextPosY)
	{
		this.nextPosY = nextPosY;
	}
	
	public double getFriction()
	{
		return this.friction;
	}
	
	public double getVelocityConstant()
	{
		return this.velocityConstant;
	}
	
	public void setVelocityConstant(double velocityConstant)
	{
		this.velocityConstant = velocityConstant;
	}
	
	public void setRotationConstant(double rotationConstant)
	{
		this.rotationConstant = rotationConstant;
	}

	public boolean isAffectedByHazard()
	{
		return affectedByHazard;
	}

	public void setAffectedByHazard(boolean affectedByHazard)
	{
		this.affectedByHazard = affectedByHazard;
	}
	
	public void setKartHazard(Hazard h)
	{
		this.kartHazard = h;
	}
	
	public Hazard getKartHazard()
	{
		return this.kartHazard;
	}
	
	
}
