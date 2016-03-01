public class Point2D extends GameObject
{
	private String name;
	private int posX;
	private int posY;
	
	public Point2D(String name, float posX, float posY)
	{
		this.name = name;
		this.posX = (int) posX;
		this.posY = (int) posY;
	}

	// Getters
	public float getPosX()
	{
		return (int) posX;
	}

	public float getPosY()
	{
		return (int) posY;
	}
	
	public String getName()
	{
		return name;
	}
	
	public float distanceTo(Point2D point)
	{
		return (float) Math.sqrt(Math.pow((point.posX - this.posX), 2) + Math.pow((point.posY - this.posY), 2));			
	}
}
