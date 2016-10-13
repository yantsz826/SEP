$(document).ready(function () {
    var width = $('#userField > .editor-label').width();
    if (width >= $('#passwordField > .editor-label').width()) {
        $('#passwordField > .editor-label').width(width);
    }
    else {
        var width = $('#passwordField > .editor-label')
            .width();
        $('#userField > .editor-label').width(width);
    }

});