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
			"name").replace('valueDiscretes[' + prevNum + ']','valueDiscretes['	+ num + ']'));
			
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