$(document).ready(function () {

    $("#resourcesTypeSelect").change(function () {
        table.ajax.reload("/registrator/resource/getResourcesByTypeId");

    });
    var table = $("#table").DataTable({
            //"bProcessing": true,
            "sAjaxSource": "/registrator/resource/getResourcesByTypeId",
            "fnServerParams": function (aoData) {
                aoData.push({"name": "resourceTypeId", "value": $("#resourcesTypeSelect").val()});
            },
            sServerMethod: 'POST',
            "sAjaxDataProp": "",
            "aoColumns": [
                {"mData": "id"},
                //{"mData": "typeId"},
                {"mData": "identifier"},
                {"mData": "description"},
                //{"mData": "registratorId"},
                {"mData": "date"},
                {"mData": "status"},
                //{"mData": "tomeId"},
                //{"mData": "reasonInclusion"}
            ]
        }
    );
});