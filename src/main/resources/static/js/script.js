$(document).ready(function() {
    $("#up").click(function () {
        var up = $.post("/upvote", {changeBy: 1}, function (dataBack) {
            $("#upvote").text(dataBack);
        })
    })
})