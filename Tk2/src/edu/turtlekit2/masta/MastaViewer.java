package edu.turtlekit2.masta;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.Viewer;

public class MastaViewer extends Viewer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6736823013883615452L;

	public MastaViewer(){
		redrawAll = true;
	}
	
	@Override
	public void paintInfo(Graphics g) {
		redrawAll = true;
		super.paintInfo(g);
	}
	
    public void paintTurtle(Graphics g,Turtle t,int x,int y,int cellSize)
    {
    	/*Polygon p = new Polygon();
    	p.addPoint(0, 0);
    	p.addPoint(0, 4*cellSize);
    	p.addPoint(8*cellSize, 2*cellSize);*/
    	
    	//super.paintTurtle(g, t, x, y, cellSize);
		if(t.isPlayingRole("Sheep")){
			Sheep sheep = (Sheep)t;
			int color = (sheep.getEnergie() < 255)? sheep.getEnergie() : 255;
			g.setColor(new Color(255,color,color));
			
			/*p.translate(x, y);
			g.fillPolygon(p);*/
			g.fillRect(x,y,cellSize*sheep.getSize(),cellSize*sheep.getSize());
		}else{
			g.setColor(new Color(250,0,150));

			g.fillRect(x,y,cellSize*5,cellSize*5);

		}
		
	}

}
