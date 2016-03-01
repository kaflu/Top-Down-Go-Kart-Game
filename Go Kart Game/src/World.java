/* SWEN20003 Object Oriented Software Development
 * player Racing Game
 * Author: <Lawrence Kuoch> <lkuoch>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 * The player (you) control the player
 */
public class World
{		 	
	/** Constants */
	private final int delta = 1;
	private final int stoppingFriction  = 1;
	
	// The finish line coordinates
	private final int finishLine = 1000;
	private boolean   crossLine  = false;
	
	private int layerIndex = 0;
	private int ranking    = 1; // Default ranking of player
	private int finishPlace;    // Race finish position

	/** Initialized class variables */
	private TiledMap background;
	private Camera   camera;
	private Panel    panel;
	
	// Players	
	private Kart  player;
	private Kart  bot1;
	private Kart  bot2;
	private Kart  bot3;
	
	// Items
	private Item tomato;
	private Item oilcan;
	private Item boost;
	
	/** ArrayLists */
	// Store the waypoints into a array list
	private ArrayList<Point2D> waypoints    = new ArrayList<Point2D>();
	// Store the karts into a array list
	private ArrayList<Kart>    kartlist     = new ArrayList<Kart>();
	// Store the itmes into a array list
	private ArrayList<Item>    itemlist     = new ArrayList<Item>();
	// Store the hazards created in each item into a hazard list
	private ArrayList<Hazard>  hazardlist   = new ArrayList<Hazard>();
	
	// END VARIABLE DECLARATIONS 
	
    /** Create a new World object. */
    public World() throws SlickException
    {
    	/** Initialise the following in the world */
    	initialiseWayPoints();
    	initialiseItemLocations();
        initialiseBackground();
        initialiseKarts();
        initialiseCamera();
        initialisePanel();
    }
    
    /** Update the game state for a frame.
     * @param rotate_dir The player's direction of rotation
     *      (-1 for anti-clockwise, 1 for clockwise, or 0).
     * @param move_dir The player's movement in the car's axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(double rotate_dir, double move_dir, boolean use_item) throws SlickException
    {       	
    	// Move the karts
    	moveKarts(rotate_dir, move_dir, use_item);
    	
    	// Update the hazards
    	updateHazards();
    	
    	// Update the camera onto the player position
    	updateCamera();
    	
    	// Update the ranking on the panel to show the player's position in the race if they haven't finished
    	updateRanking();
    }

	/** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g) throws SlickException
    {
    	renderBackground();
    	renderItems();
    	renderKarts();
    	renderHazards();
    	renderPositions(g); 	
    }

    // -----------------Initialisation functions----------------------------------------------------------------------------------- 
    
    /** Initialise the waypoints from a file */
    public void initialiseWayPoints()
    {
    	try 
    	{
    		File file = new File(AssetLocations.waypointAssetLocation); // Reading in the file
    		Scanner scanner = new Scanner(file);
    		int count = 1;
    		
    		// While there is a valid input
    		while(scanner.hasNextLine())
    		{
    			String line = scanner.nextLine();
    			String array[] = line.split(","); // Coordinates separated by a delimitter
    			Point2D waypoint = new Point2D ("Waypoint:" + count, Integer.parseInt(array[0].trim()), Integer.parseInt(array[1].trim()));
    			this.waypoints.add(waypoint);
    			count++;
    		}
    		scanner.close();	
    	} catch (FileNotFoundException e) // Checking for exceptions
    	{
    		e.printStackTrace();
    	}
    }
    
    /** Initialise the item locations from a file 
     * @throws SlickException */
    public void initialiseItemLocations() throws SlickException
    {
    	try 
    	{
    		File file = new File(AssetLocations.itemLocaleAssetLocation); // Reading in the file
    		Scanner scanner = new Scanner(file);

    		while(scanner.hasNextLine())
    		{
    			String itemName = scanner.next().toLowerCase();
    			String line = scanner.nextLine();
    			String array[] = line.split(","); // Coordinates separated by a delimitter
    			Point2D itemlocale = new Point2D (itemName, Integer.parseInt(array[0].trim()), Integer.parseInt(array[1].trim()));
    			   												
    			// Store the corresponding items into the appropriate Arraylist for that item
    			if(itemName.equals(AssetLocations.oilcanName))
    			{
    				oilcan = new OilcanItem(AssetLocations.oilcanAssetLocation, AssetLocations.oilcanName, itemlocale);
    				itemlist.add(oilcan);
    			} 
    			
    			else if(itemName.equals(AssetLocations.tomatoName))
    			{
    				tomato = new TomatoItem(AssetLocations.tomatoAssetLocation, AssetLocations.tomatoName, itemlocale);
    				itemlist.add(tomato);
    			}
    			
    			else if(itemName.equals(AssetLocations.boostName))
    			{
    				boost = new BoostItem(AssetLocations.boostAssetLocation, AssetLocations.boostName, itemlocale);
    				itemlist.add(boost);
    			}

    			// Item name in the file not defined
    			else
    				System.err.println("Unrecognised item: " + itemName );				
    		}    		
    		scanner.close();	
    	} catch (FileNotFoundException e){ // Checking for exceptions
    		e.printStackTrace();
    	}	
    }
      
    /** Initialise the background */
    public void initialiseBackground() throws SlickException 
    {
    	background = new TiledMap(AssetLocations.backgroundAssetLocation, Game.ASSETS_PATH);
    }

