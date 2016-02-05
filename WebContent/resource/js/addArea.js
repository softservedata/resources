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
            if (poligonNumber > 0) {
                $(this).attr( "name",$(this).attr("name").replace(
                    'poligons[' + (poligonNumber-1) + ']',
                    'poligons[' + poligonNumber + ']'));
            }
			});
      
		newElem.find('input#pointNumber').val(newNum);
		newElem.find('input#myparam1').val(latitudeDegrees);
        newElem.find('input#myparam2').val(latitudeMinutes);
        newElem.find('input#myparam3').val(latitudeSeconds);
        newElem.find('input#myparam4').val(longitudeDegrees);
        newElem.find('input#myparam5').val(longitudeMinutes);
        newElem.find('input#myparam6').val(longitudeSeconds);
        
		$('#areaInput' + num).after(newElem);
		$('#btnDelAreaPoint').removeAttr('disabled');
    }

}

$(document).ready(
		function() {

			$.post("getParameters", {
				"resourceTypeName" : $("#resourcesTypeSelect").val()},
				function(data) {
				$("#typeParameters").html(data);
				
			});	
			
			$("#editNumber").click(function() {
				$('#identifier').removeAttr('readonly');
			});
			
            /* Switch off form submit on ENTER keypress from every input field */
            $('form').bind("keypress", function(e) {
                if (e.keyCode == 13) {
                    e.preventDefault();
                    return false;
                }
            });
			
			/* load parameters of selected value */
			$("#resourcesTypeSelect").change(function() {
				$("#typeParameters").html("")
				$.post("getParameters", {
					"resourceTypeName" : $("#resourcesTypeSelect").val()},
					function(data) {
					$("#typeParameters").html(data);
					
				});
			});
			
			$( "#datepicker" ).datepicker({ dateFormat: 'dd.mm.yy' });
			if ($( "#datepicker" ).val() === "") {
				$("#datepicker").datepicker("setDate", new Date);
			}
			
			$('#btnAddAreaPoint').click(function() {
                if ($('#myparam1').val() == 0) {
					bootbox.alert(jQuery.i18n.prop('msg.enterFirstPoint'));
                }
				addNewPoint(0,0,0,0.0,0,0,0.0);
			});

			$('#btnDelAreaPoint').click(function() {
				var num = $('.clonedAreaInput').length;
				$('#areaInput' + num).remove();
				if (num == 2) {
					$('#btnDelAreaPoint').attr('disabled', 'disabled');
                }
			});
			
			$('.deleteButton').attr('disabled', 'disabled');

		});
