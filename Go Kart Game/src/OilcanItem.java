import org.newdawn.slick.SlickException;

public class OilcanItem extends Item
{		
	private final int oilslickOffset = 45;  // To avoid the player colliding with the hazard when used
	private Angle     kartRear = new Angle(Math.PI);
	
	/** Item initialisation */
	public OilcanItem(String assetLocation, String assetName, Point2D itemlocale) throws SlickException 
	{
		// Create
		createSprite(assetLocation, assetName);
		storeItem(itemlocale, assetName);
	}
	
	/** If item is used create a hazard */
	@Override
	public void use(Player kart, World world, int delta) throws SlickException
	{		
		// Create the hazard
		Hazard oilslick = new OilslickHazard(AssetLocations.oilslickAssetLocation, AssetLocations.oilcanName, kart);

		// Calculate the appropriate angle and position to place it at
		Angle angle   = kart.getAngle().add(kartRear);
		float hazardX = (float) (kart.getPosX() + angle.getXComponent(oilslickOffset));
		float hazardY = (float) (kart.getPosY() + angle.getYComponent(oilslickOffset));
		
		// Store the location in GameObject
		oilslick.storeObjectPosition(hazardX, hazardY);
		
		// Add to the hazards list in world
		world.getHazardlist().add(oilslick);
	}
}
