var baseUrl;

function positionFooter() {

    var headerHeight = $("#header").parent(".row").height();
    var menuHeight = $("#menu").parent(".row").height();
    var body = $("#body");
    var footerHeight = $("#footer").parent(".row").height();

    var newBodyHeight = ($(window).height()-headerHeight-menuHeight-footerHeight-5);

    console.log("Header: "+headerHeight+"Menu: "+menuHeight+" Body: "+body.height()
        + " footer: " + footerHeight +" new body: "+newBodyHeight
        + " window: "+$(window).height());

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
    baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];

    $(document).ajaxSuccess(function(){
        positionFooter();
    });

    $(window).resize(positionFooter);

    //$(document).bind("DOMSubtreeModified",function(){
    //    //console.log($('body').width() + ' x '+$('body').height());
    //    positionFooter();
    //});

    $(window).load(function () {

    //    Count resources in footer
        $.post(baseUrl.toString() + "/registrator/resource/countResources", {"count": "true"}, onPostSuccess);
        function onPostSuccess (data) {
            if(data.toString().length < 5) {
                data = ""+data;
            }
            $("#count").html(data);
        }

        //$("#body").change(positionFooter());
    });

    //$("#body").children("div").resize(BH());
});