<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<spring:eval expression="@propertyConfigurer.getProperty('cdn.url')" var="cdnUrl" />
<jsp:useBean id="date" class="java.util.Date" />
	<div id="footer">
        <div class="container">
            <p id="copyright">
            	<spring:message code="voj.include.footer.copyright" text="Copyright" />&copy; 2005-<%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %> <a href="http://zjhzxhz.com/" target="_blank">Trunk Shell</a>. 
            	<spring:message code="voj.include.footer.all-rights-reserved" text="All rights reserved." />
            </p>
        </div> <!-- .container -->
    </div> <!-- #footer -->