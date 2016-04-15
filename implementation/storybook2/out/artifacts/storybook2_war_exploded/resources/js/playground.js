//
// some pre-defined styles for jsplumb plugin
//
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
  connectorOverlays:  []
};

// dropped_item_counter is for makeSceneDroppable function
var dropped_item_counter = 0;

// a function to make scene body droppable
var makeSceneDroppable = function(){
  jQuery( ".panel-body" ).droppable({
    accept: ".menu_item",
    tolerance: 'fit',
    drop: function(event, ui) {

      dropped_item_counter = dropped_item_counter + 1;

      var cloned = jQuery(ui.draggable).clone();

      jQuery(this).append(cloned);

      cloned.addClass('item-copied');

      var dropped_item_new_id = cloned.attr("id","item"+dropped_item_counter);

      jQuery(".item-copied").removeClass("menu_item");

      //console.log(jQuery(dropped_item_new_id).parent());


      jsPlumb.setContainer(jQuery('.panel-body'));

      // add endpoints to the element with the id
      jsPlumb.addEndpoint(dropped_item_new_id, { anchors: "RightMiddle" }, hollowCircle);
      jsPlumb.addEndpoint(dropped_item_new_id, { anchors: "LeftMiddle" }, hollowCircle);
      jsPlumb.addEndpoint(dropped_item_new_id, { anchors: "TopCenter" }, hollowCircle);
      jsPlumb.addEndpoint(dropped_item_new_id, { anchors: "BottomCenter" }, hollowCircle);


      // make the dropped element draggable
      jQuery(dropped_item_new_id).draggable({
        containment: "parent",
        drag: function (event, ui) {
          jsPlumb.repaintEverything();
        },
        stop: function () {
          //jsPlumb.repaintEverything();
        }
      });

    },
    activate: function( event, ui ) {
      // do some actions when draggble element is dragged
      jQuery('.drop-panel').css('opacity', '0.5');
    },
    deactivate: function( event, ui ) {
      // do some actions when draggble element is dropped
      jQuery('.drop-panel').css('opacity', '1');
    }
  });
};

// a function to make scene title draggable instead of the whole scene

var makeSceneTitleDraggable = function(){
  jQuery('.scene>.scene-title').draggable({
    helper: "clone",
    drag: function (event, ui) {
      dragdataobjtype = jQuery(this).text().trim();
      selectAllObjects(false);
    }
  });

  // jQuery('.scene>.scene-title').draggable({
  //   connectToSortable: "#timeline",
  //   helper:function(){
  //     return jQuery("<div class='scene_title_helper'></div>").append(jQuery(this).text());
  //   },
  //   revert:"invalid"
  // });
};




// a function to make menu item sortable and draggable
var makeMenuItemSortable = function(){
  jQuery(".menu_item_list").sortable({
    // revert:"invalid",
    helper: function (e, li) {
      this.copyHelper = li.clone().insertAfter(li);

      jQuery(this).data('copied', false);

      return li.clone();
    },

    stop: function () {

      var copied = jQuery(this).data('copied');

      if (!copied) {
        this.copyHelper.remove();
      }

      this.copyHelper = null;
    }

  });
};

// mySlySlider is for timelineScrollAnimation
var mySlySlider;
// a function to make timeline scroll behavior nicer
var timelineScrollAnimation = function(){
  var frame  = jQuery('#timeline_wrap');
  var slidee = frame.children('ul').eq(0);
  var wrap   = frame.parent();

  mySlySlider = new Sly(frame, {
    horizontal: 1,
    itemNav: 'basic',
    smart: 1,
    activateOn: 'click',
    mouseDragging: 0,
    touchDragging: 1,
    releaseSwing: 1,
    startAt: 0,
    scrollBar: wrap.find('.scrollbar'),
    scrollBy: 1,
    // pagesBar: wrap.find('.pages'),
    activatePageOn: 'click',
    speed: 300,
    elasticBounds: 1,
    easing: 'easeOutExpo',
    dragHandle: 1,
    dynamicHandle: 1,
    clickBar: 1,
  }).init();
};

