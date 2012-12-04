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

import edu.turtlekit2.kernel.agents.Turtle;
/**
 * Turtle example.
 */
@SuppressWarnings("serial")
public class MyTurtle extends Turtle{
	
	/**
	 * Constructor. super("first function executed").
	 */
	public MyTurtle() {
		super("myLazyBehavior");
	}
	
	
	int speed;
	@Override
	public void activate() {
		super.activate();
		speed = getAttributes().getInt("speed");
		playRole("lazy");
	}

	public String myLazyBehavior(){
		//behavior
		if(Math.random() > 0.95){
			randomHeading();
			setColor(new Color((int)(Math.random()*65500)));
			giveUpRole("lazy");
			playRole("speedy");
			return "mySpeedyBehavior";
		}else{
			//emit pheromone
			emit("myPheromone", 30);
			return "myLazyBehavior";
		}
	}
	
	public String mySpeedyBehavior(){
		//get variable from XML parameters
		fd(speed);
		//behavior
		if(Math.random() > 0.9){
			giveUpRole("speedy");
			playRole("lazy");
			return "myLazyBehavior";
		}else
			return "mySpeedyBehavior";
	}
}
