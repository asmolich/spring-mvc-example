$(document).ready(function() {
	var ani_left = true;
	$("#feedback_button").click(function() {
		$("#feedback_button").animate({
			right : (ani_left ? '+=430' : '-=430'),
		}, 1000, function() {
			ani_left = !ani_left;
		});
		$(".feedback").toggle(1000, function() {
		});
	});
});