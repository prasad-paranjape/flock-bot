<%--
  Created by IntelliJ IDEA.
  User: moiz.p
  Date: 24/09/16
  Time: 3:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Canned Responses</title>
</head>
<body>


<script src="https://code.jquery.com/jquery-latest.min.js"
        type="text/javascript"></script>

<script>
 $(document).ready(function(){

     var canned=$('<a>This is a canned response</a>');
     $(canned).click(function(){
         $(this).css("background","#ccc");
     });
     $('body').append(canned);
 });
</script>
</body>
</html>
