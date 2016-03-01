import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameObject
{
	/** Global variables */
	public static final int COLLISION_DISTANCE = 40;
	public static final int ITEM_OFFSET = 40;
	
	/** Initialized local position variables for object class */
	private float posX;
	private float posY;
	
	/** Game object attributes */
	private Image sprite;
	private String spriteName;
	private Angle angle = new Angle(0); // Default orientation for sprite to be rendered
	
	/** Create a sprite of the gameobject */
	public void createSprite(String spriteAssetLocation, String spriteName) throws SlickException
	{
		this.sprite     = new Image(spriteAssetLocation);
		this.spriteName = spriteName;
	}
	
	/** Determines whether or not a game object collides with another game object */
	public boolean collides(GameObject object)
	{		
		Point2D self  = new Point2D("self" , this.getPosX()  , this.getPosY());
		Point2D other = new Point2D("other", object.getPosX(), object.getPosY());
		
		return self.distanceTo(other) <= COLLISION_DISTANCE;
	}
	
	/** Determines the distance between 2 game objects */
	public float distanceTo(GameObject object)
	{
		Point2D pointA = new Point2D("pointA", this.getPosX(), this.getPosY());
		Point2D pointB = new Point2D("pointB", object.getPosX(), object.getPosY());
		
		return pointA.distanceTo(pointB);	
	}
	
	/** Render the game object into the world */
	public void render(Camera camera)
	{
		float objectRenderX = this.getPosX() - camera.getCamTopX();
		float objectRenderY = this.getPosY() - camera.getCamTopY();
	
		sprite.drawCentered(objectRenderX , objectRenderY);	
		sprite.setRotation(getAngleDegrees());
	}
	
	/** Stores the object position */
	public void storeObjectPosition(float posX, float posY)
	{
		this.posX = posX;
		this.posY = posY;
	}	
	
	/** Draw the gameobject image to the panel */	
	public void drawImage(float panelX, float panelY, Item item)
	{
		sprite.draw(panelX,panelY);
	}	
	
	//----------------------------------------------------GETTERS AND SETTERS--------------------------------------------------------------//
	public float getPosX()
	{
		return this.posX;
	}
	
	public float getPosY()
	{
		return this.posY;
	}
	
	public void setPosX(float posX)
	{
		this.posX = posX;
	}
	
	public void setPosY(float posY)
	{
		this.posY = posY;
	}
	
	public Angle getAngle()
	{
		return this.angle;
	}
	
	public void setAngle(Angle angle)
	{
		this.angle = angle;
	}
	
	public void setAngle(double angle)
	{
		this.angle = new Angle(angle);
	}
	
	public float getAngleDegrees()
	{
		return (float) (getAngle().getDegrees());
	}
	
	public Image getSprite()
	{
		return this.sprite;
	}
	
	public String getSpriteName()
	{
		return this.spriteName;
	}
	
}
