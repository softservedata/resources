$(document).ready(function () {
    //var getUrl = window.location;
    //var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];

    $.post(baseUrl.toString() + "/registrator/resource/getResourcesByTypeId",
        {"resourceTypeId": $("#resourcesTypeSelect").val()},
        function (data) {
            $("#searchParameters").html(data);
            $("#table").html("");
        });

    $("#resourcesTypeSelect").change(function () {
        $("#dark_bg").show();
        $.post(baseUrl.toString() + "/registrator/resource/getResourcesByTypeId",
            {"resourceTypeId": $("#resourcesTypeSelect").val()},
            function (data) {
                $("#searchParameters").html(data);
                $("#table").html("");
                $("#dark_bg").hide();
            });
    });

    $(document).on("click", ".search", function () {

        var json = new Object();
        json.discreteParamsIds = [];
        json.discreteParamsCompares = [];
        json.discreteParamsValues = [];
        json.linearParamsIds = [];
        json.linearParamsValues = [];
        json.resourceTypeId = $("#resourcesTypeSelect").val();

        $("#dark_bg").show();

        $(".discreteParameter").each(function () {
            json.discreteParamsIds.push($(this).attr("param_id"));
            json.discreteParamsCompares.push($(this).find(".compare").val());
            json.discreteParamsValues.push($(this).find(".value").val());
        });

        $(".linearParameter").each(function () {
            json.linearParamsIds.push($(this).attr("param_id"));
            json.linearParamsValues.push($(this).find(".value").val());
        });

        $.ajax({
            type: "POST",
            url: baseUrl.toString() + "/registrator/resource/resourceSearch",
            data: JSON.stringify(json),
            contentType: 'application/json; charset=utf-8',
            timeout: 60000,
            dataType: 'json',
            success: function(data){
                createDataTable(data);
                //$("#table").html(data);
                //$("#datatable").DataTable();
                $("#dark_bg").hide();
            },
            error: function () {
                $("#dark_bg").hide();
                bootbox.alert(jQuery.i18n.prop('msg.error'));
            }
        });


    })
});
