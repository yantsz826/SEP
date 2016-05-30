/* 
Author: Nicholas Lawrence
Student: 17075930
Date Edited: 30/05/2016
 */
$(document).ready(function () {
    //Responsive Column Visibility
    //Mobile
    if ($(window).width() < 768) {
        $('.hideable').hide();
    }
    //On Edit Registration
    $('#editUsersLink').click(function() {
        window.location.replace("./UserEdit");
    });
    //On Manage Profile link click
    $('#editRegLink').click(function() {
        window.location.replace('./UserRegistration.jsp');
    });
    //On row select
    $('#invoiceList').click(function() {
        $('#hTxt').val($('.selected .idCol').text());
    });
});