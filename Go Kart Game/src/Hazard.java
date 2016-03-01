public abstract class Hazard extends GameObject
{	
	private int    spinDuration;
	private double spinRotation;
	private int	   orientation;
	
	/** Determine if a hazard has been destroyed by a kart */
	protected boolean destroyed = false;
	
	/** Update for each hazard */
	public abstract void update(World world);	
	
	/** How the hazard interacts with the karts */
	public abstract void affect(Kart kart);
	
	/** Render the hazards if they have not been destroyed */
	@Override
	public void render(Camera camera)
	{
		if(!destroyed)
		{
			float objectRenderX = this.getPosX() - camera.getCamTopX();
			float objectRenderY = this.getPosY() - camera.getCamTopY();
	
			getSprite().drawCentered(objectRenderX , objectRenderY);	
			getSprite().setRotation(getAngleDegrees());
		}
	}

	//----------------------------------------------------GETTERS AND SETTERS--------------------------------------------------------------//
	public int getSpinDuration()
	{
		return spinDuration;
	}

	public void setSpinDuration(int spinDuration)
	{
		this.spinDuration = spinDuration;
	}

	public double getSpinRotation()
	{
		return spinRotation;
	}

	public void setSpinRotation(double spinRotation)
	{
		this.spinRotation = spinRotation;
	}
	
	public void setOrientation(int orientation)
	{
		this.orientation = orientation;
	}
	
	public int getOrientation()
	{
		return this.orientation;
	}
}