    /** Initialise the karts to appear in the world */
    public void initialiseKarts() throws SlickException 
    {    	   	
    	// Donkey
    	player = new Player(AssetLocations.donkeyAssetLocation, AssetLocations.donkeyName);
    	kartlist.add(player);
    	// Elephant
    	bot1   = new Elephant(AssetLocations.elephantAssetLocation, AssetLocations.elephantName, waypoints);
    	kartlist.add(bot1);
    	// Dog
    	bot2   = new Dog(AssetLocations.dogAssetLocation, AssetLocations.dogName, waypoints);
    	kartlist.add(bot2);
    	// Octopus
    	bot3   = new Octopus(AssetLocations.octopusAssetLocation, AssetLocations.octopusName, waypoints);
    	kartlist.add(bot3);
    }

    /** Initialise the camera to appear in the world */
    public void initialiseCamera() throws SlickException 
    {
    	// Camera will tag on the player's position
    	camera = new Camera(Player.StartX, Player.StartY, background.getTileWidth(), background.getTileHeight(), 
    			            Game.SCREENWIDTH, Game.SCREENHEIGHT);
    }
        
    /** Initialise the panel to appear on the screen */
    public void initialisePanel() throws SlickException
    {
    	panel = new Panel();
    }
    
    // -----------------Update functions----------------------------------------------------------------------------------- 
    
    /** Update the hazards */
    public void updateHazards() 
    {
    	for(Hazard h: hazardlist)
    		h.update(this);		
	}
    
    /** Update the camera */
	public void updateCamera()
	{
		camera.update((int)player.getPosX(), (int)player.getPosY());	
	}
	
	/** Update the ranking */
	public void updateRanking()
	{
		if(!crossLine)
			ranking = ((Player) player).updateRank(player.getPosY(), bot1.getPosY(), bot2.getPosY(), bot3.getPosY(), ranking);
			
	}
    
    /** Method to move the karts 
     * @throws SlickException */
    public void moveKarts(double rotate_dir, double move_dir, boolean use_item) throws SlickException 
    {    	    	
    	/** Iterating through all the karts */
    	for(Kart k: this.kartlist)
    	{
    		// PlayerKart 		
    		if(k.getClass().equals(player.getClass()))
    			((Player)k).update(this, rotate_dir, move_dir, delta, use_item);
    			
    		// Computer Kart
    		else
    			((AIKart)k).move(this, delta);
    	}  	
	}
 
    // -----------------Utility functions----------------------------------------------------------------------------------- 
      
    /** Check if the given kart is going to colide with any other kart at this position **/
    public boolean didCrash(Kart k)
    {
    	for(Kart other: this.getKartlist())
    	{
    		if((other != k) && (k.collides(other)))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    /** Check if a kart collided with a object */
    public boolean didKartCollide(GameObject object)
    {
    	for(Kart k: kartlist)
    	{
    		if(k.collides(object))
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /** Check if a position is blocked **/
    public boolean blockedAt(float x, float y)
    {
    	return getFriction(x, y) == stoppingFriction;
    }
    	
    /** Function to get the friction for the player position */
    public double getFriction(float PosX, float PosY)
    {
    	int TileX = (int) (PosX / background.getTileWidth());
    	int TileY = (int) (PosY / background.getTileHeight());
    	
    	int groundType = background.getTileId(TileX, TileY, layerIndex);
    	String frictionProperty = background.getTileProperty(groundType, AssetLocations.friction, null);
    	double friction = Double.parseDouble(frictionProperty); 
    	
    	return friction;
    }
   
   // -----------------Render functions----------------------------------------------------------------------------------- 
    
    
    /** Render the background */
    public void renderBackground()
    {
    	background.render(camera.getOffsetX(), camera.getOffsetY(), camera.getSx(), camera.getSy(), 
    			          background.getTileWidth(), background.getTileHeight());
    }
    
    /** Render the in game items */
    public void renderItems()
    {    
    	for(Item items: itemlist)
    	{	
    		items.render(camera);
    	}
    }
   
    /** Render the different types of karts */ 
    public void renderKarts()
    {    	
    	for(Kart karts : kartlist)
    		karts.render(camera);    	
    }
    
    /** Render the hazards if a item is used */
    public void renderHazards()
    {
        	for(Hazard hazards : hazardlist)
        		hazards.render(camera);
    }
    
    /** Render the status positions of the karts */
    public void renderPositions(Graphics g)
    {      	
    	if(this.player.getPosY() > this.finishLine)
    	{
    		this.crossLine = false;
    		panel.render(g, ranking, ((Player) player).getPlayerItem());
    	}
    	else
    	{
    		this.crossLine = true;
    		panel.render(g, ranking, ((Player) player).getPlayerItem());
    		finishPlace = ranking;
    		g.drawString("Game Over !\n You came " + 
					  Panel.ordinal(finishPlace) , WorldCoordinates.GameOver.getPosX(), WorldCoordinates.GameOver.getPosY());    		
    	}
    }        
  //----------------------------------------------------GETTERS AND SETTERS--------------------------------------------------------------//
    
    public ArrayList<Point2D> getWaypoints()
    {
    	return this.waypoints;
    }
          
    public ArrayList<Kart> getKartlist()
    {
    	return this.kartlist;
    }
    
    public ArrayList<Hazard> getHazardlist()
    {
    	return this.hazardlist;
    }
    
    public ArrayList<Item> getItemlist()
    {
    	return this.itemlist;
    }
    
	public boolean getCrossLine()
	{
		return this.crossLine;
	}
	
	public Kart getPlayer()
	{
		return this.player;
	}
	
	public Camera getCamera()
	{
		return this.camera;
	}
}
