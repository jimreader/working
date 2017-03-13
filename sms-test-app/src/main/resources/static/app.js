var stompClient = null;

$( document ).ready(function() {
    connect();
    
    $( window ).unload(function() {
    	  disconnect();
    	});
});

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content, JSON.parse(greeting.body).originator);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    $("#messages-wrapper").append("<div class=\"message to\">" + $("#name").val() + "</div>");
}

function showGreeting(message, originator) {
    $("#messages-wrapper").append("<div class=\"message from\">" + message + "</div>");
    $("#alert")[0].play();
    $("#originator").text(originator);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendName(); });
});

