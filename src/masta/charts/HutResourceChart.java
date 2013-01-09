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
package masta.charts;


import javax.swing.JComponent;

import masta.agents.animals.Animal;
import masta.agents.animals.sheep.Sheep;
import masta.agents.things.Hut;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.TurtleProbe;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.tools.chart.ChartWindow;
import edu.turtlekit2.ui.SimulationBoard;
import edu.turtlekit2.ui.utils.GUIMessage;


public class HutResourceChart extends Viewer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 430556967523587324L;
	
	private String role; 
	private String chart_name;
	
	private static final int STATIC = 0;
	private static final int MIN = 1;
	private static final int AFFINITY = 2;
	double[] tab = new double[3];

	
	TurtleProbe allTurtles;
	int step = 0;
	ChartWindow chart;
	
	@Override
	public void setup() {
		
		
		this.chart_name = "Hut Resources";
		
		chart = new ChartWindow();
		chart.addChart(chart_name, "Step", "Hut Resources");
		
		chart.addSerie(chart_name, "STATIC");
		chart.addSerie(chart_name, "MIN");
		chart.addSerie(chart_name, "AFFINITY");
				
		this.sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager",
				new GUIMessage<JComponent>(chart, SimulationBoard.VIEWER_ZONE, chart_name));
	}

	public void watch(){
		step++;
		
		tab[STATIC] = 0;
		tab[MIN] = 0;
		tab[AFFINITY] = 0;
				
		for (Hut h: Hut.allHuts) 
		{
			switch(h.alloc_mod)
			{
			case STATIC:
				tab[STATIC] += (int)h.getMinStock();
				break;
			case MIN_BEFORE:
				tab[MIN] += (int)h.getMinStock();
				break;
			case AFFINITY:
				tab[AFFINITY] += (int)h.getMinStock();
				break;
			}
			
		}
		
		chart.update(chart_name,step,tab);
	}

}
