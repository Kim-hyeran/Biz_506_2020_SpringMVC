<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<title>나의 홈페이지</title>
<style>
	* {
	    box-sizing: border-box;
	    margin: 0;
	    padding: 0;
	    font-family: 'Times New Roman', Times, serif;
	    color: #555;
	    background: linear-gradient(#EDEED2, #629E5E) fixed;
	}
	
	body {
	    width: 50%;
	    height: 100%;
	    margin: 0 auto;
	    display: flex;
	    flex-direction: column;
	    text-align: center;
	}
	
	header {
	    font-style: italic;
	    font-size: 1.3rem;
	    margin-top: 5rem;
	}
	
	nav ul {
	    list-style: none;
	    display: flex;
	}
	
	nav ul li {
	    margin: 0 10px;
	    cursor: pointer;
	}
	
	nav ul li:hover {
	    text-decoration: line-through;
	}
	
	section#main-content {
		font-family: 'Roboto', sans-serif;
	    margin: 10px 0;
	    padding: 5px;
	    flex: 1;
	    overflow: auto;
	    background-color: white;
	}
	
	footer {
	    text-align: right;
	    font-size: 0.7rem;
	    padding: 2rem;
	}
</style> 

</head>
<body>
	<tiles:insertAttribute name="header"/>
	<tiles:insertAttribute name="menu" />
	<section id="main-content">
		<tiles:insertAttribute name="content" />
	</section>
	<tiles:insertAttribute name="footer" />
</body>
</html>