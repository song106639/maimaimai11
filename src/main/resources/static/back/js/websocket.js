function openWebSocket(aid) {
	let socket;
	
	if (typeof(WebSocket) == undefined) {
		alert("对不起，您的浏览器不支持WebSocket通信...");
		return;
	}
	
	socket = new WebSocket("ws://127.0.0.1:8888/websocket/" + aid);
	
	socket.onopen = function() {
		console.info("连接成功...");
	}
	
	socket.onclose = function() {
		console.info("已断开...");
	}
	
	socket.onerror = function() {
		console.info("连接服务器出错了...");
	}

	socket.onmessage = function(msg) {
		if (msg.data == "101") { // 则认为是挤退信息
			alert("对不起，您的账号已经其它地方登录，若非本人操作，请及时修改密码...");
			location.href="/admin/loginout";
			return;
		}
		console.info(msg);
	}
}
