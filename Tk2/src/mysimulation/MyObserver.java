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

import javax.swing.JComponent;

import edu.turtlekit2.Tk2Launcher;
import edu.turtlekit2.kernel.agents.Turtle;
import edu.turtlekit2.kernel.agents.TurtleProbe;
import edu.turtlekit2.kernel.agents.Viewer;
import edu.turtlekit2.tools.chart.ChartWindow;
import edu.turtlekit2.ui.SimulationBoard;
import edu.turtlekit2.ui.utils.GUIMessage;

/**
 * Observer example.
 */
@SuppressWarnings("serial")
public class MyObserver extends Viewer {
	TurtleProbe allTurtles;
	double[] t = new double[2];
	int step = 0;
	ChartWindow chart;
	
	@Override
	public void setup() {
		//Add probe
		allTurtles = new TurtleProbe(getSimulationGroup(),"turtle");
		addProbe(allTurtles);
		//Create chart Population then add two series
		chart = new ChartWindow();
		chart.addChart("Population", "Step", "Population");
		chart.addSerie("Population", "Right");
		chart.addSerie("Population", "Left");
		//Add the chart to the GUI
		this.sendMessage(Tk2Launcher.COMMUNITY, getSimulationGroup(), "UIManager",
				new GUIMessage<JComponent>(chart, SimulationBoard.VIEWER_ZONE, "MyChart"));
	}

	
	public void watch(){
		step++;
		int cpt = 0;
		//Count turtles (x > 50)
		for (final Turtle t : allTurtles.getCurrentAgentsList()) {
			if (t.xcor()>=  50)
				cpt++;
		}
		//Create the array to update the series (2 series, array[2])
		t[0] = cpt;
		t[1] = allTurtles.getTurtles().length - cpt;
		//Add data to the series
		chart.update("Population",step,t);
	}
}
