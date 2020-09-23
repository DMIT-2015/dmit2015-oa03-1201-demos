package ca.nait.dmit.domain;

import lombok.Data;

/**
 * The Rectangle class models a rectangle shape.
 * 
 * @author Sam Wu
 * @version 2018.01.18
 */
@Data
public class Rectangle {

	/** The length of the rectangle */
	private double length;
	/** The width of the rectangle */
	private double width;

	/**
	 * Construct a rectangle with a length 0 and a width 0
	 */
	public Rectangle() {
		length = 0;
		width = 0;
	}

	public Rectangle(double length, double width) {
		this.length = length;
		this.width = width;
	}

	/**
	 * Return the calculated perimeter of the rectangle using the formula: perimeter
	 * = 2 (length + width)
	 * 
	 * @return The perimeter of the rectangle
	 */
	public double perimeter() {
		return 2 * (length + width);
	}

	/**
	 * Return the calculated area of the rectangle using the formula: area = width *
	 * length
	 * 
	 * @return The area of the rectangle
	 */
	public double area() {
		return width * length;
	}

	/**
	 * Return the calculated diagonal length of the rectangle using the formula:
	 * diagonal = sqrt( width ^ 2 + length ^ 2)
	 * 
	 * @return The diagonal of the rectangle
	 */
	public double diagonal() {
		return Math.sqrt(Math.pow(width, 2) + Math.pow(length, 2));
	}

	

}