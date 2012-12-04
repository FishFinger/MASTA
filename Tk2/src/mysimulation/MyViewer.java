/*
 * BlackHole.java -TurtleKit - A 'star logo' in MadKit
 * Copyright (C) 2000-2010 Fabien Michel - Gregory Beurier
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package mysimulation;

import java.awt.Color;
import java.awt.Graphics;

import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.kernel.environment.Patch;

/**
 * Viewer example.
 */
public class MyViewer extends Viewer {
	private static final long serialVersionUID = 1L;

	/**Standard graphical representation of turtle. Can be disabled/enabled in real time. 
	 * You can override this method in order to give a special graphic representation of your turtles.
	 * 
	 * Here, lazy turtles are drawn with ovals, speedy with rectangles.
	 * */
	public void paintTurtle(Graphics g, Turtle t,int x,int y,int cellS){	
		if(t.isPlayingRole("lazy")){
			g.setColor(t.color);
			g.fillOval(x,y,cellS,cellS);
//			g.setColor(Color.GREEN);
//			g.fillOval(x, y, 6, 6);
			
		}
		else
		{
			g.setColor(t.color);
			g.fillRect(x,y,cellS,cellS);
		}
		
	}

	/**The Paintpatch method has been overriden in order to achieve specific representation of flavors. 
	 * Each flavor can be represented as a function of the Red/Blue/Green/Black canals.
	 * 
	 * 
	 * Here, if pheromone are low, it is drawn in black, if high it is drawn in white, else it is drawn in violet
	 * depending on the quantity.
	 * */
	public void paintPatch(Graphics g, Patch p,int x,int y,int CellSize){
		double pheromoneQuantity = p.smell("myPheromone");
		Color patchColor;
		if(pheromoneQuantity < 5)
			patchColor = Color.black;
		else if(pheromoneQuantity > 10)
			patchColor = Color.white;
		else{
			float colorVariable = (float)(pheromoneQuantity /10);
			patchColor = new Color(0.3f, colorVariable, colorVariable);
		}
		g.setColor(patchColor);
		g.fillRect(x,y,CellSize,CellSize);
	}

}
