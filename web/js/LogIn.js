/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    if (window.location.href.indexOf("LogInFailed") > -1)
    {
        $('<p style="color:red;width:100%;">The vendor ID/password combination do not ' +
                'match any records within the system.<br /> Please ensure the ' +
                'details are correct and try again.</p>')
                .insertAfter($('#inputTitle'));
    }
    $('#forgotpwdbtn').click(function () {
        window.location.replace("./PwdResetOne.jsp");
    });
});

