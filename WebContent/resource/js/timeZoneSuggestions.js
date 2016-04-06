jQuery(document).ready(function ($) {

    $("#time_search").keyup(function () {
        if (!this.value) {
            clearData();
            $("#time_id").val('');
        }
    });

    $('#time_search').autocomplete({
        serviceUrl: location.origin + '/timeZones',
        paramName: "value",
        delimiter: ",",
        minChars: 2,
        autoSelectFirst: true,
        deferRequestBy: 100,
        type: "GET",
        onSearchStart: function () {
            // clearData();
        },
        transformResult: function (response) {
            return {
                suggestions: $.map($.parseJSON(response), function (item) {
                    return {
                        value: item.description,
                        data: item.id
                    };
                })
            };
        },
        onSelect: function (suggestion) {
            $("#time_id").val(suggestion.id)
            //$.ajax({
            //    url: "getOwnerInfo",
            //    data: {
            //        ownerLogin: suggestion.data
            //    },
            //    dataType: "json",
            //    type: "GET",
            //    success: function (data) {
            //        clearData();
            //        $("#time_id").val(data.id);
            //    }
            //});
        }

    });

    // to restore the information about the co-owner after unsuccessful validation
    if ($("#time_id").val() !== "") {
        //$.ajax({
        //    url: "getOwnerInfo",
        //    data: {
        //        ownerLogin: $("#time_id").val()
        //    },
        //    dataType: "json",
        //    type: "GET",
        //    success: function (data) {
        //        $("#time_search").val(data.lastName + " " + data.firstName + " " + data.middleName);
        //    }
        //});
    }

    function clearData() {
        //$("#reasonInclusion").val("");
        //$(".checkbox").each(function () {
        //    $("input").prop("checked", false);
        //    $(".disable").prop("disabled", true);
        //});
    }

});
