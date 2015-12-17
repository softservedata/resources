$(document).ready(function () {
    $(window).load(function () {
        var window_height = $(window).height();
        var header_height = $("#header").height();
        var menu_height = $("#menu").height();
        var footer_height = $("#footer").height();

        $("#body").height(window_height - header_height - menu_height - footer_height -50);
    })
});