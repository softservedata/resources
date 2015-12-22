$(document).ready(function () {
    var getUrl = window.location;
    var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];

    $(window).load(function () {
        var window_height = $(window).height();
        var body_height = $("#body").height();
        var header_height = $("#header").height();
        var menu_height = $("#menu").height();
        var footer_height = $("#footer").height();
        if (body_height < window_height) {
            $("#body").height(window_height - header_height - menu_height - footer_height - 50);
        }

    //    Count resources in footer
        $.post(baseUrl.toString() + "/registrator/resource/countResources", {"count": "true"}, onPostSuccess);
        function onPostSuccess (data) {
            if(data.toString().length < 5) {
                data = "000"+data;
            }
            $("#count").html(data);
        }
    });



    $("#datatable").DataTable({

        }
    );
});