// some pre-defined styles for jsplumb
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
    endpoint: ["Dot", { radius: 7 }],  // endpoint's style
    connectorStyle: connectorPaintStyle,//connector's color, size or style
    connectorHoverStyle: connectorHoverStyle,
    paintStyle: {
        strokeStyle: "#1e8151",
        fillStyle: "transparent",
        radius: 5,
        lineWidth: 2
    },
    isSource: true,    
    connector: ["Straight", { stub: [0, 0], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }], 
    isTarget: true,    
    maxConnections: -1,    
    connectorOverlays:  [
            //[ "Label", {
            //    fillStyle:"rgba(100,100,100,80)",
            //    color:"white",
            //    font:"12px sans-serif",
            //    label:"Static label",
            //    borderStyle:"black",
            //    borderWidth:2
            //}]
    ]

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

      // add endpoints to the element with the id
      jsPlumb.addEndpoint(id, { anchors: "RightMiddle" }, hollowCircle);
      jsPlumb.addEndpoint(id, { anchors: "LeftMiddle" }, hollowCircle);
      jsPlumb.addEndpoint(id, { anchors: "TopCenter" }, hollowCircle);
      jsPlumb.addEndpoint(id, { anchors: "BottomCenter" }, hollowCircle);


      // make the dropped element draggable
      jQuery(id).draggable({
          containment: "parent",
          drag: function (event, ui) {
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

// a function to make scene title draggable instead of the whole scene
var makeSceneTitleDraggable = function(){
  jQuery('.scene>.scene-title').draggable({
    connectToSortable: "#timeline",
    helper:function(){
      return jQuery("<div class='scene_title_helper'></div>").append(jQuery(this).text());
    },
    revert:"invalid"
  });
}