import org.newdawn.slick.SlickException;

public class Camera 
{	
	/** Initialized the local variables that will be used in the Camera class */
	
	private int screenWidth;
	private int screenHeight;
	
	private int camPlayerX;
	private int camPlayerY;

	private int camTopX = 0;
	private int camTopY = 0;
	
	private int tileWidth;
	private int tileHeight;
	
	private int offsetX = 0;
	private int offsetY = 0;
	
	private int sx;
	private int sy;
	
	/** Initializing camera class */
	public Camera(int playerX, int playerY, int tileWidth, int tileHeight, int screenWidth, int screenHeight) 
	throws SlickException
	{
		/** Set the player x,y current position */ 
		this.camPlayerX = playerX;
		this.camPlayerY = playerY;
		
		/** Set the tile width and tile height */
		this.tileWidth  = tileWidth;
		this.tileHeight = tileHeight;
		
		/** Set the screen width and screen height */
		this.screenWidth  = screenWidth;
		this.screenHeight = screenHeight;
		
		/** Camera reference to the origin on the screen */
		this.camTopX = this.camPlayerX - screenWidth  / 2; 
		this.camTopY = this.camPlayerY - screenHeight / 2;
		
		/** Remainder of the tile in terms of the pixels */
		this.offsetX =  -(camPlayerX % tileWidth);
		this.offsetY =  -(camPlayerY % tileHeight);	
		
		/** Location of the tile to render at */
		this.sx = camPlayerX / tileWidth  -  (screenWidth  / tileWidth) / 2;
		this.sy = camPlayerY / tileHeight -  (screenHeight / tileHeight) / 2;	
	}
	
	/** Reads in updates based on the player position */ 
	public void update(int posX, int posY)
	{
		// Get the current position
		this.camPlayerX = posX;
		this.camPlayerY = posY;

		/**
		 *  Update the camera with this position.
		 *  Same process that happens when the camera was initialized 
		 */
		updateCamera(this.camPlayerX, this.camPlayerY);
	}
	
	// For further reference
	public void setScreenDimensions(int screenWidth, int screenHeight)
	{
		this.screenWidth  = screenWidth;
		this.screenHeight = screenHeight;	
	}
	
	/** Update the camera based on current position */
	public void updateCamera(int posX, int posY)
	{
		this.camTopX = this.camPlayerX - screenWidth / 2; 
		this.camTopY = this.camPlayerY - screenHeight / 2;
		
		this.offsetX = -(camPlayerX % tileWidth);
		this.offsetY = -(camPlayerY % tileHeight);	
		
		this.sx = camPlayerX / tileWidth  -  (screenWidth  / tileWidth)  / 2;
		this.sy = camPlayerY / tileHeight -  (screenHeight / tileHeight) / 2;	
	}
		
	//----------------------------------------------------GETTERS AND SETTERS--------------------------------------------------------------//
	public int getcamPlayerX()
	{
		return camPlayerX;
	}
	
	public int getcamPlayerY()
	{
		return camPlayerY;
	}
	
	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public int getSx() {
		return sx;
	}

	public int getSy() {
		return sy;
	}
	
	public int getCamTopX() {
		return camTopX;
	}

	public int getCamTopY() {
		return camTopY;
	}
}
