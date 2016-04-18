<%--
  Created by IntelliJ IDEA.
  User: Zhipe
  Date: 2016-04-18
  Time: 5:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="css/jquery-ui.css">
</head>
<body>
<div style="float: right; ">
    <div id="dialog_box" style="width: 120px; height: 100px; background-color: #ff6666; line-height: 100px; text-align: center;z-index: 999;">Question</div>
    <div id="statement_box" style="width: 90px; height: 75px; background-color: #6699ff; line-height: 75px; text-align: center;z-index: 999;">statement</div>
    <button id="zoomIn">+</button>
    <button id="zoomOut">-</button>
    <table>
        <tr>
            <td></td>
            <td><button id="toTop">w</button></td>
            <td></td>
        </tr>
        <tr>
            <td><button id="toLeft">a</button></td>
            <td><button id="toBottom">s</button></td>
            <td><button id="toRight">d</button></td>
        </tr>
    </table>
    <label><input type="checkbox" id="connect">connect</label>
    <label><input type="checkbox" id="move">move</label>
    <p>double click rectangle<br> to change the content</p>
    <p><button type="button" id="delete" >DELETE </button></p>
    <p><button type="button" id="toJSON" >toJSON </button></p>
    <p><button type="button" id="loadJSON" >loadJSON </button></p>
    <!-- <p><button type="button" id="test" >test </button></p> -->
</div>
<div id="prompt" style="display: none;">
    <p>Please specify the speaker</p>
    <select id="speaker">
        <option>===callout===</option>
        <option>Diana</option>
        <option>Prannoy</option>
    </select>
    <p>Please enter the dialog/question</p>
    <textarea id="content" cols="30"></textarea>
</div>
<div id="canvas_wrap" style="float: left;"><canvas id="dialog_canvas" width="800" height="600"></canvas></div>


<script type="text/javascript" src="js/jquery-2.2.0.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/fabric.origin.js"></script>
<script type="text/javascript" src="js/fabric.canvasex.js"></script>
<script type="text/javascript" src="js/fabric.myext.js"></script>

