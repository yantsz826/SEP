/* 
Author: Nicholas Lawrence
Student: 17075930
Date Edited: 30/05/2016

 */
$(document).ready(function() {
    //If LogInFailed in query string, display error message
    if (window.location.href.indexOf("LogInFailed") > -1)
    {
        $('<p style="color:red;width:100%;">The vendor ID/password combination do not ' +
                'match any records within the system.<br /> Please ensure the ' +
                'details are correct and try again.</p>')
                .insertAfter($('#inputTitle'));
    }
    //If PasswordExpired in query string, display error message
    else if (window.location.href.indexOf("PasswordExpired") > -1)
    {
        $('<p style="color:red;width:100%;">The temporary password has expired.<br />' + 
                ' Please request another password in order to be able to log in.</p>')
                .insertAfter($('#inputTitle'));
    }
    //If forgot pwd button clicked, redirect user to pwd reset.
    $('#forgotpwdbtn').click(function () {
        window.location.replace("./PwdResetOne.jsp");
    });
});

