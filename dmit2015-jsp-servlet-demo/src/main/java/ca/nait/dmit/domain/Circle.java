package ca.nait.dmit.domain;


/**
 * The Circle class models a circle shape.
 * 
 * @author Sam Wu
 * @version 2018.01.18
 */
public class Circle {

	/** The radius of the circle */
	private double radius;

	/** Construct a circle with a radius 0 */
	public Circle() {
		radius = 1;
	}

	/** Construct a circle with a specified radius */
	public Circle(double newRadius) {
		radius = newRadius;
	}

	/**
	 * Return the radius
	 * 
	 * @return The value in the radius field
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Set the radius
	 * 
	 * @param radius The value to store in the radius field
	 */
	public void setRadius(double newRadius) {
		this.radius = newRadius;
	}

	/**
	 * Returns the area of the circle using the formula: area = PI * radius * radius
	 * 
	 * @return The calculated area of the circle
	 */
	public double area() {
		return Math.PI * Math.pow(radius, 2);
	}

	/**
	 * Returns the diameter of the circle using the formula: diameter = radius * 2
	 * 
	 * @return The calculated diameter of the circle
	 */
	public double diameter() {
		return radius * 2;
	}

	/**
	 * Returns the circumference of the circle using the formula: circumference = 2
	 * * PI * radius
	 * 
	 * @return The calculated circumference of the circle
	 */
	public double circumference() {
		return 2 * Math.PI * radius;
	}
}