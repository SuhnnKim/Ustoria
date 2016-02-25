
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
    fillStyle: "#216477",
    strokeStyle: "#216477"
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


var num = 0;

var makeSceneDroppable = function(){
  jQuery( ".panel-body" ).droppable({
    accept: ".menu_item",
    tolerance: 'fit',
    drop: function(event, ui) {
      num = num + 1;

      var cloned = jQuery(ui.draggable).clone();

      jQuery(this).append(cloned);

      cloned.addClass('item-copied');
      var id = cloned.attr("id","item"+num);

      jQuery(".item-copied").removeClass("menu_item");
      

      jsPlumb.setContainer(jQuery(".panel-body"));

      jsPlumb.addEndpoint(id, { anchors: "RightMiddle" }, hollowCircle);
      jsPlumb.addEndpoint(id, { anchors: "LeftMiddle" }, hollowCircle);
      jsPlumb.addEndpoint(id, { anchors: "TopCenter" }, hollowCircle);
      jsPlumb.addEndpoint(id, { anchors: "BottomCenter" }, hollowCircle);

      jQuery(id).draggable({
          containment: "parent",
          start: function () {
              // startMove();
          },
          drag: function (event, ui) {
              // MoveSelectDiv(event, ui, id);
              jsPlumb.repaintEverything();
          },
          stop: function () {
              jsPlumb.repaintEverything();
          }
      });
            
    },
    activate: function( event, ui ) {
    },
    deactivate: function( event, ui ) {
    }
  });
};

var makeSceneTitleDraggable = function(){
  jQuery('.scene>.scene-title').draggable({
    // containment: "document",
    connectToSortable: "#timeline",
    helper:function(){
      return jQuery("<div class='scene_title_helper'></div>").append(jQuery(this).text());
    },
    revert:"invalid"
  });
}