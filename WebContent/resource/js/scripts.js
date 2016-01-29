var baseUrl;

function positionFooter() {

    var headerHeight = $("#header").parent(".row").height();
    var menuHeight = $("#menu").parent(".row").height();
    var body = $("#body");
    var footerHeight = $("#footer").parent(".row").height();

    var newBodyHeight = ($(window).height()-headerHeight-menuHeight-footerHeight-5);

    //console.log("Header: "+headerHeight+"Menu: "+menuHeight+" Body: "+body.height()
    //    + " footer: " + footerHeight +" new body: "+newBodyHeight
    //    + " window: "+$(window).height());

    //if ( ($("body").children(".container").height()+footerHeight) < $(window).height()) {
    if ( (body.height()) < newBodyHeight+5) {
        body.parent(".row").height(newBodyHeight);
    } else {
        body.parent(".row").css({
            height: ""
        })
    }
}

$(document).ready(function () {

    positionFooter();

    var getUrl = window.location;
    baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];

    $(document).ajaxSuccess(function(){
        positionFooter();
    });

    $(window).resize(positionFooter);

    $(window).load(function () {

    //    Count resources in footer
        $.post("/registrator/resource/countResources", {"count": "true"}, onPostSuccess);
        function onPostSuccess (data) {
            if(data.toString().length < 5) {
                data = ""+data;
            }
            $("#count").html(data);
        }
    });

    //Dropdown menu
    $(".dropdown").mouseenter(function(){
        $(this).find(".dropdown_menu").slideDown(150);
    });

    $(".dropdown").mouseleave(function(){
        $(this).find(".dropdown_menu").slideUp(150);
    });

    //Testing....
    document.onkeydown = function(e) {
        e = e || window.event;
        if (e.shiftKey && e.keyCode == 65) {
            alert('base URL: '+baseUrl + "\n" +
            'pathname split: ' + window.location.pathname.split('/')[1] + "\n" +
            'window.location.pathname: '+window.location.pathname + "\n" +
            'window.location.host: '+window.location.host + "\n"+
            'window.location.hostname: '+window.location.hostname + "\n" +
            'window.location.protocol: '+window.location.protocol);

        }
        return true;
    }
});