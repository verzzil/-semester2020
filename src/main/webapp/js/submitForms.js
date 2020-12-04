$(document).ready(function() {
    // open user profile
    $("#userList").on('click', 'li', function(e) {
        if(e.target.className !== "countLikes cl" && e.target.className !== "icl" && e.target.className !== "scl") {
            $($(this).children('form')).submit();
        }
    });

    // logout
    submitForm("#logout");

    // open create thought
    submitForm("#openCreateThought");

    // open messages
    submitForm("#openMessages");

    // open my profile
    submitForm("#openMyProfile");

    // open liked user
    submitForm(".openLikedUser");
});

function submitForm(current) {
    $(current).click(function() {
        console.log("click");
        $($(this).children('form')).submit();
    });
}