var stompClient = null;

function connect() {
  var socket = new SockJS('/websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    // setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/admin/updates', function (message) {
      console.log('Recebeu');
      console.log('Message: ', message);

      try {
        console.log(JSON.parse(message.body));
      } catch (error) {
        console.log('Error');
        console.log(error);
      }
    });
  });
}

connect();

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  console.log('Disconnected');
}

window.addEventListener('beforeunload', function (e) {
  // e.preventDefault();
  // e.returnValue = '';
  disconnect();
});

function sendMessage() {
  stompClient.send(
    '/app/updateAdminInfo',
    {},

    JSON.stringify({
      content: 'Test',
    }),
  );
}
