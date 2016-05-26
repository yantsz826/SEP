<%-- 
    Document   : UserHome
    Created on : 12/05/2016, 9:54:28 PM
    Author     : Nick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    System.out.println(session.getAttribute("authenticated"));
    if (session.getAttribute("authenticated") != "true")
    {
        response.sendRedirect("./LogIn.jsp?LogInFailed");
    }
    %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
        <script type="text/javascript" src="DataTables/datatables.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
        <script src="//cdn.datatables.net/plug-ins/1.10.11/sorting/datetime-moment.js"></script>
        <script type="text/javascript" src="js/jquery.cookie.js"></script>
        <script type="text/javascript" src="js/UserHome.js"></script>
        <link type="text/css" rel="stylesheet" href="DataTables/datatables.min.css" />
        <link type="text/css" rel="stylesheet" href="css/UserHome.css" />
        <script type="text/javascript">
            $(document).ready(function() {             
                var invoiceList = <%=(session.getAttribute("invoiceList"))%>;
                if (invoiceList === null)
                {
                    $('<p>No invoices are currently available for the vendor ID "<%=(session.getAttribute("username"))%>".<br /> If an invoice should be present in this list please contact the Curtin Finance department.</p>').appendTo($('#tableDIV'));
                    $('#getDetailsBtn').hide();
                }
                else
                {
                    $.fn.dataTable.moment( 'DD/MM/YYYY' );
                    $('#invoiceList').DataTable({
                        data : invoiceList,
                        columns : [
                            { title : "Reference",
                              className : "idCol" },
                            { title : "Document Date" },
                            { title : "Narration",
                              className : "hideable"},
                            { title : "Status",
                              className : "hideable" },
                            { title : "Stage" }
                        ],
                        order : [[1, "desc"]]
                    });
                }
                $('#logoutLink').click(function () {
                    logout();
                });
                $('#invoiceList tbody tr').click(function () {
                    if ( $(this).hasClass('selected') ) {
                        $(this).removeClass('selected');
                    }
                    else {
                        $('tr.selected').removeClass('selected');
                        $(this).addClass('selected');
                    }
                } );
            });
            function logout()
            {
                <%session.setAttribute("authenticated", "false");%>
                window.location.replace("./LogIn.jsp");
            }
        </script>
    </head>
    <body>
        <div id="userMenu">
            <ul>
                <li>
                    <a id="editRegLink" class="menuLink">Manage Profile</a>
                </li>
                <%
                    if (session.getAttribute("admin") == "true") { %>
                <li>
                    <a id="editUsersLink" class="menuLink">Edit User List</a>
                </li>
                <%
                    }
                    %>
                <li>
                    <a id="logoutLink" class="menuLink">Log Out</a>
                </li>
            </ul>
        </div>
        <form name="userMenuForm" action="UserHome_Servlet">
            <div id="tableDIV">
                <table id="invoiceList" class="display" cellspacing="0" width="100%">
                </table>
                <input type="hidden" name="invRef">
            </div>
            <button id="getDetailsBtn" type="submit" action="UserHome_Servlet">Get Details</button>
        </form>
    </body>
</html>
