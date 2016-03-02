
var connectorPaintStyle = {
    strokeStyle: "#1e8151",
    fillStyle: "transparent",
    radius: 5,
    lineWidth: 2
};

var connectorHoverStyle = {
    lineWidth: 3,
    strokeStyle: "#216477",
    outlineWidth: 2,
    // outlineColor: "white"
};
var endpointHoverStyle = {
    fillStyle: "#fff",
    strokeStyle: "#fff"
};

var hollowCircle = {
    DragOptions: { cursor: 'pointer', zIndex: 2000 },
    endpoint: ["Dot", { radius: 7 }],  //端点的形状
    connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
    connectorHoverStyle: connectorHoverStyle,
    paintStyle: {
        strokeStyle: "#1e8151",
        fillStyle: "transparent",
        radius: 5,
        lineWidth: 2
    },        
    //anchor: "AutoDefault",
    isSource: true,    
    connector: ["Straight", { stub: [0, 0], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }], 
    isTarget: true,    
    maxConnections: -1,    
    connectorOverlays: [["Arrow", { width: 10, length: 10, location: 1 }]]
};

var hollowSquare = {
    DragOptions: { cursor: 'pointer', zIndex: 2000 },
    endpoint: ["Rectangle", { width: 15, height: 20 }],  //端点的形状
    connectorStyle: connectorPaintStyle,//连接线的颜色，大小样式
    connectorHoverStyle: connectorHoverStyle,
    paintStyle: {
        strokeStyle: "#fff",
        fillStyle: "#fff",
        // radius: 5,
        lineWidth: 2
    },        
    //anchor: "AutoDefault",
    isSource: true,    
    connector: ["Straight", { stub: [0, 0], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }], 
    isTarget: true,    
    maxConnections: -1,    
    // connectorOverlays: [["Arrow", { width: 10, length: 10, location: 1 }]], // set the arrow
    ConnectionOverlays: [
            [ "Label", 
              {
                location: 0.5,
                id: "label",
                cssClass: "aLabel",
              //   events:{tap://function() { alert("hey"); }
              // }
            }]
        ],
};


var num = 0;
var confirmed = false;

var makeSceneDroppable = function(){
  jQuery( ".panel-body" ).droppable({
    accept: ".menu_item",
    tolerance: 'fit',
    out: function (event, ui) {
      var self = ui;
      ui.helper.off('mouseup').on('mouseup', function () {
          jQuery(this).remove();
          self.draggable.remove();
      });
      // alert("ddd");
    },
    drop: function(event, ui) {
      num = num + 1;

      var cloned = jQuery(ui.draggable).clone();

      jQuery(this).append(cloned);

      var item_copied = cloned.addClass('item-copied');
      var id = cloned.attr("id","item"+num);

      item_copied.removeClass("menu_item");

      

      jsPlumb.setContainer(jQuery(".panel-body"));

      jsPlumb.addEndpoint(id, { anchors: "RightMiddle" }, hollowSquare);
      jsPlumb.addEndpoint(id, { anchors: "LeftMiddle" }, hollowSquare);
      // jsPlumb.addEndpoint(id, { anchors: "TopCenter" }, hollowCircle);
      // jsPlumb.addEndpoint(id, { anchors: "BottomCenter" }, hollowCircle);

      // jsPlumb.getOverlay("label").setLabel(connection.sourceId.substring(15) + "-" + connection.targetId.substring(15));
      // init = function (connection) {
      //       connection.getOverlay("label").setLabel(connection.sourceId.substring(15) + "-" + connection.targetId.substring(15));
      //   };
      // jsPlumb.bind("connection", function (connInfo, originalEvent) {
      //       init(connInfo.connection);
      //   });

      jsPlumb.bind("click", function (conn, originalEvent) {
        
        if (!confirmed && confirm("Delete connection between " + conn.sourceId + " and " + conn.targetId + "?")){
          this.detach(conn);
        }
        confirmed = true;
        
      });

      jQuery(id).draggable({
          // containment: "parent",
          // containment: "parent",
          start: function () {
              // startMove();
          },
          drag: function (event, ui) {
              // MoveSelectDiv(event, ui, id);
              jsPlumb.repaintEverything();
          },
          stop: function () {
              jsPlumb.repaintEverything();
          },
      });
            
    },
    activate: function( event, ui ) {

    },
    deactivate: function( event, ui ) {
    },

  });
};

var makeSceneTitleDraggable = function(){
  jQuery('.scene>.scene-title').draggable({
    containment: "#content",
    connectToSortable: "#timeline",
    helper:function(){
      return jQuery("<div class='scene_title_helper'></div>").append(jQuery(this).text());
    },
    revert:"invalid"
  });
}