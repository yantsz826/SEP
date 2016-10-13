/* 
Author: Nicholas Lawrence
Student: 17075930
Date Edited: 12/10/2016
 */
$(document).ready(function () {
    //Responsive Column Visibility
    //Mobile
    if ($(window).width() < 768) {
        $('.hideableCol').hide();
    }
    //On row select
    $('#invoiceList').click(function () {
        $('#hTxt').val($('.selected .idCol').text());
    });
    if ($('.dataTables_empty').children() === 0) {
        alert("test");
        $('#getDetailsBtn').hide();
    }
    else {
        $.fn.dataTable.moment('DD/MM/YYYY');
        $('#invoiceList').dataTable({
            "order": [[1, "desc"]]
        });
    }
    $('#invoiceList').on("click", "tbody tr", function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            $('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });
});