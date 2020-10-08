package ca.nait.dmit.demo.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.nait.dmit.domain.Circle;

/**
 * Servlet implementation class CirclePassDataServlet
 */
@WebServlet("/servlet/form/CirclePassDataServlet")
public class CirclePassDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CirclePassDataServlet() {
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
		// Store the circle1 object in request scope
		request.setAttribute("circleBean", circle1);
		// Use the dispatcher and forward to circleResult.jsp
		request.getRequestDispatcher("/demo/servlet/circlePassDataResult.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
