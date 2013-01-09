package masta.agents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.vecmath.Point2i;

import masta.world.World;

import edu.turtlekit2.kernel.agents.Turtle;

public abstract class AAgent extends Turtle{
	
	private static final long serialVersionUID = -5473677723398639840L;
	private Image img;
	private Point2i dim;
	private Color color;
		
	public AAgent()
	{
		super("_update");
	}
	
	public void setup()
	{
		super.setup();
		this.setDebug(true);
		this.playRole("AAgent");
	}
	
	public String _update()
	{
		this.beforeUpdateTest();
		this.update();
		this.afterUpdateTest();
		
		return "_update";
	}
	
	protected abstract void beforeUpdateTest();
	protected abstract void update();
	protected abstract void afterUpdateTest();
	
	public void setImage(Image img)
	{
		this.img = img;
	}
	
	public Image getImage()
	{
		return this.img;
	}
	
	public void setDimension(Point2i dim)
	{
		this.dim = dim;
	}
	
	public void setDimension(int width, int height)
	{
		this.dim = new Point2i(width,height);
	}
	
	public Point2i getDimension()
	{
		return this.dim;
	}
	
	public int getWidth()
	{
		return dim.getX();
	}
	
	public int getHeight()
	{
		return dim.getY();
	}

	public Color getColor()
	{
		return this.color;
	}
	
	public void setColor(Color c)
	{
		this.color = c;
	}
	
	public abstract boolean isMovable();
	
	public void paint(Graphics g, int x, int y, int cell_size)
	{
		Image img = this.getImage();
		if(img != null)
		{
			int width = img.getWidth(null)*cell_size / 4;
			int height = img.getHeight(null)*cell_size / 4;
			int posX = x - (width - cell_size)/2;
			int posY = y - (height - cell_size)/2;
			g.drawImage(img, posX, posY, width, height, null);
		}
		else
		{
			g.setColor(this.getColor());
			g.fillRect(x,y,cell_size*this.getWidth() / 4,cell_size*this.getHeight() / 4);
		}
	}
	
}
