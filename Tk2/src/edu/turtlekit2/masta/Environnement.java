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
import java.util.LinkedList;

import javax.vecmath.Point3i;

import edu.turtlekit2.kernel.agents.Observer;
import edu.turtlekit2.kernel.environment.Patch;

/** 
 * Adapted from the termites simulation of TurtleKit (v1). 
 * @author G.Beurier
 * @version 1.1 - 4/2010
 * 
 * 
 * The only goal of this Observer is to setup the patches for 
	the termite simulation, having its variables: patchGrid, EnvWidth, EnvHeight

  @author Fabien MICHEL
  @version 1.0 20/3/2000 */

@SuppressWarnings("serial")
public class Environnement extends Observer
{
	private float sea_level = 0.33f;

	private float percent_point;
	private int nb_point_for_interpolation;
	int step = -1;
	

	public Environnement()
	{

	}

	public void setup()
	{
		double tmp_map[][] = new double[envWidth][envHeight];
		
		percent_point = this.getAttrib().getFloat(("percent_point"));
		nb_point_for_interpolation = this.getAttrib().getInt("nb_point_for_interpolation");
		System.out.println(percent_point+"--"+nb_point_for_interpolation);
		
		int nb_patch = (envWidth+1)*(envHeight+1);
		int percent_loaded = 0;
		int old_percent_loaded = 0;

		
		System.out.println("Initialisation");
		
		LinkedList<Point3i> list = new LinkedList<Point3i>();
		for(int i=0;i<envWidth;i++)
			for(int j=0;j<envHeight;j++)
				if (Math.random() < percent_point)
					list.add(new Point3i(i,j, (int)(Math.random()*1000)));
		
		
		for(int i=0;i<envWidth;i++)
			for(int j=0;j<envHeight;j++)
			{
				this.computeVar(this.patchGrid[i][j], (float)interpolation(list, i, j, nb_point_for_interpolation)/1000.f);

				//mise à jour pourcentage chargement
				percent_loaded = ((envHeight+1)*i + j)/(nb_patch/100);
				if (percent_loaded > old_percent_loaded)
				{	
					old_percent_loaded = percent_loaded;
					System.out.println( percent_loaded + "%");

				}
			}

	}			
	
	private void computeVar(Patch p, float level)
	{
		if (level < this.sea_level)
			p.setPatchVariable("sea", level*(255.0 / this.sea_level));
		else
			p.setPatchVariable("grass", 255.0 * ((1.f - this.sea_level) - (level-this.sea_level)));
		
	}
	
	private static void addNeighbors(int dist, int val, int n_dist[], int n_val[], int k)
	{
		
	}
	
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
				
	
	public void watch()
	{
		int red,green,blue;
		
			
			for(int i=0; i<envWidth; i++)
				for(int j=0; j<envHeight; j++)
				{
					red = 0;
					green = (int)patchGrid[i][j].smell("grass");
					blue = (int)patchGrid[i][j].smell("sea");
				
					patchGrid[i][j].setColor(new Color(red, green , blue));
					
				}
			
		
	}
	

}
