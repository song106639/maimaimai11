function showmsg(msg, color, callback) {
	if (!document.getElementById("popup_info")) {
		var str = '<div style="display:table;width:300px;height:150px;border: 2px solid #dddddd;background-color:#FFFFFF;position:fixed;left:50%;margin-left:-150px;top:50%;margin-top:-75px;z-index:1000;border-radius:4px;box-shadow: 0px 0px 10px red;padding:10px;" id="popup_con"><p style="display:table-cell;vertical-align: middle;line-height: 30px;text-align: center;font-size: 18px;" id="popup_info"></p></div>';
		$(document.body).append($(str));
	}
	let url = 'info.png';
	if (color == "red") {
		url = 'error.png';
	} else if (color == "green") {
		url = 'success.png';
	} else if (color == "yellow") {
		color = "orange";
		url = 'warning.png';
	}
	
	$("#popup_info").text(msg).prepend($('<span id="popup_icon" style="display: inline-block;vertical-align: middle;width: 40px;height: 30px;line-height: 30px;background: url(icon/info.png) no-repeat center center;background-size: 28px 28px;"></span>')).css("color", color);
	$("#popup_con").css({"border-color": color, "box-shadow": "0px 0px 10px " + color});
	$("#popup_icon").css("background-image", "url('icon/" + url + "')");
	$('#popup_con').fadeIn('fast', function() {
		setTimeout(function(){
			$('#popup_con').fadeOut('fast', callback);	
		}, 2000);
	});
}