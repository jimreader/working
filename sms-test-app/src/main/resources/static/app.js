var stompClient = null;

$(document).ready(function() {
	connect();

	scrollDown();
});

function connect() {
	var socket = new SockJS('/gs-guide-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/greetings', function(greeting) {
			showGreeting(JSON.parse(greeting.body).content, JSON
					.parse(greeting.body).originator);
		});
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	console.log("Disconnected");
}

function showGreeting(message, originator) {
	$("#alertMedia")[0].play();

	$('#messages').append(
			'<div class="message"><div class="fromThem"><p>' + message
					+ '</p><date><b>' + originator
					+ '</b> 01/01/2017 12:34</date></div></div>');
	setTimeout(function() {
		$('form.chat > span').addClass('spinner');
	}, 100);
	setTimeout(function() {
		$('form.chat > span').removeClass('spinner');
	}, 2000);
	scrollDown();
}

$(function() {

	$("#send").click(function() {
		event.preventDefault();
		sendMessage();
	});

	$("#send").keypress(function(event) {
		if (event.which == 13) {
			event.preventDefault();
			$('#send').click();
		}
	});
});

function sendMessage() {

	var message = $("#newmessage").val();
	stompClient.send("/app/hello", {}, JSON.stringify({
		'name' : message
	}));

	if ($("#newmessage").val()) {
		$('#messages')
				.append(
						'<div class="message"><div class="myMessage"><p>'
								+ message
								+ '</p><date><b>Me</b> 01/01/2017 12:34</date></div></div>');

		setTimeout(function() {
			$('form.chat > span').addClass('spinner');
		}, 100);
		setTimeout(function() {
			$('form.chat > span').removeClass('spinner');
		}, 2000);
	}
	$("#newmessage").val('');
	scrollDown();
}

function scrollDown() {
	$("#messages").animate({ scrollTop: $("#messages")[0].scrollHeight}, 1000);
}