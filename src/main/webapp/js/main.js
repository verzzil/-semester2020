$(document).ready(function () {
    let direction = window.screen / 2;
    $("#greeting").mousemove(function (e) {
        let temp = 0;
        if (e.clientX < direction - 1) temp = -100;
        if (e.clientX > direction + 1) temp = 100;

        $("#chocoSprite").css({"left": "+=" + (temp * 0.01)});
        $("#loveMessageSprite").css({"left": "+=" + (temp * 0.02)});
        $("#handSprite").css({"left": "+=" + (temp * 0.015)});
        $("#loveBookSprite").css({"left": "+=" + (temp * 0.037)});
        $("#homeSprite").css({"left": "+=" + (temp * 0.02)});
        direction = e.clientX;
    });

    $("#signIn").click(function () {
        $("#layers").animate({left: "0"}, {duration: 1000});
        $("#layer1").animate({left: "0"}, {duration: 1000});
        $("#layer2").delay(250).animate({left: "0"}, {duration: 1000});
        $("#layer3").delay(500).animate({left: "0"}, {duration: 1000});
        $("#layer4").delay(750).animate({left: "0"}, {duration: 1000});
        $("#layer5").delay(1000).animate({left: "0"}, {duration: 1000});
        $("#layerSprite").css({"animation": "layerAnim 20s ease-in-out infinite"});
        $("body").delay(550)
            .queue(function (next) {
                $(this).css("overflow", "hidden");
                next();
            });
    });

    $("#closeSignInForm").click(function () {
        $("#layer5").animate({left: "100vw"}, {duration: 1000});
        $("#layer4").delay(125).animate({left: "100vw"}, {duration: 1000});
        $("#layer3").delay(250).animate({left: "100vw"}, {duration: 1000});
        $("#layer2").delay(500).animate({left: "100vw"}, {duration: 1000});
        $("#layer1").delay(750).animate({left: "100vw"}, {duration: 1000});
        $("#layers").delay(1000).animate({left: "100vw"}, {duration: 1000});
        $("body").delay(1200)
            .queue(function (next) {
                $(this).css("overflow-y", "scroll");
                $("#layerSprite").css({"animation": "none"});
                next();
            });
    });

    $("#menu").click(function () {
        let nav = $("nav");
        if (nav.css("width") === "80px") {
            nav.animate({width: "200px"}, 300);
            $("#logo").animate({width: "200px"}, 300);
            $("#plug").fadeIn();
            $("#menu").css({"background": "#5de2b1"});
            $("body").css({"overflow": "hidden"});
        } else {
            nav.animate({width: "80px"}, 300);
            $("#logo").animate({width: "80px"}, 300);
            $("#plug").fadeOut();
            $("#menu").css({"background": ""});
            $("body").css({"overflow-y": "scroll"});
        }
    });
    $("#plug").click(function () {
        $("nav").animate({width: "80px"}, 300);
        $("#logo").animate({width: "80px"}, 300);
        $("#plug").fadeOut();
        $("#menu").css({"background": ""});
        $("body").css({"overflow-y": "scroll"});
        $("#filters").fadeOut();
    });

    $("#search").on('click', function (e) {
        if (
            e.target.id !== "searchBlock" &&
            e.target.id !== "showFilters" &&
            e.target.id !== "liveSearchInput"
        ) {
            if ($("#searchBlock").css('display') === "none") {
                $("#search").css("background", "#5de2b1");
                $("#searchBlock").fadeIn();
                $("#showFilters").css({transform: "rotate(360deg)"});
                $("#liveSearchInput").css({transform: "translateX(-20px)"});
            } else {
                $("#search").css("background", "transparent");
                $("#searchBlock").fadeOut();
                $("#showFilters").css({transform: "rotate(0deg)"});
                $("#liveSearchInput").css({transform: "translateX(20px)"});
            }
        }
    });

    $("#showFilters").click(function () {
        $("#plug").fadeIn();
        $("#filters").fadeIn().css('display', 'grid');
    });

});