var makeTimelineSortable = function(){

  var removeIntent = false;

  jQuery("#timeline").sortable({
    opacity: 0.5,

    over: function (event, ui) {

      removeIntent = false;
    },
    out: function () {
      removeIntent = true;
    },
    beforeStop: function (event, ui) {
      // remove the item dragged out of timeline
      if(removeIntent == true){
        ui.item.remove();
        // reload the timeline slider since item was removed
        mySlySlider.reload();
      }
    },
    update: function (event, ui) {
      // reload the timeline slider since item was added
      mySlySlider.reload();
    },
  });
};

var jsplumbCustomizedBinding = function(){
  // delete the connector of items inside of scenes
  jsPlumb.bind("click", function (conn, originalEvent) {
    if (confirm("Delete connection between " + jQuery('#'+conn.sourceId+" a").text() + " and " + jQuery('#'+conn.targetId+" a").text() + "?")){
      this.detach(conn);
    }
  });
  jsPlumb.bind("connection", function(info) {
    var con = info.connection;
    var relationship = prompt("Please enter the relationship", "");
    if (relationship != null && relationship != '') {
      con.addOverlay(
          ["Label", {
            label: relationship,
            location: 0.5,
            cssClass: 'connectionLabel',
          }]
      );
    }
    console.log("a connection was made from " + info.sourceId + " to " + info.targetId);
  });
};

var makeDragDropSort = function(){
  jQuery(".draggable").draggable({
    helper: "clone"
  });
  jQuery( ".drop-panel" ).droppable({
    activeClass: "ui-state-default",
    hoverClass: "ui-state-hover",
    accept: ":not(.ui-sortable-helper)",
    drop: function( event, ui ) {
      jQuery( this ).find( ".placeholder" ).remove();
      jQuery(ui.draggable).appendTo(this);
    }
  }).sortable({
    items: "li:not(.placeholder)",
    sort: function() {
      jQuery( this ).removeClass( "ui-state-default" );
    }
  });
};


  // the square brackets is for dependency injection
  var app = angular.module('playground', [ ]);

  var scenes = [];

  app.controller('scenePanelController', function($scope){

    $scope.id_counter = 2;
    $scope.scenes = scenes;
  	$scope.scenes.push({title: 'scene1', id: 'scene1'});

    $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
        makeSceneDroppable();
        makeSceneTitleDraggable();
    });

    $scope.removeScene = function(scene){
      var index = $scope.scenes.indexOf(scene);
      var n = index + 1;
      console.log("deleted"+n);
      //var myNode = document.getElementById("scene" + n);
      //while (myNode.firstChild) {
      //  myNode.removeChild(myNode.firstChild);
      //  console.log("deleted")
      //}
      jQuery('#scene' + n).empty();
      $scope.scenes.splice(index, 1);
    };

  });

  // toggle controller for timeline part
  app.controller('TimeLineController', function($scope){
    $scope.timeline = {
      hidden: false,
      toggle: function() {
        this.hidden = !this.hidden;
        timeline_wrapper.hidden = !timeline_wrapper.hidden;
        timeline_hide.hidden = !timeline_hide.hidden;

        // change the fixed height of scene part along with timeline part toggled
        if(this.hidden){
          jQuery('#timeline_panel').height(20);
          jQuery('#scene-panel').height(jQuery('#content').height()-120);
        }else{
          jQuery('#scene-panel').css('height', jQuery('#content').height() * 0.6);
          jQuery('#timeline_panel').css('height', jQuery('#content').height()-jQuery('#scene-panel').height()-50);
        }
      }
    };
  });

  // add scene controller for adding scene dynamically
  app.controller('addSceneController', function($scope){

    var id_counter = 2;
    $scope.addNew = function($title) {

      // validate the title is not empty string
      if($title === ""){
        $scope.error_message = "Please type in your new scene's title.";
        jQuery("#new_scene_title").focus();
        return;
      }

      // push the new scene item to scenes array
      scenes.push({title: $title, id: "scene"+id_counter});

      // hide the modal manually since the default action of bootstrap is removed
      jQuery("#myModal").modal("hide");
      id_counter ++;
      $scope.title = '';
    }

  });


  // enable sidebar menu item toggle animation
  var sidebarAnimate = function(){    
    // in case click event repeated or mixed up with other click event
    jQuery("#cssmenu li.has-sub>a").off("click");
    // rebind the click event
    jQuery('#cssmenu li.has-sub>a').on('click', function(){

      jQuery(this).removeAttr('href');
      var element = jQuery(this).parent('li');


      if (element.hasClass('open')) {
        element.removeClass('open');
        element.find('li').removeClass('open');
        element.find('ul').slideUp(200);
      }
      else {
        element.addClass('open');
        element.children('ul').slideDown(200);
        element.siblings('li').find('li').removeClass('open');
      }
    });
  };



  

  // callback function for np-repeat
  // bind the ngRepeatFinished event with noFinishRender directive
  // reference from http://stackoverflow.com/questions/15207788/calling-a-function-when-ng-repeat-has-finished
  app.directive('onFinishRender', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last) {
                $timeout(function () {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    }
  });



  // no need to use tooltip already since we have titles for scenes
  //jQuery(document).ready(function(){
  //  jQuery('#timeline_panel [data-toggle="tooltip"]').tooltip();
  //});




