var stompClient = null;

function connect() {
  var socket = new SockJS('/websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    stompClient.subscribe('/admin/updates', function (message) {
      try {
        const resJson = JSON.parse(message.body);

        showBarrels(resJson.barrels);
        updateTopSearchList(resJson.topSearch);
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

function showBarrels(barrels) {
  $('#barrels').html('');

  barrels.forEach((barrel) => {
    $('#barrels').append(`
      <tr >
        <td><span> ${barrel.hostAddress} </span></td>
        <td><span> ${barrel.isActive ? 'Ativo' : 'Inativo'} </span></td>
        <td><span> ${barrel.avgResponseTime} (decimos de segundos) </span></td>
      </tr>
      `);
  });
}

function updateTopSearchList(listTopSearch) {
  $('#listTopSearch').html('');

  listTopSearch.forEach((topSearch) => {
    $('#listTopSearch').append(`
      <tr >
        <td><span> ${topSearch.search} </span></td>
        <td><span> ${topSearch.count} </span></td>
      </tr>
      `);
  });
}

function sendMessage() {
  stompClient.send(
    '/app/updateAdminInfo',
    {},

    JSON.stringify({
      content: 'Test',
    }),
  );
}
