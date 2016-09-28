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
    $(document).ready(function () {
        var canned1 = '<a>This is a canned response</a>';
        var canned2 = '<a>Hi, my name is Moiz. How can I help you?';
        var canned3 = '<a>Please wait for a few minutes. I will get back to you in sometime after checking the logs.';
        var canned4 = '<a>I need your email id, phone number and account number.';
        var canned5 = '<a>Thank you. Let me know if you have any other questions.';

        var canned1 = $('<a>This is a canned response</a>');
        $(canned).click(function () {
            $(this).css("background", "#ccc");
            copyTextToClipboard($(this).text());
        });
        var canned2 = $('<a>This is a canned response</a>');
        $(canned).click(function () {
            $(this).css("background", "#ccc");
            copyTextToClipboard($(this).text());
        });
        var canned3 = $('<a>This is a canned response</a>');
        $(canned).click(function () {
            $(this).css("background", "#ccc");
            copyTextToClipboard($(this).text());
        });
        var canned4 = $('<a>This is a canned response</a>');
        $(canned).click(function () {
            $(this).css("background", "#ccc");
            copyTextToClipboard($(this).text());
        });
        var canned5 = $('<a>This is a canned response</a>');
        $(canned).click(function () {
            $(this).css("background", "#ccc");
            copyTextToClipboard($(this).text());
        });
        $('body').append(canned1);
        $('body').append('<br/>');
        $('body').append(canned2);
        $('body').append('<br/>');
        $('body').append(canned3);
        $('body').append('<br/>');
        $('body').append(canned4);
        $('body').append('<br/>');
        $('body').append(canned5);
        $('body').append('<br/>');
    });


    var copyTextToClipboard = function (text) {
        document.addEventListener("copy", function (evt) {
            evt.clipboardData.setData("text/plain", text), evt.preventDefault(), document.removeEventListener("copy")
        }), document.execCommand("copy")
    };
</script>
</body>
</html>
