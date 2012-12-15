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

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.TurtleProbe;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.tools.chart.ChartWindow;
import edu.turtlekit2.ui.SimulationBoard;
import edu.turtlekit2.ui.utils.GUIMessage;


public class EnergieChart extends Viewer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 430556967523587324L;
	
	private String role; 
	private String chart_name;
	
	private static final int AVG = 0;
	private static final int MIN = 1;
	private static final int MAX = 2;
	double[] tab = new double[3];

	
	TurtleProbe allTurtles;
	int step = 0;
	ChartWindow chart;
	
	@Override
	public void setup() {
		this.role = getAttrib().getString("role"); 
		
		allTurtles = new TurtleProbe(getSimulationGroup(),this.role);
		addProbe(allTurtles);
		
		this.chart_name = this.role+" energie";
		
		chart = new ChartWindow();
		chart.addChart(chart_name, "Step", "Energie");
		
		chart.addSerie(chart_name, "AVG");
		chart.addSerie(chart_name, "MIN");
		chart.addSerie(chart_name, "MAX");
		
		this.sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager",
				new GUIMessage<JComponent>(chart, SimulationBoard.VIEWER_ZONE, chart_name));
	}

	public void watch(){
		step++;
		
		tab[AVG] = 0;
		tab[MIN] = Integer.MAX_VALUE;
		tab[MAX] = 0;
		
		int nb_turtles = allTurtles.getCurrentAgentsList().size();
		
		for (final Turtle t : allTurtles.getCurrentAgentsList()) {
			Animal a  = (Animal) t;
			tab[AVG] += (double)a.getEnergie() / (double)nb_turtles;
			
			if (a.getEnergie() > tab[MAX])
				tab[MAX] = a.getEnergie();
			
			if (a.getEnergie() < tab[MIN])
				tab[MIN] = a.getEnergie();
		}
		
		chart.update(chart_name,step,tab);
	}

}
