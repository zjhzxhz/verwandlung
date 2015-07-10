<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')" var="cdnUrl" />
<jsp:useBean id="date" class="java.util.Date" />
    <div id="footer">
        <div class="container">
            <ul id="footer-nav" class="inline">
                <li><a href="<c:url value="/terms" />"><spring:message code="voj.include.footer.terms" text="Terms of Use" /></a></li>
                <li><a href="<c:url value="/privacy" />"><spring:message code="voj.include.footer.privacy" text="Privacy & Cookies" /></a></li>
                <li><a href="<c:url value="/judgers" />"><spring:message code="voj.include.footer.judgers" text="Judgers" /></a></li>
                <li><a href="<c:url value="/help" />"><spring:message code="voj.include.footer.help" text="Help" /></a></li>
                <li><a href="<c:url value="/about" />"><spring:message code="voj.include.footer.about-us" text="About Us" /></a></li>
                <li>
                    <a href="<c:url value="/worldwide?forward=" />${requestScope['javax.servlet.forward.request_uri']}">
                        <i class="fa fa-globe"></i>
                        <span id="current-language">${language}</span>
                    </a>
                </li>
            </ul>
            <p id="copyright">
                <spring:message code="voj.include.footer.copyright" text="Copyright" />&copy; <%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %> ${copyright}. 
                <spring:message code="voj.include.footer.all-rights-reserved" text="All rights reserved." />
                ${icpNumber}
            </p>
            <p>
                <button class="btn-github" onclick="window.location.href='https://github.com/zjhzxhz/voj/'">
                    <i class="fa fa-github"></i> Star
                </button>
            </p>
        </div> <!-- .container -->
    </div> <!-- #footer -->