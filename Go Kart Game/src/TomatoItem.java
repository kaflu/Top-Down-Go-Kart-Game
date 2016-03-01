import org.newdawn.slick.SlickException;

public class TomatoItem extends Item
{	
	private final int tomatoOffset = 45; // To avoid the player colliding with the hazard when used
	
	// Invoke base class method declaration
	public TomatoItem(String assetLocation, String assetName, Point2D itemLocale) throws SlickException 
	{
		createSprite(assetLocation, assetName);
		storeItem(itemLocale, assetName);
	}
	
	/** If item is used create a hazard */
	@Override
	public void use(Player kart, World world, int delta) throws SlickException
	{
		// Initialise the hazard
		Hazard tomatoProjectile = new TomatoProjectileHazard(AssetLocations.tomatoProjectileAssetLocation, AssetLocations.tomatoProjectile, kart);
		
		// Work out its positions
		Angle angle = new Angle(kart.getRotation());
		float hazardX = (float) angle.getXComponent(tomatoOffset);
		float hazardY = (float) angle.getYComponent(tomatoOffset);
		
		// Store the positions and update the hazards list
		tomatoProjectile.storeObjectPosition(world.getPlayer().getPosX() + hazardX, world.getPlayer().getPosY() + hazardY);
		world.getHazardlist().add(tomatoProjectile);		
	}
}
