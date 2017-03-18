<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>List of contacts</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
   <div>
      <jsp:forward page="/my-servlet?command=show"/>
   </div>
</body>
</html>
