$(document).ready(function(){
    
    /**
     * INITIAL ACTIONS
     * */
    
    (function(){
    })();
    
    /**
     * VARIABLES
     * */
    
    var ws = new WebSocket("ws://localhost:8080/Game/server");
    var loggedIn = false;
    var connected = false ;
    var canvas = false;
    var messages = [];
    var MAX_MESSAGES = 20;
    var users = [];
    var playing = false;
    var myId = false;
    var gameObjects = {};
    
    /**
     * HELPING FUCTIONS
     * */
    
    function write(info) {
        console.log(info);
    }
    
    function addUser(user) {
        users[users.length] = user;
        updateUsers();
    }
    
    function removeUser(id) {
        for( var i=0 ; i<users.length ; ++i ) {
            if( users[i]['id'] === id ) {
                users.splice(i,1);
            }
        }
        updateUsers();
    }
    
    function updateUsers() {
        $("#users-list").empty();
        for( var i=0 ; i<users.length ; ++i ) {
            var id = users[i]["id"];
            var username = users[i]["name"];
            var inGame = users[i]['inGame'];
            var color = users[i]['color'];
            
            var userDiv = document.createElement("div");
            var className = "user glyphicon ";
            var iconClass = "glyphicon-user";
            if( id == myId ) {
                iconClass = "glyphicon-certificate";
            } else if( inGame ) {
                iconClass = "glyphicon-play-circle" ;
            }
            className += iconClass ;
            userDiv.className = className;
            $(userDiv).html("<span style='color:"+ color +";'>"+ username +"</span>");
            $("#users-list").append(userDiv);
        }
    }
    
    function receiveFromServer(data) {
        data = JSON.parse(data);
        var type = data['type'];
        switch(type) {
            case "message":
                var message = data['message'];
                var color = data['color'];
                receiveMessage({
                    color: color,
                    message: message
                });
                break;
            case "privateMessage":
                var message = data['message'];
                var color = data['color'];
                receiveMessage({
                    color: color,
                    message: "<i>"+message+"</i>"
                });
                break;
            case "messageFromServer":
                var message = data['message'];
                if(message instanceof Array) {
                    receiveMessage({
                        color: "#FFFFFF",
                        message: "********** HELP **********"
                    });
                    for( var i in message ) {
                        receiveMessage({
                            color: "#FFFFFF",
                            message: "*"+message[i]
                        });
                    }
                    receiveMessage({
                        color: "#FFFFFF",
                        message: "**************************"
                    });
                } else {
                    receiveMessage({
                        color: "#FFFFFF",
                        message: "*"+message
                    });
                }
                break;
            case "userChangedName":
                for( var i in users ) {
                    if( users[i]['id'] == data['id'] ) {
                        users[i]['name'] = data['name'];
                        updateUsers();
                    }
                }
                break;
            case "addUser":
                if( myId === false ) {
                    return;
                }
                var id = data['id'];
                var name = data['name'];
                addUser({
                    id: id,
                    name: name
                });
                break;
            case "removeUser":
                var id = data['id'];
                removeUser(id);
                break;
            case "usersList":
                myId = data['yourId'];
                data = data['users'];
                users = [];
                for( var i in data ) {
                    var user = JSON.parse(data[i]);
                    addUser(user);
                }
                break;
            case "userJoinedGame":
                var id = data['id'];
                var color = data['newColor'];
                for( var i in users ) {
                    if( users[i]['id'] === id ) {
                        users[i]['inGame'] = true;
                        users[i]['color'] = color;
                    }
                }
                updateUsers();
                if( id === myId ) {
                    hideUserInterface();
                    playing = true;
                    refreshCanvas();
                }
                break;
            case "gameFull":
                receiveMessage({
                    color: "#FFFFFF",
                    message: "you can not join right now, the game is full, sorry"
                });
                break;
            case "gameState":
                write(data);
                var data = data['data'];
                for( var i in data ) {
                    var id = data[i]['id'];
                    if( typeof gameObjects[id] === 'undefined' ) {
                        gameObjects[id] = {};
                    }
                    for( var key in data[i] ) {
                        gameObjects[id][key] = data[i][key];
                    }
                }
                break;
            default:
                write("unrecognized data: " + data);
        }
    }
    
    $("body").keydown(function(e){
        if( !playing ) {
            return;
        }
        var key = e.keyCode || e.which;
        switch(key) {
            case 37:
                write("left");
                break;
            case 39:
                write("right");
                break;
            case 32:
                write("space");
                break;
        }
    });
    
    /*
    addUser({id:1,name:"asd"});
    addUser({id:2,name:"qwe"});
    addUser({id:3,name:"dsff"});
    addUser({id:4,name:"gh"});
    */
    function receiveMessage(data) {
        messages[messages.length] = data;
        if( messages.length > MAX_MESSAGES ) {
            messages.shift();
        }
        updateMessages();
    }
    
    function updateMessages() {
        var scrollH = $("#chat-messages")[0].scrollHeight;
        var scrolldown = ($("#chat-messages").scrollTop()+$("#chat-messages").height() >= scrollH);
        $("#chat-messages").empty();
        for( var i in messages ) {
            var message = messages[i];
            var msg = message["message"];
            var color = message["color"];
            var msgDiv = document.createElement("div");
            $(msgDiv).css("color",color);
            $(msgDiv).html(msg);
            $("#chat-messages").append(msgDiv);
        }
        if( scrolldown ) {
            scrollH = $("#chat-messages")[0].scrollHeight;
            $("#chat-messages").scrollTop( scrollH );
        }
    }
    
    function sendToServer(data,type) {
        var type = ( typeof type === 'undefined' )? 'message' : type ;
        var info = JSON.stringify({
            type: type,
            data: data,
        });
        ws.send(info);
    }
    
    function openUserInterface() {
        $("#user-interface").css("top","0px");
        $("#show-hide-button").addClass("glyphicon-arrow-up");
        $("#show-hide-button").removeClass("glyphicon-arrow-down");
        $("#chat-field").focus();
        $("#chat-field").val("");
    }
    
    function hideUserInterface() {
        $("#user-interface").css("top","-180px");
        $("#show-hide-button").removeClass("glyphicon-arrow-up");
        $("#show-hide-button").addClass("glyphicon-arrow-down");
    }
    
    
    /**
     * EVENTS
     * */
    /*
    $("#show-hide-button").click(function(){
        var top = parseInt($("#user-interface").css("top"));
        if( top >= 0 ) {
            hideUserInterface();
        } else {
            openUserInterface();
        }
    });
    */
    $("#chat-send-button").click(function(){
        var msg = $("#chat-field").val();
        if( !msg.length || !msg.trim().length ) {
            return;
        }
        if( msg[0] === '/' ) {
            msg = msg.substring(1);
            sendToServer(msg,"command");
        } else {
            sendToServer(msg,"message");
        }
        $("#chat-field").val("");
    });
    
    $("#chat-field").keydown(function(e){
        var key = e.keyCode || e.which;
        if( key === 13 ) {
            $("#chat-send-button").click();
        }
    });
    
    /**
    * WEBSOCKETS CLIENT FUNCTIONS
    * */
   
    ws.onopen = function() {
        connected = true;
        write("open");
        openUserInterface();
        receiveMessage({
            message: "Welcome! Be respectful and have a good time! To see the list of commands, type /help",
            color: "#FFFFFF"
        });
        initCanvas();
    };

    ws.onmessage = function (e) {
        receiveFromServer(e.data);
    };

    ws.onclose = function() {
        connected = false;
        write("close");
    };
    
    /*
     * CANVAS FUNCTIONS
     * */
    
    var canvas = false;
    var ctx = false;
    window.requestAnimFrame = (function(){
        return  window.requestAnimationFrame       ||
                window.webkitRequestAnimationFrame ||
                window.mozRequestAnimationFrame    ||
                function( callback ){
                  window.setTimeout(callback, 1000 / 60);
                };
    })();

    
    function initCanvas() {
        canvas = document.getElementById("canvas");
        canvas.width = 800;
        canvas.height = 600;
        ctx = canvas.getContext("2d");
        refreshCanvas();
    }
    /*
    objects[objects.length] = {
        x:50,
        y:50,
        size:30,
        shield:2,
        color:"#F00",
        shape:"circle",
    };
    objects[objects.length] = {
        x:50,
        y:100,
        size:20,
        shield:0,
        color:"#F0F",
        shape:"square",
    };
    */
    function refreshCanvas() {
        ctx.clearRect(0,0,800,600);
        for( var i in gameObjects ) {
            var x = gameObjects[i]['x'];
            var y = gameObjects[i]['y'];
            var size = gameObjects[i]['size'];
            //var shield = gameObjects[i]['shield'];
            //var shape = gameObjects[i]['shape'];
            var color = gameObjects[i]['color'];
            
            ctx.beginPath();
            ctx.lineWidth = 1;
            ctx.fillStyle = color;
            ctx.arc(x, y, size, 0, 2*Math.PI, false);
            ctx.fill();
            ctx.strokeStyle = '#003300';
            ctx.stroke();
            /*
            ctx.beginPath();
            ctx.moveTo(x,y);
            ctx.lineTo(angle...);
            ctx.stroke();
            */
        }
        requestAnimFrame(refreshCanvas);
    }
    
});