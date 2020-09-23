<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Rectangle Calculator</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">

</head>
<body>
	<h1>Rectangle Calculator</h1>
	
	<jsp:useBean id="rectangleBean" class="ca.nait.dmit.domain.Rectangle">
		<jsp:setProperty name="rectangleBean" property="*" />
	</jsp:useBean>
	<c:if test="${pageContext.request.method == 'POST'}">
		<p>
			For a rectangle with a length of ${rectangleBean.length} and width of ${rectangleBean.width},
			the area is <strong>${rectangleBean.area()}</strong>,
			the perimeter is <strong>${rectangleBean.perimeter()}</strong>,
			the diagonal is <strong>${rectangleBean.diagonal()}</strong>.
		</p>
				
	</c:if>
	
	<table class="table">
		<c:forEach var="currentRectangle" items="${rectangleBean.nextTenRectangles()}">
			<tr>
				<td>${currentRectangle.length}</td>
				<td>${currentRectangle.width}</td>
				<td>${currentRectangle.area()}</td>
				<td>${currentRectangle.perimeter()}</td>				
			</tr>		
		</c:forEach>
	</table>
	
	<form method="post">
		<label for="length">Length</label>
		<input type="text" id="length" name="length" value="${rectangleBean.length}"/>	

		<label for="width">Width</label>
		<input type="text" id="width" name="width" value="${rectangleBean.width}"/>	
		
		<input type="submit" value="Calculate" />
	</form>
	
	
	 <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</body>
</html>