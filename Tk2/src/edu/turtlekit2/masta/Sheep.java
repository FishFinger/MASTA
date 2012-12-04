/*
 * TurtleKit - An Artificial Life Simulation Platform
 * Copyright (C) 2000-2010 Fabien Michel, Gregory Beurier
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
package edu.turtlekit2.masta; 
import java.awt.Color;

import edu.turtlekit2.kernel.agents.Turtle;


/**
 * Adapted from the termites simulation of TurtleKit (v1). 
 * @author G.Beurier
 * @version 1.1 - 4/2010
 * 
 * Termite 

  @author Fabien MICHEL
  @version 1.1 6/12/1999 */

@SuppressWarnings("serial")
public class Sheep extends Animal
{
	
	private int mounthful_quantity;
	private int size;

	/** the first time behavior of a turtle will be "searchForChip"*/
	public Sheep()
	{
		super("automaton");
	}

	/**it is compulsory to redefine this method. Even empty.
	 */
	public void setup()
	{
		setColor(Color.white);
		randomHeading();
		this.energie = 50;
		this.mounthful_quantity = 10;
		this.size = 4;
		this.visionRadius = 5;
		this.playRole("Sheep");
	}
	
	//******************************************************************


	public String automaton() 
	{
		--this.energie;
		if(this.energie < 0)
			this.die();

		Color patch = getPatchColor();
		if (patch.getGreen() > this.mounthful_quantity)
		{
			this.eat();
			this.energie += this.mounthful_quantity;
			
		}
		
		towardsTheGrass();
		fd(1);
		
		if(Math.random() > 0.9995)
		{
			Sheep s = new Sheep();
			this.createTurtle(s);
			this.energie -= s.energie;
			
		}
		
		return ("automaton");
	}
	
	private void eat()
	{
		this.incrementPatchVariableAt("grass", -this.mounthful_quantity, 0, 0);
	}
	
	private void towardsTheGrass()
	{
		int max_grass_quantity = 0;
		int grass_quantity;
		
		
		for(int i=-this.visionRadius;i<=this.visionRadius;i++)
			for(int j=-this.visionRadius;j<=this.visionRadius;j++)
				if ( !(i==0 && j==0) )
				{
					grass_quantity = this.getPatchColorAt(i, j).getGreen();
					
					if (grass_quantity > max_grass_quantity && Math.random() > 0.1) //instead of "instanceof". So prey can be another java class
					{
						this.setHeading(towards(this.x+i, this.y+j));
						max_grass_quantity = grass_quantity;
					}
				}
		
	}
	

	
	//******************************************************************
	//	GETTERS
	//******************************************************************

	public int getSize()
	{
		return this.size;
	}
	
	public int getEnergie()
	{
		return this.energie;
	}
	
	

}
