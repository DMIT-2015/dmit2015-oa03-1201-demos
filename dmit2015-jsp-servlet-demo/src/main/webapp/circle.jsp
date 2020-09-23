<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Circle Calculator</title>
</head>
<body>
	<h1>Circle Calculator</h1>
	
	<jsp:useBean id="circleBean" class="ca.nait.dmit.domain.Circle">
		<jsp:setProperty name="circleBean" property="radius" />
	</jsp:useBean>
	<c:if test="${pageContext.request.method == 'POST'}">
		<p>
			For a circle with a radius of ${circleBean.radius},
			the area is <strong>${circleBean.area()}</strong>,
			the circumference is <strong>${circleBean.circumference()}</strong>,
			the diameter is <strong>${circleBean.diameter()}</strong>.
		</p>
	</c:if>
	
	<form method="post">
		<label for="radius">Radius</label>
		<input type="text" id="radius" name="radius" value="${circleBean.radius}"/>	
		<input type="submit" value="Calculate" />
	</form>
	
</body>
</html>