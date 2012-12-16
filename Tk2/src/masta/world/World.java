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
package masta.world; 

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.vecmath.Point3i;

import masta.agents.animals.humans.Human;
import masta.agents.things.Hut;

import edu.turtlekit2.kernel.agents.Observer;
import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.environment.Patch;
import edu.turtlekit2.kernel.environment.PatchVariable;
import edu.turtlekit2.kernel.environment.TurtleEnvironment;


@SuppressWarnings("serial")
public class World extends TurtleEnvironment
{
	private float sea_level = 0.15f;
	private float wood_level = 0.6f;
	
	private boolean fast_starting = true;

	private float berry_percent = 0.3f; 
	private float berry_max = 100;
	private float wood_max = 500;
	
	private float point_percent = 0.0001f;//0.0003f
	private int nb_point_for_interpolation = 3;
	
	int step = -1;
	
	private static World instance;

	public World() 
	{
		World.instance = this;
	}
	
	public static World getInstance()
	{
		return World.instance;
	}
	
	public void setup()
	{	
		
		int nb_patch = this.getWidth()*this.getHeight();
		int percent_loaded = 0;
		int old_percent_loaded = 0;

		this.println("Initialisation");
		
		LinkedList<Point3i> list = new LinkedList<Point3i>();
		for(int i=0; i<this.getWidth(); ++i)
			for(int j=0; j<this.getHeight(); ++j)
			{
				if (!fast_starting)
				{
					if (i == 0 || i == (this.getWidth() - 1))
					{
						if (j % 30 == 0)
							list.add(new Point3i(i,j,0));
					}
					else if (j == 0 || j == (this.getHeight() - 1))
					{
						if (i % 30 == 0)
							list.add(new Point3i(i,j,0));
					}
				}
				
				if(Math.random() < point_percent)
					list.add(new Point3i(i,j, (int)(Math.random()*1000)));
			}
		
		
		
		for(int i=0;i<getWidth();i++)
			for(int j=0;j<getHeight();j++)
			{
				this.computeVar(this.grid[i][j], (float)interpolation(list, i, j, nb_point_for_interpolation)/1000.f);

				//mise à jour pourcentage chargement
				percent_loaded = ((getHeight()+1)*i + j)/(nb_patch/100);
				if (percent_loaded > old_percent_loaded)
				{	
					old_percent_loaded = percent_loaded;
					this.println( percent_loaded + "%");
				}
			}
		
		this.humansRepartition();

	}			
	
	private void humansRepartition()
	{
		Iterator<Hut> it = Hut.allHuts.iterator();
		Hut hut = null;
		for(Human h: Human.allHumans)
		{
			if(!it.hasNext())
				it = Hut.allHuts.iterator();
				
			hut = it.next();
				
			
			hut.addAnInhabitant(h);
			h.setXY(hut.xcor(), hut.ycor());
		}
	}
	
	private void computeVar(Patch p, float level)
	{
		if (level <= this.sea_level)
			p.setPatchVariable("sea", 255.0 - level*(254.0 / this.sea_level));
		else
			p.setPatchVariable("grass", 100.0 * (0.10f*Math.random() + (1.f - this.sea_level) - (level-this.sea_level)));
	
		  if (level > this.wood_level)
		  {
			  p.setPatchVariable("wood", 1.0);
			  if(Math.random() < this.berry_percent)
				  p.setPatchVariable("berry", this.berry_max * Math.random());
		  }

	}
	
	//@todo need use of QuadTree or other similar structure
	private int interpolation(LinkedList<Point3i> list, int x, int y, int k)
	{
		
		int value;
		
		//neighbors distance and value
		int n_val[] = new int[k];
		int n_dist[] = new int[k];
		
		int dist;
		for(int i=0; i<list.size(); ++i)
		{
			Point3i p = list.get(i);
			
			dist = Math.abs(p.x - x) + Math.abs(p.y - y);

			for(int j=0; j<k; ++j)
			{
				if(dist < n_dist[j] || n_dist[j] == 0)
				{
					if(j>0)
					{
						n_dist[j-1] = n_dist[j];
						n_val[j-1] = n_val[j];
					}
					n_dist[j] = dist;
					n_val[j] = p.z;
				}
				else
				{
					break;
				}
			}
			
				
		}
				
		value = 0;
		for(int i=0; i<k; ++i)
		{
			dist = 0;
			for(int j=0; j<k; ++j)
			{
				if(i!=j)
					dist += n_dist[j];
			}
			value += dist*n_val[i];
		}
		
		//calcul distance total
		dist = 0;
		for(int i=0; i<k; ++i)
		{
			dist += n_dist[i];
		}
		
		value /= (k-1)*dist;
		
		return value;
	}
	
				
	
	

}
