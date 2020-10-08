package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.nait.dmit.domain.Circle;

/**
 * Servlet implementation class CircleServlet
 */
@WebServlet("/servlet/form/CircleServlet")
public class CircleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CircleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve the radius value from the request
		String radiusString = request.getParameter("radius");
		// Convert the radius from a String to a double
		double radius = Double.parseDouble(radiusString);
		// Construct with Circle with the radius
		Circle circle1 = new Circle(radius);
		// Output the area and circumference of the circle directly to the HTTP response
		PrintWriter out = response.getWriter();
		out.println("<p>The area is <strong>" + circle1.area() + "</strong> for a radius of " + radius + "</p>");
		out.println("<p>The circumference is <strong>" + circle1.circumference() + "</strong> for a radius of " + radius + "</p>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
