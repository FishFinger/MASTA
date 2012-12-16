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
package masta.agents.animals.sheep; 
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.turtlekit2.kernel.environment.Patch;

import masta.agents.animals.Animal;
import masta.agents.animals.AnimalBody;
import masta.agents.things.Hut;

public class Sheep extends Animal
{

	private static final long serialVersionUID = -8609891987504272728L;
	private static Image sheep_img = null;

	public Sheep()
	{
		super();
	}

	public void setup()
	{
		super.setup();
		this.body = new SheepBody(this, 100, 5);
		
		this.getBody().setVision(5f);
		this.fertility_rate = 0.0005f;
		
		if(sheep_img == null)
			try {
				sheep_img = ImageIO.read(new File("/home/clement/m2/masta/Tk2/src/masta/img/sheep.png"));
			} catch (IOException e) {}
		
		this.setImage(sheep_img);
		
		setColor(Color.white);
		this.setDimension(4, 3);
		this.playRole("Sheep");
	}
	
	//******************************************************************

	public void update() 
	{		
		this.lookForGrass();
		if(this.getEnergie() < this.getEnergieMax())
			this.body.eat(this.getPatchAt(0, 0), "grass");
	}
	
	private float getEnergieMax() {
		return 500f;
	}

	public void lookForGrass()
	{
		if(Math.random() > 0.1)
		{
			Patch p = this.getPatchWithMaxOf("grass", (int)this.getVision());
			this.setHeading(this.towards(p.x, p.y));
			this.fd((int)this.getSpeed());
		}
		else
			this.wiggle();
	}

	//******************************************************************
	//	GETTERS
	//******************************************************************

	protected SheepBody getBody(){ return this.body; }
	
	//*************************************************************************
	//	ATTRIBUTS
	//*************************************************************************

	private SheepBody body;

}
