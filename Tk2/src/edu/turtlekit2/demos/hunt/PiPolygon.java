package edu.turtlekit2.demos.hunt;

import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/*
 * source : http://www.developpez.net/forums/d125607/java/interfaces-graphiques-java/graphisme/2d/java2d-translation-rotation-changement-taille/
 */

public class PiPolygon extends Polygon
	{
	 
	 
		public void scale (double sx, double sy)
		{
			AffineTransform rotation = AffineTransform.getScaleInstance (sx, sy);

			for (int i=0; i < this.npoints; ++i)
			{
				rotation.transform (, point);
			}
			reset ();
			for (Point2D.Double point : points)
			{
				addPoint ((int) point.getX(), (int) point.getY());
			}
	 
			double previousX = centerX;
			double previousY = centerY;
	 
			updateCenter ();
			double currentX = centerX;
			double currentY = centerY;
			double deltaX = previousX - currentX;
			double deltaY = previousY - currentY;
	 
			translate ((int) deltaX, (int) deltaY);
			centerX = previousX;
			centerY = previousY;
		}
	 
		public void rotate (double degree)
		{
			AffineTransform rotation = AffineTransform.getRotateInstance (Math.toRadians (degree), centerX, centerY);
			for (Point2D.Double point : points)
			{
				rotation.transform (point, point);
			}
			reset ();
			for (Point2D.Double point : points)
			{
				addPoint ((int) point.getX(), (int) point.getY());
			}
		}
	 
		public void translate (int dx, int dy)
		{
			double x = 0;
			double y = 0;
			for (Point2D.Double point : points)
			{
				x = point.getX() + dx;
				y = point.getY() + dy;
				point.setLocation (x, y);
			}
			reset ();
			for (Point2D.Double point : points)
			{
				addPoint ((int) point.getX(), (int) point.getY());
			}
			updateCenter ();
		}
	 
		public void updateCenter ()
		{		
			process :
			{
				if (points.size () <= 0)
				{
					centerX = 0;
					centerY = 0;
					break process;
				}
	 
				double xSum = 0;
				double ySum = 0;
				for (Point2D.Double point : points)
				{
					xSum += point.getX();
					ySum += point.getY();
				}
				centerX = xSum / points.size ();
				centerY = ySum / points.size ();
			}
		}
	 
		public int getCenterX ()
		{
			return (int) centerX;
		}
	 
		public int getCenterY ()
		{
			return (int) centerY;
		}
	 
		private static final long serialVersionUID = 0;
	}
}
