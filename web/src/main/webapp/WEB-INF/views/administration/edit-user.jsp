<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:eval expression="@propertyConfigurer.getProperty('url.cdn')" var="cdnUrl" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta charset="UTF-8">
    <title><spring:message code="voj.administration.edit-user.title" text="Edit User" />: ${user.username} | ${websiteName}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="${description}">
    <meta name="author" content="谢浩哲">
    <!-- Icon -->
    <link href="${cdnUrl}/img/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <!-- StyleSheets -->
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/bootstrap-responsive.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/flat-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/style.css" />
    <link rel="stylesheet" type="text/css" href="${cdnUrl}/css/administration/edit-user.css" />
    <!-- JavaScript -->
    <script type="text/javascript" src="${cdnUrl}/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/flat-ui.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/md5.min.js"></script>
    <script type="text/javascript" src="${cdnUrl}/js/pace.min.js"></script>
    <!--[if lte IE 9]>
        <script type="text/javascript" src="${cdnUrl}/js/jquery.placeholder.min.js"></script>
    <![endif]-->
    <!--[if lte IE 7]>
        <script type="text/javascript"> 
            window.location.href='<c:url value="/not-supported" />';
        </script>
    <![endif]-->
</head>
<body>
    <div id="wrapper">
        <!-- Sidebar -->
        <%@ include file="/WEB-INF/views/administration/include/sidebar.jsp" %>
        <div id="container">
            <!-- Header -->
            <%@ include file="/WEB-INF/views/administration/include/header.jsp" %>
            <!-- Content -->
            <div id="content">
                <h2 class="page-header"><i class="fa fa-user"></i> <spring:message code="voj.administration.edit-user.edit-user" text="Edit User" /></h2>
                <form id="profile-form" onSubmit="onSubmit(); return false;">
                    <div class="alert alert-error hide"></div> <!-- .alert-error -->
                    <div class="alert alert-success hide"><spring:message code="voj.administration.edit-user.profile-updated" text="Profile updated." /></div> <!-- .alert-success -->
                    <div class="control-group row-fluid">
                        <label for="username"><spring:message code="voj.administration.edit-user.username" text="Username" /></label>
                        <input id="username" class="span12" type="text" value="${user.username}" disabled="disabled" />
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="password"><spring:message code="voj.administration.edit-user.password" text="Password" /></label>
                        <input id="password" class="span12" type="password" maxlength="16" />
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="email"><spring:message code="voj.administration.edit-user.email" text="Email" /></label>
                        <input id="email" class="span12" type="text" maxlength="64" value="${user.email}" />
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="user-group"><spring:message code="voj.administration.edit-user.user-group" text="User Group" /></label>
                        <select id="user-group" name="userGroup">
                        <c:forEach var="userGroup" items="${userGroups}">
                            <option value="${userGroup.userGroupSlug}" <c:if test="${userGroup.userGroupSlug == user.userGroup.userGroupSlug}">selected</c:if> >${userGroup.userGroupName}</option>
                        </c:forEach>
                        </select>
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="prefer-language"><spring:message code="voj.administration.edit-user.prefer-language" text="Prefer Language" /></label>
                        <select id="prefer-language" name="preferLanguage">
                        <c:forEach var="language" items="${languages}">
                            <option value="${language.languageSlug}" <c:if test="${language.languageId == user.preferLanguage.languageId}">selected</c:if> >${language.languageName}</option>
                        </c:forEach>
                        </select>
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="location"><spring:message code="voj.administration.edit-user.location" text="Location" /></label>
                        <input id="location" class="span12" type="text" value="${location}" maxlength="128" />
                    </div> <!-- .control-group -->
                    <div class="control-group row-fluid">
                        <label for="website"><spring:message code="voj.administration.edit-user.website" text="Website" /></label>
                        <input id="website" class="span12" type="text" value="${website}" maxlength="64" />
                    </div> <!-- .control-group -->
                    <div class="row-fluid">
                        <div class="span12">
                            <label for="social-links">
                                <spring:message code="voj.administration.edit-user.social-links" text="Social Links" />
                                <a id="new-social-link" title="<spring:message code="voj.administration.edit-user.new-social-link" text="New social link" />" href="javascript:void(0);">
                                    <i class="fa fa-plus-circle"></i>
                                </a>
                            </label>
                            <div id="social-links">
                                <p id="no-social-links"><spring:message code="voj.administration.edit-user.no-social-links" text="No Social Links." /></p>
                                <ul></ul>
                            </div> <!-- #social-links -->
                        </div> <!-- .span12 -->
                    </div> <!-- .row-fluid -->
                    <div class="row-fluid">
                        <div class="span12">
                            <label for="wmd-input"><spring:message code="voj.administration.edit-user.about-me" text="About Me" /></label>    
                            <div id="markdown-editor">
                                <div class="wmd-panel">
                                    <div id="wmd-button-bar"></div> <!-- #wmd-button-bar -->
                                    <textarea id="wmd-input" class="wmd-input">${aboutMe}</textarea>
                                </div> <!-- .wmd-panel -->
                                <div id="wmd-preview" class="wmd-panel wmd-preview"></div> <!-- .wmd-preview -->
                            </div> <!-- #markdown-editor -->
                        </div> <!-- .span12 -->
                    </div> <!-- .row-fluid -->
                    <div class="row-fluid">
                        <div class="span12">
                            <button class="btn btn-primary" type="submit"><spring:message code="voj.administration.edit-user.update-profile" text="Update Profile" /></button>
                        </div> <!-- .span12 -->
                    </div> <!-- .row-fluid -->
                </form> <!-- #profile-form -->
            </div> <!-- #content -->
        </div> <!-- #container -->
    </div> <!-- #wrapper -->
    <!-- Java Script -->
    <!-- Placed at the end of the document so the pages load faster -->
    <%@ include file="/WEB-INF/views/administration/include/footer-script.jsp" %>
    <script type="text/javascript">
        $.getScript('${cdnUrl}/js/markdown.min.js', function() {
            converter = Markdown.getSanitizingConverter();
            editor    = new Markdown.Editor(converter);
            editor.run();

            $('.markdown').each(function() {
                var plainContent    = $(this).text(),
                    markdownContent = converter.makeHtml(plainContent.replace(/\\\n/g, '\\n'));
                
                $(this).html(markdownContent);
            });
        });
    </script>
    <script type="text/javascript">
        socialServices      = {
            'Facebook'      : 'https://facebook.com/',
            'Twitter'       : 'https://twitter.com/',
            'Weibo'         : 'http://weibo.com/',
            'Instagram'     : 'https://instagram.com/',
            'GitHub'        : 'https://github.com/',
            'StackOverflow' : 'http://stackoverflow.com/users/',
            'LinkedIn'      : 'https://www.linkedin.com/profile/view?id='
        };
    </script>
    <script type="text/javascript">
        $(function() {
            var mySocialLinks   = {
                'Facebook'      : '${socialLinks['Facebook']}',
                'Twitter'       : '${socialLinks['Twitter']}',
                'Weibo'         : '${socialLinks['Weibo']}',
                'Instagram'     : '${socialLinks['Instagram']}',
                'GitHub'        : '${socialLinks['GitHub']}',
                'StackOverflow' : '${socialLinks['StackOverflow']}',
                'LinkedIn'      : '${socialLinks['LinkedIn']}'
            };

            for ( var serviceName in mySocialLinks ) {
                var serviceBaseUrl  = socialServices[serviceName],
                    serviceUsername = mySocialLinks[serviceName],
                    serviceUrl      = serviceBaseUrl + serviceUsername;

                if ( typeof(serviceUsername) != 'undefined' && serviceUsername != '' ) {
                    $('#no-social-links').addClass('hide');
                    $('#social-links > ul').append(getSocialLinkContainer(serviceName, serviceUrl));
                }
            }
        });
    </script>
    <script type="text/javascript">
        $('#new-social-link').click(function() {
            var serviceName = 'Facebook', 
                serviceUrl  = socialServices['Facebook'];
            
            $('#no-social-links').addClass('hide');
            $('#social-links > ul').append(getSocialLinkContainer(serviceName, serviceUrl));
        });
    </script>
    <script type="text/javascript">
        function getSocialLinkContainer(serviceName, serviceUrl) {
            var containerTemplate = '<li class="social-link">' +
                                    '    <div class="header">' +
                                    '        <h5>%s</h5>' +
                                    '        <ul class="inline">' +
                                    '            <li><a href="javascript:void(0);"><i class="fa fa-edit"></i></a></li>' +
                                    '            <li><a href="javascript:void(0);"><i class="fa fa-trash"></i></a></li>' +
                                    '        </ul>' +
                                    '    </div> <!-- .header -->' +
                                    '    <div class="body hide">' +
                                    '        <div class="row-fluid">' +
                                    '            <div class="span4">' +
                                    '                <label><spring:message code="voj.administration.edit-user.service-name" text="Service Name" /></label>' +
                                    '            </div> <!-- .span4 -->' +
                                    '            <div class="span8">' +
                                    '                <div class="control-group">' +
                                    '                    <select class="service">' + getSocialLinkOptions(serviceName) + '</select>' +
                                    '                </div> <!-- .control-group -->' +
                                    '            </div> <!-- .span8 -->' +
                                    '        </div> <!-- .row-fluid -->' +
                                    '        <div class="row-fluid">' +
                                    '            <div class="span4">' +
                                    '                <label>URL</label>' +
                                    '            </div> <!-- .span4 -->' +
                                    '            <div class="span8">' +
                                    '                <div class="control-group">' +
                                    '                    <input class="url span8" type="text" maxlength="128" value="%s" />' +
                                    '                </div> <!-- .control-group -->' +
                                    '            </div> <!-- .span8 -->' +
                                    '        </div> <!-- .row-fluid -->' +
                                    '    </div> <!-- .body -->' +
                                    '</li> <!-- .social-link -->';

            return containerTemplate.format(serviceName, serviceUrl);
        }
    </script>
    <script type="text/javascript">
        function getSocialLinkOptions(selectedServiceName) {
            var socialLinkOptions       = '',
                optionTemplate          = '<option value="%s">%s</option>',
                selectedOptionTemplate  = '<option value="%s" selected>%s</option>';

            for ( var serviceName in socialServices ) {
                if ( serviceName == selectedServiceName ) {
                    socialLinkOptions  += selectedOptionTemplate.format(serviceName, serviceName);
                } else {
                    socialLinkOptions  += optionTemplate.format(serviceName, serviceName);
                }
            }
            return socialLinkOptions;
        }
    </script>
    <script type="text/javascript">
        $('#social-links').delegate('i.fa-edit', 'click', function() {
            var socialLinkContainer = $(this).parent().parent().parent().parent().parent(),
                isBodyUnfolded      = $('.body', $(socialLinkContainer)).is(':visible');

            if ( isBodyUnfolded ) {
                $('.body', $(socialLinkContainer)).addClass('hide');
            } else {
                $('.body', $(socialLinkContainer)).removeClass('hide');
            }
        });
    </script>
    <script type="text/javascript">
        $('#social-links').delegate('i.fa-trash', 'click',function() {
            var socialLinkContainer = $(this).parent().parent().parent().parent().parent(),
                socialLinks         = $('li.social-link', '#social-links').length;
            
            $(socialLinkContainer).remove();

            if ( socialLinks == 1 ) {
                $('#no-social-links').removeClass('hide');
            }
        });
    </script>
    <script type="text/javascript">
        $('#social-links').delegate('select.service', 'change', function() {
            var socialLinkContainer = $(this).parent().parent().parent().parent().parent(),
                serviceName         = $(this).val(),
                serviceBaseUrl      = socialServices[serviceName];
            
            $('h5', $(socialLinkContainer)).html(serviceName);
            $('input.url', $(socialLinkContainer)).val(serviceBaseUrl);
        });
    </script>
    <script type="text/javascript">
        function getSocialLinks() {
            var socialLinks = {};

            $('.social-link').each(function() {
                var serviceName     = $('select.service', $(this)).val(),
                    serviceUrl      = $('input.url', $(this)).val(),
                    serviceBaseUrl  = socialServices[serviceName],
                    serviceUsername = serviceUrl.substr(serviceBaseUrl.length);

                if ( serviceUrl.indexOf(serviceBaseUrl) != -1 && serviceUsername != '' ) {
                    socialLinks[serviceName] = serviceUsername;
                }
            });
            return JSON.stringify(socialLinks);
        }
    </script>
    <script type="text/javascript">
        function onSubmit() {

        }
    </script>
    <c:if test="${GoogleAnalyticsCode != ''}">
    <script type="text/javascript">${googleAnalyticsCode}</script>
    </c:if>
</body>
</html>