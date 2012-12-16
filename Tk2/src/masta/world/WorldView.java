package masta.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
	
	@Override
	public void paintInfo(Graphics g) {
		if(++step%100 == 0)
			this.redrawAll = true;
		
		super.paintInfo(g);

	}
	
	@Override
	public void paintPatch(Graphics g, Patch p,int x,int y,int cellS){
		
		 if(p.smell("wood") >= 1)
			 if(x%15==0 && y%15==0)
			 {
				 int width = tree_img.getWidth(null)*cellS;
				 int height= tree_img.getHeight(null)*cellS;
				 g.drawImage(tree_img, x, y, width , height , null);
			 }

		
		int red,green,blue;
		red = (int)p.smell("berry");
		green = (int)p.smell("grass")*2;
		blue = (int)p.smell("sea");
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
