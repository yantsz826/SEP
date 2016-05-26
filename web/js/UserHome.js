$(document).ready(function () {
    /*$.fn.dataTable.moment( 'DD/MM/YYYY' );
    var table = $('#invoiceList').DataTable({
                "order": [[1, "desc"]]
            });
    $('#button').click( function () {
        table.row('.selected').remove().draw( false );
    } );*/
    
    //Responsive Column Visibility
    //Mobile
    if ($(window).width() < 768) {
        $('.hideable').hide();
    }
    //On Edit Registration
    $('#editUsersLink').click(function() {
        window.location.replace("./UserEdit");
    });
    //On log out link click
    $('#logoutLink').click(function() {
        //window.location.replace("./UserHome_Servlet.java?LogOut");
    });
    $('#invoiceList').click(function() {
        $('#invRef').append($('.selected .idCol').text());
        //$('.selected .idCol').text().appendTo($('#invRef'));
    });
    $('#getDetailsBtn').click(function() {
        if ($('#invRef').text().length === 0)
        {
            return false;
        }
    });
});