<script type="text/javascript">


    var canvas = this.__canvas = new fabric.CanvasEx('dialog_canvas');

    var myDialogMap = new Map();
    var connection_pool = [];

    // setting the background image of canvas
    canvas.setBackgroundImage('img/grid.svg', canvas.renderAll.bind(canvas), {
        // Needed to position backgroundImage at 0/0
        originX: 'left',
        originY: 'top'
    });

    // make the #dialog_box draggable
    $( "#dialog_box" ).draggable({
        helper:'clone'
    });

    $( "#statement_box" ).draggable({
        helper:'clone'
    });

    $('#test').on('click',function(){
        var id = createUUID();
        var zline = new fabric.Zline([100, 100, 200, 200], {
            id: id,
            fill: '#ff6666',
            stroke: '#ff6666',
            strokeWidth: 2,
            selectable: false,
            perPixelTargetFind: true,
            sourceRectId: '111',
            targetRectId: '222'
        });
        console.log(id);
        zline.setCoords();
        canvas.add(zline);
        canvas.renderAll();
        console.log('added');
        var jsonObj = JSON.stringify(canvas);
        console.log(jsonObj);

        canvas.clear();
        console.log(canvas.getObjects().length);

        canvas.loadFromJSON(jsonObj, canvas.renderAll.bind(canvas), function(o, object) {
            fabric.log(o, object);
        });
        // canvas.loadFromJSON(jsonObj,canvas.renderAll.bind(canvas));
        canvas.renderAll();
        console.log(canvas.getObjects().length);
        console.log(canvas.getObjects()[0]);
        // console.log('added again');
    });

    $('#toJSON').on('click',function(){
        console.log(JSON.stringify(canvas.toDatalessJSON()));
    });

    $('#loadJSON').on('click',function(){

        myDialogMap = new Map();
        connection_pool = [];

        var array = [];

        canvas.loadFromJSON(json, canvas.renderAll.bind(canvas), function(o, object) {
            // fabric.log(o, object);
            array.push(object);
        });

        canvas.clear();


        // fabric.util.enlivenObjects(array, function(objects) {

        array.forEach(function(o) {

            if(o.get('type') === 'zrect'){

                myDialogMap.set(o.id, o);

                // convert json object to klass object (content)
                var klass = fabric.util.getKlass(o.text.type);
                var str = o.text.text;
                var newText = klass.fromObject(o.text);
                o.text = newText;
                o.text.text = str;
                klass = null;

                // convert json object to klass object (speaker)
                var klass2 = fabric.util.getKlass(o.speaker.type);
                var str = o.speaker.text;
                var newSpeaker = klass2.fromObject(o.speaker);
                o.speaker = newSpeaker;
                o.speaker.text = str;
                klass2 = null;


                var linePair = [];
                var shouldPush = true;

                if(o.connections_I_am_source){
                    fabric.util.enlivenObjects(o.connections_I_am_source, function(lines){
                        o.connections_I_am_source = [];
                        lines.forEach(function(line) {


                            for(var i = 0; i < connection_pool.length; i++){
                                if(connection_pool[i].sourceID === line.sourceID){
                                    if(connection_pool[i].targetID === line.targetID){
                                        shouldPush = false;
                                        break;
                                    }
                                }
                            }
                            if(shouldPush){
                                connection_pool.push(line);
                            }


                            klass = fabric.util.getKlass(line.arrow.type);
                            var newArrow = klass.fromObject(line.arrow);
                            line.arrow = newArrow;
                            o.connections_I_am_source.push(line);
                            klass = null;


                        });
                    });
                }
                if(o.connections_I_am_target){
                    fabric.util.enlivenObjects(o.connections_I_am_target, function(lines){
                        o.connections_I_am_target = [];
                        lines.forEach(function(line) {

                            for(var i = 0; i < connection_pool.length; i++){
                                if(connection_pool[i].sourceID === line.sourceID){
                                    if(connection_pool[i].targetID === line.targetID){
                                        shouldPush = false;
                                        break;
                                    }
                                }
                            }
                            if(shouldPush){
                                connection_pool.push(line);
                            }

                            klass = fabric.util.getKlass(line.arrow.type);
                            var newArrow = klass.fromObject(line.arrow);
                            line.arrow = newArrow;
                            o.connections_I_am_target.push(line);
                            klass = null;

                        });
                    });
                }
            }
        });

        // connection_pool = uniques(connection_pool);

        renderMyCanvas();
        console.log(connection_pool);
        // });
    });
    // unique the array
    // function uniques(arr) {
    //     var a = [];
    //     for (var i=0, l=arr.length; i<l; i++)
    //         if (a.indexOf(arr[i]) === -1 && arr[i] !== '')
    //             a.push(arr[i]);
    //     return a;
    // }

    $('#delete').on('click',function(){

        var obj,
                line,
                i,
                j,
                index,
                sourceID,
                targetID,
                source_obj,
                target_obj,
                arr;

        if(canvas.getActiveObject()){
            obj = canvas.getActiveObject();

            // only apply delete function to Rectangle object
            if(obj.get('type') === 'zrect'){

                // delete all the connections which begin from this object
                if(obj.connections_I_am_source.length > 0){
                    for(i=0; i < obj.connections_I_am_source.length; i++){

                        var sourceLine = obj.connections_I_am_source[i];

                        // delete the reference from target object
                        targetID = sourceLine.targetID;
                        arr = myDialogMap.get(targetID).connections_I_am_target;
                        arr.splice(arr.indexOf(sourceLine), 1);

                        index = connection_pool.indexOf(sourceLine);
                        connection_pool.splice(index, 1);
                        obj.connections_I_am_source.splice(i, 1);
                        // sourceLine.arrow.remove();
                        // sourceLine.remove();
                        sourceLine = index = arr = null;
                        console.log('removed a source connection');
                    }
                }
                // delete all the connections which end to this object
                if(obj.connections_I_am_target.length > 0){
                    for(j=0; j < obj.connections_I_am_target.length; j++){

                        var targetLine = obj.connections_I_am_target[j];

                        sourceID = targetLine.sourceID;
                        arr = myDialogMap.get(sourceID).connections_I_am_source;
                        arr.splice(arr.indexOf(targetLine), 1);

                        index = connection_pool.indexOf(targetLine);
                        connection_pool.splice(index, 1);
                        obj.connections_I_am_target.splice(j, 1);
                        // targetLine.arrow.remove();
                        // targetLine.remove();
                        targetLine = index = arr = null;
                        console.log('removed a target connection');
                    }
                }

                // obj.text.remove();
                obj.remove();
                myDialogMap.delete(obj.id);

                obj = line = i = j = index = arr = null;

                console.log('removed');
                renderMyCanvas();
            }
        }

    });

    // make the #dialog_canvas droppable
    $( "#dialog_canvas" ).droppable({
        accept: "#dialog_box, #statement_box",
        drop: function( event, ui ) {

            if (!$("#prompt").dialog ("isOpen")){
                $('#prompt').data('toCreate', 'toCreate')
                        .data('pointerX', canvas.getPointer(event.e).x)
                        .data('pointerY', canvas.getPointer(event.e).y)
                        .data('typeId', ui.draggable.attr('id'))
                        .dialog("open");
            }
        }
    });

    // function of creating a random uuid for objects
    var createUUID = function(){
        return  'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    };

    var createDialog = function(speaker, content, pointerX, pointerY, typeId){
        var uuid = createUUID();
        console.log('break point');
        // creating the new Textbox for dialog
        // Textbox is a subclass of IText in fabric
        var contentText = new fabric.Textbox(
                content,
                {
                    left: pointerX,
                    top: pointerY,
                    originX: "center",
                    originY: "center",
                    fontSize: 15,
                    width: 100,
                    height: 30,
                    selectable: false,
                    backgroundColor: '#ff6666',
                    textAlign: 'center',
                    lockMovementX: true,
                    lockMovementY: true,
                    lockRotation: true
                }
        );

        // creating the new Textbox for speaker
        var speakerText = new fabric.Textbox(
                speaker,
                {
                    left: pointerX,
                    top: pointerY-50,
                    originX: "center",
                    originY: "center",
                    fontSize: 15,
                    width: 60,
                    height: 20,
                    selectable: false,
                    hasBorders: false,
                    hasControls: false,
                    lockRotation: true,
                    backgroundColor: '#ff6666',
                    textAlign: 'center',
                    lockMovementX: true,
                    lockMovementY: true,
                    lockRotation: true
                }
        );

        // creating the dialog rectangle box
        var newRect = new fabric.Zrect({
            id: uuid,
            boxType: "dialog",
            text: contentText,
            speaker: speakerText,
            top: pointerY,
            left: pointerX,
            width: 120,
            height:100,
            fill: '#ff6666',
            originX: "center",
            originY: "center",
            lockRotation: true,
            connections_I_am_source: [],
            connections_I_am_target: []
        });

        if(typeId === 'statement_box'){
            contentText.set({
                width: 60,
                height: 50,
                backgroundColor: '#6699ff'
            });
            speakerText.set({
                top: pointerY-37.5,
                backgroundColor: '#6699ff'
            });
            newRect.set({
                width: 90,
                height: 75,
                fill: '#6699ff',
                boxType: "option"
            });
        }
        myDialogMap.set(uuid, newRect);
        canvas.add(newRect);
        canvas.add(speakerText);
        canvas.add(contentText);
    };

    // canvas listening on events
    canvas.on({
        'object:moving': function(e) {
            var p = e.target;
            if(p.get('type') == "zrect"){

                if(p.text && p.speaker){
                    p.text.set({
                        'left': p.left,
                        'top': p.top
                    });
                    p.speaker.set({
                        'left': p.left,
                        'top': p.top-p.height/2,
                    });
                }


                for(var index = 0; index < connection_pool.length; index++){

                    var thisLine = connection_pool[index];
                    var o_1 = myDialogMap.get(thisLine.sourceID);
                    var o_2 = myDialogMap.get(thisLine.targetID);

                    thisLine.set({
                        x1: o_1.left,
                        y1: o_1.top,
                        x2: o_2.left,
                        y2: o_2.top
                    });

                    var x1, x2, y1, y2;
                    x1 = thisLine.get('x1');
                    y1 = thisLine.get('y1');
                    x2 = thisLine.get('x2');
                    y2 = thisLine.get('y2');

                    var angle = calcArrowAngle(x1, y1, x2, y2);

                    thisLine.arrow.set({
                        'angle': angle + 90,
                        'top': calcArrowPositionY(o_1.top, o_2.top),
                        'left': calcArrowPositionX(o_1.left, o_2.left)
                    });

                    thisLine.setCoords();
                }
            }
            canvas.renderAll();
        },
        'object:modified': function(e) {
            e.target.opacity = 1;
            // renderMyCanvas();
        },
        'mouse:dblclick': function(e){
            var p = e.target;
            if(canvas.getActiveObject()){
                if(p.get('type') == "zrect" || p.get('type') == "textbox"){
                    // var newContent = prompt("Please enter the dialog/question", p.text.text);
                    //   	p.text.text = newContent || p.text.text;
                    $("#content").val(canvas.getActiveObject().text.text);
                    $("#prompt").data('activeObj', canvas.getActiveObject()).dialog("open");
                    renderMyCanvas();
                }else{
                    console.log(p.get('type'));
                }
            }
        },
        'mouse:over': function(e){
            // e.target.setOpacity(0.8);
            // e.target.setFill("#ff5555");
            // canvas.renderAll();
        },
        'mouse:out': function(e){
            // e.target.setOpacity(1);
            // e.target.setFill("#ff6666");
            // canvas.renderAll();
        },
        'object:selected': function(e){
            console.log(e.target.type);
            // console.log(e.target.connections_I_am_source.length + ", " + e.target.connections_I_am_target.length);
        }
    });



    var myIdPair = [];
    var myBoxTypePair = [];

    // trigger event for #connect checkbox
    $('#connect').change(
            function(){
                console.log("connecting mode: " + $('#connect').prop('checked'));

                if($('#connect').prop('checked')){
                    canvas.on('mouse:down', function (e) {
                        var obj;

                        if(canvas.getActiveObject()){
                            obj = canvas.getActiveObject();
                            if(obj.get('type') == 'zrect'){
                                myIdPair.push(obj.id);
                                myBoxTypePair.push(obj.boxType);
                            }
                            obj = null;
                        }else{
                            myIdPair = [];
                            myBoxTypePair = [];
                            console.log("empty");
                        }
                        if(myIdPair.length == 2){
                            if(myIdPair[0] != myIdPair[1] && (myBoxTypePair[0] != 'option' || myBoxTypePair[1] != 'option')){
                                var obj_source = myDialogMap.get(myIdPair[0]);
                                var obj_target = myDialogMap.get(myIdPair[1]);

                                var line = makeLine(
                                        [ obj_source.left, obj_source.top, obj_target.left, obj_target.top ],
                                        myIdPair[0],
                                        myIdPair[1]
                                );

                                line.arrow = getNewArrow(line);
                                connection_pool.push(line);
                                obj_source.connections_I_am_source.push(line);
                                obj_target.connections_I_am_target.push(line);
                                myIdPair = [];
                                myBoxTypePair = [];
                                canvas.deactivateAll().renderAll();
                            }else{
                                myIdPair.pop();
                                myBoxTypePair.pop();
                                console.log("pop");
                            }
                        };

                        renderMyCanvas();
                    });
                }else{
                    // need to clean the events if you are not in connecting mode
                    canvas.__eventListeners["mouse:down"] = [];
                };
            }
    );

    // trigger event for #move checkbox
    $('#move').change(
            function(){

                console.log("moving mode: " + $('#move').prop('checked'));

                if($('#move').prop('checked')){
                    var panning = false;
                    canvas.selection = false;
                    canvas.on('mouse:up', function (e) {
                        panning = false;
                    });

                    canvas.on('mouse:down', function (e) {
                        panning = true;
                    });
                    canvas.on('mouse:move', function (e) {
                        if (panning && e && e.e) {
                            var units = 10;
                            var delta = new fabric.Point(e.e.movementX, e.e.movementY);
                            canvas.relativePan(delta);
                        }
                    });
                }else{
                    // need to clean the events if you are not in moving mode
                    canvas.__eventListeners["mouse:up"] = [];
                    canvas.__eventListeners["mouse:down"] = [];
                    canvas.__eventListeners["mouse:move"] = [];
                    canvas.selection = true;
                }
            }
    );

    // function of creating a new arrow(which is a triangle in fabric) object for accociated line
    // parameter line is the accociated line which holds the arrow
    var getNewArrow = function(line){
        var centerX = (line.x1 + line.x2) / 2,
                centerY = (line.y1 + line.y2) / 2;
        deltaX = line.left - centerX,
                deltaY = line.top - centerY;
        var r = new fabric.Triangle({
            left: line.get('x2'),
            top: line.get('y2'),
            originX: 'center',
            originY: 'center',
            hasBorders: false,
            hasControls: false,
            lockScalingX: true,
            lockScalingY: true,
            lockRotation: true,
            pointType: 'arrow_start',
            angle: -45,
            width: 15,
            height: 15/2*Math.sqrt(3),
            fill: '#000'
        });
        return r;
    };


    // calculate the angle of arrow
    var calcArrowAngle = function(x1, y1, x2, y2) {
        var angle = 0,
                x, y;

        x = (x2 - x1);
        y = (y2 - y1);

        if (x === 0) {
            angle = (y === 0) ? 0 : (y > 0) ? Math.PI / 2 : Math.PI * 3 / 2;
        } else if (y === 0) {
            angle = (x > 0) ? 0 : Math.PI;
        } else {
            angle = (x < 0) ? Math.atan(y / x) + Math.PI : (y < 0) ? Math.atan(y / x) + (2 * Math.PI) : Math.atan(y / x);
        }

        return (angle * 180 / Math.PI);
    };

    // calculate the X position of arrow
    var calcArrowPositionX = function(x1, x2){
        return (x2 - x1) >= 0 ? (x2 - x1)/2 + x1 : (x1 - x2)/2 + x2;
    };

    // calculate the Y position of arrow
    var calcArrowPositionY = function(y1, y2){
        return (y2 - y1) >= 0 ? (y2 - y1)/2 + y1 : (y1 - y2)/2 + y2;
    };

    // pop up propmt for changing the content of dialog/question
    $("#prompt").dialog({
        autoOpen: false,
        show : "fadeIn",
        hide : "puff",
        buttons: {
            Ok: function() {

                if($(this).data('toCreate') === 'toCreate'){

                    var speaker = $('#speaker').find(":selected").text();
                    var content = $("#content").val();

                    var pointerX = $(this).data('pointerX');
                    var pointerY = $(this).data('pointerY');
                    var typeId = $(this).data('typeId');

                    console.log(content.text);

                    createDialog(speaker, content, pointerX, pointerY, typeId);

                    $("#content").val("");


                    console.log('done');
                }else{
                    if($(this).data('activeObj')){
                        $(this).data('activeObj').text.text = $("#content").val();
                        $(this).data('activeObj').speaker.text = $('#speaker').find(":selected").text();
                        canvas.renderAll();
                    }
                }
                $(this).dialog("close");
            },
            Cancel: function () {
                $(this).dialog("close");
            }
        },
        beforeClose: function( event, ui ) {
            $('#speaker').prop('selectedIndex',0);
            $(this).data('toCreate', 'doneCreate');
        }
    });

    // functions of buttons, zoom in/out, pan to left/right/top/bottom
    $('#zoomIn').click(function(){
        canvas.setZoom(canvas.getZoom() * 1.1 ) ;
    }) ;

    $('#zoomOut').click(function(){
        canvas.setZoom(canvas.getZoom() / 1.1 ) ;
    }) ;

    $('#toRight').click(function(){
        var units = 30 ;
        var delta = new fabric.Point(units,0) ;
        canvas.relativePan(delta) ;
    }) ;

    $('#toLeft').click(function(){
        var units = 30 ;
        var delta = new fabric.Point(-units,0) ;
        canvas.relativePan(delta) ;
    }) ;
    $('#toTop').click(function(){
        var units = 30 ;
        var delta = new fabric.Point(0,-units) ;
        canvas.relativePan(delta) ;
    }) ;

    $('#toBottom').click(function(){
        var units = 30 ;
        var delta = new fabric.Point(0,units) ;
        canvas.relativePan(delta) ;
    }) ;

    // function of creating a line object of fabric
    // parameter coords is an array representing [x1, y1, x2, y2]
    // (x1, y1) is the one point, (x2, y2) is another
    function makeLine(coords, sourceID, targetID) {
        var l = new fabric.Zline(coords, {
            fill: '#ff6666',
            stroke: '#ff6666',
            strokeWidth: 2,
            selectable: false,
            hasControls: false,
            perPixelTargetFind: true,
            sourceID: sourceID,
            targetID: targetID,
            arrow: {}
        });
        l.setCoords();
        return l;
    }

    // function of rendering all objects stored in connection_pool and myDialogMap
    // need to store connection_pool and myDialogMap to backend if you want data persistence
    var renderMyCanvas = function(){
        canvas.clear();

        var i;

        for(i=0; i < connection_pool.length; i++){

            canvas.add(connection_pool[i]);
            canvas.add(connection_pool[i].arrow)
        }

        for (var [key, value] of myDialogMap) {

            canvas.add(value);

            if(value.text){
                canvas.add(value.text);
            }

            if(value.speaker){
                canvas.add(value.speaker);
            }
        }

        canvas.renderAll();
    };

    var json = {"objects":[{"type":"zline","originX":"left","originY":"top","left":210,"top":147,"width":253,"height":8,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":-126.5,"x2":126.5,"y1":4,"y2":-4,"sourceID":"7a945ec8-92f9-4404-83c5-646647f6e452","targetID":"c82f1245-dc28-429b-a9d6-19f1a8c0b08b","arrow":{"type":"triangle","originX":"center","originY":"center","left":336.5,"top":151,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":88.19,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}},{"type":"triangle","originX":"center","originY":"center","left":336.5,"top":151,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":88.19,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0},{"type":"zline","originX":"left","originY":"top","left":214,"top":147,"width":249,"height":217,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":124.5,"x2":-124.5,"y1":-108.5,"y2":108.5,"sourceID":"c82f1245-dc28-429b-a9d6-19f1a8c0b08b","targetID":"c0795185-7aba-4b78-a748-37ed776c8d1f","arrow":{"type":"triangle","originX":"center","originY":"center","left":338.5,"top":255.5,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":-131.07,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}},{"type":"triangle","originX":"center","originY":"center","left":338.5,"top":255.5,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":-131.07,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0},{"type":"zline","originX":"left","originY":"top","left":214,"top":364,"width":256,"height":8,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":-128,"x2":128,"y1":-4,"y2":4,"sourceID":"c0795185-7aba-4b78-a748-37ed776c8d1f","targetID":"247f6300-a374-4d0c-a02f-09d930cf89f7","arrow":{"type":"triangle","originX":"center","originY":"center","left":342,"top":368,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":91.79,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}},{"type":"triangle","originX":"center","originY":"center","left":342,"top":368,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":91.79,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0},{"type":"zrect","originX":"center","originY":"center","left":210,"top":155,"width":120,"height":100,"fill":"#ff6666","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"rx":0,"ry":0,"id":"7a945ec8-92f9-4404-83c5-646647f6e452","boxType":"dialog","text":{"type":"textbox","originX":"center","originY":"center","left":210,"top":155,"width":100,"height":39.32,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#ff6666","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"what did you eat?","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},"speaker":{"type":"textbox","originX":"center","originY":"center","left":210,"top":105,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#ff6666","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"Diana","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},"connections_I_am_source":[{"type":"zline","originX":"left","originY":"top","left":210,"top":147,"width":253,"height":8,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":-126.5,"x2":126.5,"y1":4,"y2":-4,"sourceID":"7a945ec8-92f9-4404-83c5-646647f6e452","targetID":"c82f1245-dc28-429b-a9d6-19f1a8c0b08b","arrow":{"type":"triangle","originX":"center","originY":"center","left":336.5,"top":151,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":88.19,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}}],"connections_I_am_target":[]},{"type":"textbox","originX":"center","originY":"center","left":210,"top":155,"width":100,"height":39.32,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#ff6666","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"what did you eat?","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},{"type":"textbox","originX":"center","originY":"center","left":210,"top":105,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#ff6666","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"Diana","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},{"type":"zrect","originX":"center","originY":"center","left":463,"top":147,"width":90,"height":75,"fill":"#6699ff","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"rx":0,"ry":0,"id":"c82f1245-dc28-429b-a9d6-19f1a8c0b08b","boxType":"option","text":{"type":"textbox","originX":"center","originY":"center","left":463,"top":147,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#6699ff","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"apple","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},"speaker":{"type":"textbox","originX":"center","originY":"center","left":463,"top":109.5,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#6699ff","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"Prannoy","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},"connections_I_am_source":[{"type":"zline","originX":"left","originY":"top","left":214,"top":147,"width":249,"height":217,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":124.5,"x2":-124.5,"y1":-108.5,"y2":108.5,"sourceID":"c82f1245-dc28-429b-a9d6-19f1a8c0b08b","targetID":"c0795185-7aba-4b78-a748-37ed776c8d1f","arrow":{"type":"triangle","originX":"center","originY":"center","left":338.5,"top":255.5,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":-131.07,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}}],"connections_I_am_target":[{"type":"zline","originX":"left","originY":"top","left":210,"top":147,"width":253,"height":8,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":-126.5,"x2":126.5,"y1":4,"y2":-4,"sourceID":"7a945ec8-92f9-4404-83c5-646647f6e452","targetID":"c82f1245-dc28-429b-a9d6-19f1a8c0b08b","arrow":{"type":"triangle","originX":"center","originY":"center","left":336.5,"top":151,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":88.19,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}}]},{"type":"textbox","originX":"center","originY":"center","left":463,"top":147,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#6699ff","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"apple","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},{"type":"textbox","originX":"center","originY":"center","left":463,"top":109.5,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#6699ff","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"Prannoy","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},{"type":"zrect","originX":"center","originY":"center","left":214,"top":364,"width":120,"height":100,"fill":"#ff6666","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"rx":0,"ry":0,"id":"c0795185-7aba-4b78-a748-37ed776c8d1f","boxType":"dialog","text":{"type":"textbox","originX":"center","originY":"center","left":214,"top":364,"width":100,"height":39.32,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#ff6666","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"what about you?","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},"speaker":{"type":"textbox","originX":"center","originY":"center","left":214,"top":314,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#ff6666","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"Prannoy","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},"connections_I_am_source":[{"type":"zline","originX":"left","originY":"top","left":214,"top":364,"width":256,"height":8,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":-128,"x2":128,"y1":-4,"y2":4,"sourceID":"c0795185-7aba-4b78-a748-37ed776c8d1f","targetID":"247f6300-a374-4d0c-a02f-09d930cf89f7","arrow":{"type":"triangle","originX":"center","originY":"center","left":342,"top":368,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":91.79,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}}],"connections_I_am_target":[{"type":"zline","originX":"left","originY":"top","left":214,"top":147,"width":249,"height":217,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":124.5,"x2":-124.5,"y1":-108.5,"y2":108.5,"sourceID":"c82f1245-dc28-429b-a9d6-19f1a8c0b08b","targetID":"c0795185-7aba-4b78-a748-37ed776c8d1f","arrow":{"type":"triangle","originX":"center","originY":"center","left":338.5,"top":255.5,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":-131.07,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}}]},{"type":"textbox","originX":"center","originY":"center","left":214,"top":364,"width":100,"height":39.32,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#ff6666","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"what about you?","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},{"type":"textbox","originX":"center","originY":"center","left":214,"top":314,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#ff6666","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"Prannoy","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},{"type":"zrect","originX":"center","originY":"center","left":470,"top":372,"width":90,"height":75,"fill":"#6699ff","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"rx":0,"ry":0,"id":"247f6300-a374-4d0c-a02f-09d930cf89f7","boxType":"option","text":{"type":"textbox","originX":"center","originY":"center","left":470,"top":372,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#6699ff","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"banana","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},"speaker":{"type":"textbox","originX":"center","originY":"center","left":470,"top":334.5,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#6699ff","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"Diana","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},"connections_I_am_source":[],"connections_I_am_target":[{"type":"zline","originX":"left","originY":"top","left":214,"top":364,"width":256,"height":8,"fill":"#ff6666","stroke":"#ff6666","strokeWidth":2,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"x1":-128,"x2":128,"y1":-4,"y2":4,"sourceID":"c0795185-7aba-4b78-a748-37ed776c8d1f","targetID":"247f6300-a374-4d0c-a02f-09d930cf89f7","arrow":{"type":"triangle","originX":"center","originY":"center","left":342,"top":368,"width":15,"height":12.99,"fill":"#000","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":91.79,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0}}]},{"type":"textbox","originX":"center","originY":"center","left":470,"top":372,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#6699ff","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"banana","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20},{"type":"textbox","originX":"center","originY":"center","left":470,"top":334.5,"width":60,"height":19.66,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":1,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"#6699ff","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"text":"Diana","fontSize":15,"fontWeight":"normal","fontFamily":"Times New Roman","fontStyle":"","lineHeight":1.16,"textDecoration":"","textAlign":"center","textBackgroundColor":"","styles":{},"minWidth":20}],"background":"","backgroundImage":{"type":"image","originX":"left","originY":"top","left":0,"top":0,"width":1600,"height":1000,"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":0,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"src":"file:///C:/Users/Zhipe/Desktop/resource/img/grid.svg","filters":[],"crossOrigin":"","alignX":"none","alignY":"none","meetOrSlice":"meet"}};



</script>
</body>
</html>