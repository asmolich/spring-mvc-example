<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><tiles:insertAttribute name="title" /></title>

<meta name='yandex-verification' content='5562eb40b68f609b' />
<meta name="google-site-verification" content="trksTccP8L8DXodR5VdsRGqalS471Znls-eXwmJYLm4" />

<link rel="icon" href="<c:url value="/resources/favicon.ico" />" type="image/x-icon">
<link rel="shortcut icon" href="<c:url value="/resources/favicon.ico" />" type="image/x-icon">

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/styles/normalize.css" />" media="all" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/styles/styles.css" />" media="all" />
<!--[if lt IE 9]>
	<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>

<body>

	<!--  header -->
	<tiles:insertAttribute name="header" />

	<!-- main central container -->
	<tiles:insertAttribute name="body" />

	<!-- footer -->
	<tiles:insertAttribute name="footer" />

</body>
</html>