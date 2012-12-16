package masta;

import java.awt.Color;

public enum Resource
{
	MEAT, FRUIT, WOOD;	
	
	public Job getJob()
	{
		switch(this)
		{
		case MEAT:
			return Job.HUNTER;
		case FRUIT:
			return Job.GATHERER;
		case WOOD:
			return Job.WOODCUTTER;
		default:
			return null;
		}
	}
	
	public Color getColor()
	{
		return this.getJob().getColor();
	}
}
