<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Rectangle Calculator</title>
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
	
	<form method="post">
		<label for="length">Length</label>
		<input type="text" id="length" name="length" value="${rectangleBean.length}"/>	

		<label for="width">Width</label>
		<input type="text" id="width" name="width" value="${rectangleBean.width}"/>	
		
		<input type="submit" value="Calculate" />
	</form>
	
</body>
</html>