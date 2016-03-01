import org.newdawn.slick.SlickException;

public class BoostItem extends Item
{	
	public static int     boostDuration = 3000;
	public static int     boostMoveDir = 1;
	public static double  boostAccelerationRate = 0.0008;
	
	// Invoke base class method declaration
	public BoostItem(String assetLocation, String assetName, Point2D itemlocale) throws SlickException 
	{
		createSprite(assetLocation, assetName);
		storeItem(itemlocale, assetName);
	}
	
	// If boost is used
	public void use(Player kart, World world, int delta) throws SlickException
	{
		kart.Boost();
	}
}
