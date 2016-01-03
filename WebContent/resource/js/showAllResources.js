$(document).ready(function () {
    var getUrl = window.location;
    var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];

    $.post(baseUrl.toString() + "/registrator/resource/getResourcesByTypeId",
        {"resourceTypeId": $("#resourcesTypeSelect").val()},
        function (data) {
            $("#searchParameters").html(data);
            $("#table").html("");
        });

    $("#resourcesTypeSelect").change(function () {
        //table.ajax.reload(baseUrl.toString() + "/registrator/resource/getResourcesByTypeId");
        $("#dark_bg").show();
        $.post(baseUrl.toString() + "/registrator/resource/getResourcesByTypeId",
            {"resourceTypeId": $("#resourcesTypeSelect").val()},
            function (data) {
                $("#searchParameters").html(data);
                $("#table").html("");
                $("#dark_bg").hide();
            });
    });

    //var table = $("#table").DataTable({
    //    //"bProcessing": true,
    //    "sAjaxSource": baseUrl.toString() + "/registrator/resource/getResourcesByTypeId",
    //    "fnServerParams": function (aoData) {
    //        aoData.push({"name": "resourceTypeId", "value": $("#resourcesTypeSelect").val()});
    //    },
    //    sServerMethod: 'POST',
    //    "sAjaxDataProp": "",
    //    "aoColumns": [
    //        {"mData": "id"},
    //        //{"mData": "typeId"},
    //        {"mData": "identifier"},
    //        {"mData": "description"},
    //        //{"mData": "registratorId"},
    //        {"mData": "date"},
    //        {"mData": "status"},
    //        //{"mData": "tomeId"},
    //        //{"mData": "reasonInclusion"}
    //    ]
    //});

    $(document).on("click", ".search", function () {
        var discreteParamId = [-1];
        var discreteParamCompare = [-1];
        var discreteParamVal = [-1];
        var linearParamId = [-1];
        var linearParamVal = [-1];

        $("#dark_bg").show();

        $(".discreteParameter").each(function () {
            discreteParamId.push($(this).attr("param_id"));
            discreteParamCompare.push($(this).find(".compare").val());
            discreteParamVal.push($(this).find(".value").val());
        });

        $(".linearParameter").each(function () {
            linearParamId.push($(this).attr("param_id"));
            linearParamVal.push($(this).find(".value").val());
        });

        $.ajax({
            type: "POST",
            url: baseUrl.toString() + "/registrator/resource/resourceSearch",
            data: {
                "discreteParametersId": discreteParamId,
                "discreteParametersCompare": discreteParamCompare,
                "discreteParametersValue": discreteParamVal,
                "linearParametersId": linearParamId,
                "linearParametersValue": linearParamVal,
                "resourceTypeId":$("#resourcesTypeSelect").val()
            },
            success: function(data){
                $("#table").html(data);
                $("#datatable").DataTable();
                $("#dark_bg").hide();
            },
            traditional: true
        });



    })
});
