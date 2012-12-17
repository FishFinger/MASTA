package masta.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import masta.agents.AAgent;
import masta.agents.animals.sheep.Sheep;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.kernel.environment.Patch;

public class WorldView extends Viewer
{
	
	private static final long serialVersionUID = -6736823013883615452L;
	private static int step = -1;
	private static Image tree_img = null;

	public WorldView()
	{
		try {
			tree_img = ImageIO.read(
					new File("/home/clement/m2/masta/Tk2/src/masta/img/tree.png")
				);
		} catch (IOException e) {}
	}
	
	
	/*private Image world;
	public void paintInfo(Graphics g){
		
		if(++step%100 == 0)
			this.redrawAll = true;
		
		if (world == null || this.redrawAll)
		{
			Image img = new BufferedImage(envWidth*cellSize,envHeight*cellSize,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)img.getGraphics();
			
			this.createWorld(g2d);
			g.drawImage(this.world, xDecay, yDecay, envWidth*cellSize, envHeight*cellSize, null);
		}
		
		Turtle[] turtles = allTurtles.getTurtles();		
		for(int i=turtles.length-1;i>=0;i--)
			if (turtles[i] != null && ! turtles[i].hidden)
				paintTurtle(g,turtles[i],(turtles[i].xcor()*cellSize)+xDecay,((envHeight-turtles[i].ycor()-1)*cellSize)+yDecay,cellSize);
		
		
		this.redrawAll = false;
	}
	
	protected void createWorld(Graphics g)
	{
		for (int i=envWidth-1; i >=0 ; i--)
			for (int j=envHeight-1; j >=0; j--)
				paintPatch(g, patchGrid[i][j],(i*cellSize),((envHeight-j-1)*cellSize),cellSize);
	
		Turtle[] turtles = allTurtles.getTurtles();		
		for(int i=turtles.length-1;i>=0;i--)
			if (turtles[i] != null && turtles[i].hidden)
				paintTurtle(g,turtles[i],(turtles[i].xcor()*cellSize)+xDecay,((envHeight-turtles[i].ycor()-1)*cellSize)+yDecay,cellSize);
		
	}

	*/
	@Override
	public void paintPatch(Graphics g, Patch p,int x,int y,int cellS){
		
		if(p.smell("wood") >= 10)
		{
			 int width = tree_img.getWidth(null)*cellS / 2;
			 int height= tree_img.getHeight(null)*cellS / 2;
			 g.drawImage(tree_img, x, y, width , height , null);
		}

		
		
		int red, green, blue;
		red = (int)p.smell("berry");
		green = (int)p.smell("grass")*2;
		blue = (int)p.smell("sea");
		
		green = (green > 255)? 255 : green;
		blue = (blue > 255)? 255 : blue;
		red = (red > 255)? 255 : red;

		green = (green < 0)? 0 : green;
		blue = (blue < 0)? 0 : blue;
		red = (red < 0)? 0 : red;
		
		if(blue != 0)
			blue = 255 - blue;
		
		g.setColor(new Color(red, green , blue));
		
		g.fillRect(x, y, cellS, cellS);
		
	}
	
	@Override
    public void paintTurtle(Graphics g,Turtle t,int x,int y,int cell_size)
    {

		if(t.isPlayingRole("AAgent"))
		{
			AAgent agent = (AAgent)t;
			agent.paint(g, x, y, cell_size);
		}
		
	}

}
