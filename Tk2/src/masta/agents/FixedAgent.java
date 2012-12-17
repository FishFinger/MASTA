package masta.agents;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class FixedAgent extends AAgent {
	
	public boolean isMovable()
	{
		return false;
	}

}
