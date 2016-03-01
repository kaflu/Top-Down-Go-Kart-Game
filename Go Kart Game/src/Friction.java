import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Friction 
{
	// Constants
	private int layerIndex;
	private int stoppingFriction;
	
	// Classes
	private TiledMap background;

	/** Initializing the player with the appropriate image sprite */
	public Friction(TiledMap background, int layerIndex, int stoppingFriction) 
	throws SlickException  
	{
		this.background = background;
		this.layerIndex = layerIndex;
		this.stoppingFriction = stoppingFriction;
	}
	
	/** Check if the friction is enough to stop the kart */
    public void checkFriction(Kart k, float currentPosX, float currentPosY, float prevPosX, float prevPosY)
    {
    	if(getFriction(currentPosX, currentPosY) == stoppingFriction)
    	{
    		k.updateFriction(prevPosX, prevPosY);
    	} 
    	return;
    }
	
    /** Function to get the friction for the player position */
    public double getFriction(float PosX, float PosY)
    {
    	int TileX = (int) (PosX / background.getTileWidth());
    	int TileY = (int) (PosY / background.getTileHeight());
    	
    	int groundType = background.getTileId(TileX, TileY, layerIndex);
    	String frictionProperty = background.getTileProperty(groundType, "friction", null);
    	double friction = Double.parseDouble(frictionProperty); 
    	
    	return friction;
    }
        
}
