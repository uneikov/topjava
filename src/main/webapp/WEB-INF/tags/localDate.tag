<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="date" required="true" type="java.time.LocalDateTime" %>
<%@ attribute name="pattern" required="false" type="java.lang.String" %>

<c:if test="${empty pattern}">
    <c:set var="pattern" value="dd.MM.yyy HH:mm"/>
</c:if>

<fmt:parseDate value="${date}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both"/>
<fmt:formatDate value="${parsedDate}" type="date" pattern="${pattern}"/>
