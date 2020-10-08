package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.nait.dmit.domain.Rectangle;

/**
 * Servlet implementation class RectangleServiet
 */
@WebServlet("/servlet/form/RectangleServlet")
public class RectangleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RectangleServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve the form field values for length and width
		String lengthString = request.getParameter("length");
		String widthString = request.getParameter("width");
		// Convert the length and width from String to double
		double length = Double.parseDouble(lengthString);
		double width = Double.parseDouble(widthString);
		// Construct a Rectangle object
		Rectangle rectangle1 = new Rectangle();
		// Set the length and width of the rectangle
		rectangle1.setLength(length);
		rectangle1.setWidth(width);
		// Output the area and perimeter of the rectangle directly to the HTTP response
		PrintWriter out = response.getWriter();
		out.println("<p>The area is <strong>" + rectangle1.area() + "</strong> for a length of " + length
				+ " and width of " + width + "</p>");
		out.println("<p>The perimeter is <strong>" + rectangle1.perimeter() + "</strong> for a length of " + length
				+ " and width of " + width + "</p>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