jQuery(window).resize(function (event) {

  // setting #sidebar's width
  jQuery('#sidebar').css('width', 150);
  // setting #content's width
  jQuery('#content').css('width', jQuery(window).width() - jQuery('#sidebar').width());
  // setting #content's height
  jQuery('#content').css('height', jQuery(window).height() - jQuery('#navbar_playgrond').height());

  jQuery('#content').css('margin-left', jQuery('#sidebar').width());

  jQuery('#play_wrap').css('height', (jQuery('#content').height()-50));

  jQuery('#play_wrap').css('border-bottom', 'solid 1px black');

  jQuery('#scene-panel').css('height', jQuery('#play_wrap').height()*0.6);

  jQuery('#time').css('height', jQuery('#play_wrap').height()-jQuery('#scene-panel').height())

  // jQuery('#time>#timeline').css('width',jQuery('#play_wrap').width());
  // jQuery('#timeline_panel').css('height', jQuery('#play_wrap').height()-jQuery('#scene-panel').height());
  //
  // jQuery('#timeline_wrapper').css('height', jQuery('#timeline_panel').height());

  jQuery('#show-sidebar').css('top', jQuery(window).height() * 0.5);

  jQuery('#hide-sidebar').css({

    'top': jQuery(window).height() * 0.5,
    'left':jQuery('#sidebar').width()-20,

  });
  jQuery('#link-playground').css({

    'bottom': 0,
    'left':0,
    'width': jQuery('#sidebar').width(),

  });
  jQuery('#link-add-category').css({

    'bottom': jQuery('#link-playground').height()*2.5,

  });
});




//jQuery(function(){

  // initialize the width of #content and #timeline
  // jQuery('#content').css('width', jQuery(window).width()-jQuery('#sidebar').width());
  // jQuery('#timeline').width(jQuery('#content').width());

  // jQuery(window).resize();

  // timelineScrollAnimation();

  // makeTimelineSortable();

  // jsplumbCustomizedBinding();

  // makeDragDropSort();
//});

// jQuery("#scene-panel").resizable({
//   handles: "e,s",
//   resize: function (event, ui) {
//     canvas.setHeight(jQuery('#play_wrap').height()-pageY);
//     canvas.renderAll();
//   },
//   alsoResize: "#canvas"
// });

