var addNewPoint = function(poligonNumber,
                           latitudeDegrees, latitudeMinutes, latitudeSeconds,
                           longitudeDegrees, longitudeMinutes,longitudeSeconds) {
	var num = $('.clonedAreaInput').length;
	var prevNum = new Number(num - 1);
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
    	
    	var newElem = $('#areaInput' + num).clone().attr('id',
				'areaInput' + newNum);
		newElem.find('input').each(function() {
			$(this).removeAttr('value');
			$(this).attr( "name",$(this).attr("name").replace(
					'points[' + prevNum + ']',
					'points[' + num + ']'));
			});
      
		newElem.find('input#pointNumber').val(newNum);
		newElem.find('input#myparam1').val(latitudeDegrees);
        newElem.find('input#myparam2').val(latitudeMinutes);
        newElem.find('input#myparam3').val(latitudeSeconds);
        newElem.find('input#myparam4').val(longitudeDegrees);
        newElem.find('input#myparam5').val(longitudeMinutes);
        newElem.find('input#myparam6').val(longitudeSeconds);
        
		$('#areaInput' + num).after(newElem);
		$('#btnDelAreaPoint').attr('disabled', '');
    }

}

$(document).ready(
		function() {
			
			/* load parameters of selected value */
			$("#resourcesTypeSelect").change(function() {
				$("#typeParameters").html("")
				$.post("getParameters", {
					"resourceTypeName" : $("#resourcesTypeSelect").val()},
					function(data) {
					$("#typeParameters").html(data);
					
				});
			});
			
			$('#btnAddAreaPoint').click(function() {
                if ($('#myparam1').val() == 0) {
                    alert("Будь ласка, введіть значення координат першої точки.");
                }
				addNewPoint(0,0,0,0.0,0,0,0.0);
			});

			$('#btnDelAreaPoint').click(function() {
				var num = $('.clonedAreaInput').length;
				if (num <= 1) {
					$('#btnDelAreaPoint').attr('disabled', 'disabled');
                }
                else {
                	$('#areaInput' + num).remove();
                }
			});
			
			$('.deleteButton').attr('disabled', 'disabled');
			
			
            //We can't send the point number from disabled field. The following code
/*            //makes all point number fields enabled, exactly before sending.
            $(".formsubmit").click(function() {
                $('.clonedInput').each(function () {
                    $(this).find('#myparam0').attr('disabled', '');
                });
            });*/
			//$('#btnDelArea').attr('disabled', 'disabled');
		});
