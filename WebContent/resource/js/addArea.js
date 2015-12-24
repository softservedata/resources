/**
 * 
 */
$(document).ready(
		function() {
			$('#btnAddArea').click(
					function() {
						var num = $('.clonedInput').length;
						var newNum = new Number(num + 1);
						var newElem = $('#input' + num).clone().attr('id',
								'input' + newNum);
						newElem.children('#myparam0').attr(
								'name',
								'resourceArea.poligons[0].points['
										+ (newNum - 1) + '].orderNumber');
						newElem.children('#myparam0').attr('value', newNum);
						newElem.children('#myparam1').attr(
								'name',
								'resourceArea.poligons[0].points['
										+ (newNum - 1) + '].latitudeDegrees')
						newElem.children('#myparam2').attr(
								'name',
								'resourceArea.poligons[0].points['
										+ (newNum - 1) + '].latitudeMinutes');
						newElem.children('#myparam3').attr(
								'name',
								'resourceArea.poligons[0].points['
										+ (newNum - 1) + '].latitudeSeconds');
						newElem.children('#myparam4').attr(
								'name',
								'resourceArea.poligons[0].points['
										+ (newNum - 1) + '].longitudeDegrees');
						newElem.children('#myparam5').attr(
								'name',
								'resourceArea.poligons[0].points['
										+ (newNum - 1) + '].longitudeMinutes');
						newElem.children('#myparam6').attr(
								'name',
								'resourceArea.poligons[0].points['
										+ (newNum - 1) + '].longitudeSeconds');
						;
						$('#input' + num).after(newElem);
						$('#btnDelArea').attr('disabled', '');

					});

			$('#btnDelArea').click(function() {
				var num = $('.clonedInput').length;
				$('#input' + num).remove();
				$('#btnAdd').attr('disabled', '');
				if (num - 1 == 1)
					$('#btnDel').attr('disabled', 'disabled');
			});

			$('#btnAddDiscrete').click(function() {

			});

			$('#btnDelArea').attr('disabled', 'disabled');
		});
