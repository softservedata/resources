var addNewPoint = function(poligonNumber,
                           latitudeDegrees, latitudeMinutes, latitudeSeconds,
                           longitudeDegrees, longitudeMinutes,longitudeSeconds) {
    var num = $('.clonedInput').length;
    var newNum = new Number(num + 1);

    if ($('#myparam1').val() == 0) {
        $('#myparam1').val(latitudeDegrees);
        $('#myparam2').val(latitudeMinutes);
        $('#myparam3').val(latitudeSeconds);
        $('#myparam4').val(longitudeDegrees);
        $('#myparam5').val(longitudeMinutes);
        $('#myparam6').val(longitudeSeconds);
    }
    else {
        var newElem = $('#input' + num).clone().attr('id', 'input' + newNum);

        newElem.children('#myparam0').attr(
            'name',
            'resourceArea.poligons[' + poligonNumber + '].points['
            + (newNum - 1) + '].orderNumber').val(newNum);
        newElem.children('#myparam1').attr(
            'name',
            'resourceArea.poligons[' + poligonNumber + '].points['
            + (newNum - 1) + '].latitudeDegrees').val(latitudeDegrees);
        newElem.children('#myparam2').attr(
            'name',
            'resourceArea.poligons[' + poligonNumber + '].points['
            + (newNum - 1) + '].latitudeMinutes').val(latitudeMinutes);
        newElem.children('#myparam3').attr(
            'name',
            'resourceArea.poligons[' + poligonNumber + '].points['
            + (newNum - 1) + '].latitudeSeconds').val(latitudeSeconds);
        newElem.children('#myparam4').attr(
            'name',
            'resourceArea.poligons[' + poligonNumber + '].points['
            + (newNum - 1) + '].longitudeDegrees').val(longitudeDegrees);
        newElem.children('#myparam5').attr(
            'name',
            'resourceArea.poligons[' + poligonNumber + '].points['
            + (newNum - 1) + '].longitudeMinutes').val(longitudeMinutes);
        newElem.children('#myparam6').attr(
            'name',
            'resourceArea.poligons[' + poligonNumber + '].points['
            + (newNum - 1) + '].longitudeSeconds').val(longitudeSeconds);
        $('#input' + num).after(newElem);
        $('#btnDelArea').attr('disabled', '');
    }

}

$(document).ready(
		function() {

			$('#btnAddArea').click(function(){
                addNewPoint(0,0,0,0.0,0,0,0.0);
            });

			$('#btnDelArea').click(function() {
				var num = $('.clonedInput').length;
				//$('#btnAdd').attr('disabled', '');
				if (num <= 1) {
                    $('#btnDel').attr('disabled', 'disabled');
                }
                else {
                    $('#input' + num).remove();
                }

			});

			$('#btnAddDiscrete').click(function() {

			});

            //We can't send the point number from disabled field. The following code
            //makes all point number fields enabled, exactly before sending.
            $(".formsubmit").click(function() {
                $('.clonedInput').each(function () {
                    $(this).find('#myparam0').attr('disabled', '');
                });
            });
			//$('#btnDelArea').attr('disabled', 'disabled');
		});
