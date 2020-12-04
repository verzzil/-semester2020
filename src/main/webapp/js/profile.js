$(document).ready(function() {
    $("#thoughtsSelected").click(function() {
        changeSelection(
            "thoughtsSelected",
            "likedUsersSelected",
            "thoughts",
            "likedUsers"
        );
    });
    $("#likedUsersSelected").click(function() {
        changeSelection(
            "likedUsersSelected",
            "thoughtsSelected",
            "likedUsers",
            "thoughts"
        );
    });
});

function changeSelection(clicked, another, clickedBlock, anotherBlock) {
    if($("#"+clicked).css("background") !== "#77ffcd") {
        $("#"+clicked).css("background", "#77ffcd");
        $("#"+another).css("background","#e4e4e4");
        $("#"+anotherBlock).fadeOut(100, function() {
            $("#"+clickedBlock).css("display","grid").fadeIn(100);
        });
    }
}