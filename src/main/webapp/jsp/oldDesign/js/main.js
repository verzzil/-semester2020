$(document).ready(function () {
    let dialog_windows = [
        "#dialog-window-for-filters",
        "#dialog-for-user-card",
        "#dialog-window-for-sign-in"
    ];
    $("#show-window-for-filters").click(function () {
        show_dialog_window(dialog_windows[0]);
    });
    $("#list-of-users").on('click', 'li img', function () {
        show_dialog_window(dialog_windows[1]);
    });
    $("#show-sign-in-window").click(function () {
        show_dialog_window(dialog_windows[2]);
    });
    $("#black-plug").click(function () {
        $(this).fadeOut();
        dialog_windows.forEach(function (item, i, arr) {
            $(item).fadeOut();
        });
    });
});

function show_dialog_window(dialog_window) {
    $("#black-plug").fadeIn();
    $(dialog_window).fadeIn().css({"display": "grid"});
}