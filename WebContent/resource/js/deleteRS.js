$(function() {
	$(document).on("click","#deleterestype",
					function(event) {
						var conBox = confirm("Ви впевнені, що хочете видалити цей підклас?");
						if (conBox) {
							$
									.ajax({
										url : $(event.target).attr("href"),
										type : "DELETE",

										success : function() {
											var tr = $(event.target).closest(
													"tr");
											tr.css("background-color",
													"#000000");
											tr.fadeIn(1000).fadeOut(200,
													function() {
														tr.remove();
													})
										},
										error : function() {
											alert('Цей підклас не може бути видалений, тому що вже існують ресурси даного підкласу')
										}

									});
						} else {
							event.preventDefault();
						}
						event.preventDefault();
					});
});
