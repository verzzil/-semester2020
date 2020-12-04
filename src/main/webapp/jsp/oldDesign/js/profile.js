$(document).ready(function() {

    $('a[href^="#"]').click(function(){
        $("#for-each-user > *").css({"display":"none"});
        $("#available-sections a").css({"background":"","text-decoration":"none"});
        $(this).css({"background":"rgba(82, 179, 164, 1)", "text-decoration":"underline"});

        let anchor = $(this).attr('href');
        $(anchor).fadeIn();
        // $('html, body').animate({scrollTop: $(anchor).offset().top}, 500);
        return false;
    });

});