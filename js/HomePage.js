$(document).ready(function () {
    $.fn.dataTable.moment( 'DD/MM/YYYY' );
    var table = $('#invoiceList').DataTable();
    
    $('#invoiceList tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
 
    $('#button').click( function () {
        table.row('.selected').remove().draw( false );
    } );
    
    //Responsive Column Visibility
    //Mobile
    if ($(window).width() < 768) {
        $('.hideable').hide();
    }
});