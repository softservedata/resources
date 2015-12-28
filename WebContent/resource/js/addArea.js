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
			
			/* load parameters of selected value */
			$("#resourcesTypeSelect").change(function() {
				$("#typeParameters").html("")
				$.post("getParameters", {
					"resourceTypeName" : $("#resourcesTypeSelect").val()},
					function(data) {
					$("#typeParameters").html(data);
					
				});
			});
			

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
			
			$("button").click(function() {
				
				if ($(this).attr("id").match(/^btnAddDiscreteValue_(\d+)$/)) {
					var theId = parseInt($(this).attr('id').replace(/[^\d]/g,''), 10);
					var num = $('.DiscreteInput_'+ theId).length;
					var prevNum = new Number(num - 1);
					var newNum = new Number(num + 1);
					var newElem = $('#discreteInput_'+ theId + '_'+ num).clone().
					attr('id', 'discreteInput_'	+ theId + '_' + newNum);
					newElem.children('label').text('');
					newElem.children("input:hidden").remove();
					newElem.find('input').each( function() {
						$(this).removeAttr('value');
						$(this).attr("name", $(this).attr(
						"name").replace('values[' + prevNum + ']','values['	+ num + ']'));
						
					});
					$('#discreteInput_'+ theId + '_'+ num).after(newElem);
					$('#btnDelDiscreteValue_' + theId).attr('disabled', '');	
				}
				
				if ($(this).attr("id").match(/^btnDelDiscreteValue_(\d+)$/)) {
					var theId = parseInt($(this).attr('id').replace(/[^\d]/g,''), 10);
					var num = $('.DiscreteInput_'+ theId).length;
					if (num - 1 == 1)
						$('#btnDelDiscreteValue_'+ theId).attr('disabled', 'disabled');
					$('#discreteInput_'+ theId + '_'+ num).remove();
					$('#btnAddDiscreteValue_' + theId).attr('disabled', '');
				}
				
				if ($(this).attr("id").match(/^btnAddLinearValue_(\d+)$/)) {
					var theId = parseInt($(this).attr('id').replace(/[^\d]/g,''), 10);
					//alert(theId);
					var num = $('.LinearInput_'+ theId).length;
					var prevNum = new Number(num - 1);
					var newNum = new Number(num + 1);
					var newElem = $('#linearInput_'+ theId + '_'+ num).clone().
						attr('id', 'linearInput_'	+ theId + '_' + newNum);
					newElem.children('label').text('');
					newElem.children("input:hidden").remove();
					newElem.find('input').each( function() {
						$(this).removeAttr('value');
						$(this).attr("name", $(this).attr(
								"name").replace('segments[' + prevNum + ']','segments['	+ num + ']'));
											
					});
					$('#linearInput_'+ theId + '_'+ num).after(newElem);
					$('#btnDelLinearValue_' + theId).attr('disabled', '');	
				}
				
				if ($(this).attr("id").match(/^btnDelLinearValue_(\d+)$/)) {
					var theId = parseInt($(this).attr('id').replace(/[^\d]/g,''), 10);
					var num = $('.LinearInput_'+ theId).length;
					if (num - 1 == 1)
						$('#btnDelLinearValue_'+ theId).attr('disabled', 'disabled');
					$('#linearInput_'+ theId + '_'+ num).remove();
					$('#btnAddLinearValue_' + theId).attr('disabled', '');
					}
				});
			
			$('.deleteButton').attr('disabled', 'disabled');
			
			

            //We can't send the point number from disabled field. The following code
            //makes all point number fields enabled, exactly before sending.
            $(".formsubmit").click(function() {
                $('.clonedInput').each(function () {
                    $(this).find('#myparam0').attr('disabled', '');
                });
            });
			//$('#btnDelArea').attr('disabled', 'disabled');
		});
