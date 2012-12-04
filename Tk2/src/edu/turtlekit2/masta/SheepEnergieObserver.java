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

import javax.swing.JComponent;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.demos.gas.Gas;
import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.TurtleProbe;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.tools.chart.ChartWindow;
import edu.turtlekit2.ui.SimulationBoard;
import edu.turtlekit2.ui.utils.GUIMessage;


@SuppressWarnings("serial")
public class SheepEnergieObserver extends Viewer
{
	private static final int AVG = 0;
	private static final int MAX = 1;
	double[] tab = new double[2];

	
	TurtleProbe allTurtles;
	int step = 0;
	ChartWindow chart;
	
	@Override
	public void setup() {
		allTurtles = new TurtleProbe(getSimulationGroup(),"Sheep");
		addProbe(allTurtles);
		chart = new ChartWindow();
		chart.addChart("Sheep Energie", "Step", "Energie");
		chart.addSerie("Sheep Energie", "AVG");
		chart.addSerie("Sheep Energie", "MAX");
		this.sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager",
				new GUIMessage<JComponent>(chart, SimulationBoard.VIEWER_ZONE, "Sheep Energie Chart"));
	}

	public void watch(){
		step++;
		
		tab[AVG] = 0;
		tab[MAX] = 0;
		
		int nb_turtles = allTurtles.getCurrentAgentsList().size();
		
		for (final Turtle t : allTurtles.getCurrentAgentsList()) {
			Sheep sheep  = (Sheep) t;
			tab[AVG] += (double)sheep.getEnergie() / (double)nb_turtles;
			
			if (sheep.getEnergie() > tab[MAX])
				tab[MAX] = sheep.getEnergie();
		}
		
		chart.update("Sheep Energie",step,tab);
	}

}
