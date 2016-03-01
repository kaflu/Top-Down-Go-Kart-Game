import org.newdawn.slick.SlickException;

public abstract class Item extends GameObject
{		
	/** Item name and position */
	private float posX;
	private float posY;
	private String itemName;

	/** Method to use the item in the Player's inventory */
	public abstract void use(Player kart, World world, int delta) throws SlickException;
		
	// Store the item's position
	public void storeItem(Point2D itemlocale, String itemName) 
	{
		this.posX = itemlocale.getPosX();
		this.posY = itemlocale.getPosY();
		this.itemName = itemName;
	}
		
	//----------------------------------------------------GETTERS AND SETTERS--------------------------------------------------------------//
	public float getPosX()
	{
		return posX;
	}
	
	public float getPosY()
	{
		return posY;
	}
	
	public String getItemName()
	{
		return this.itemName;
	}
	
}
