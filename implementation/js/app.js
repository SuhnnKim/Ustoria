
(function(){

	var app = angular.module('playground', [ ]);

	app.controller('scenePanelController', function($scope){

  	this.scenes = scenes_collection;

    $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
        makeSceneDroppable();
        makeSceneTitleDraggable();
    });
    
  });


  app.controller('addSceneController', function($scope){

    var id_counter = 2;

    $scope.error_message = "Please type in your new scene's title.";

    $scope.scene = {};

    $scope.addNew = function($title) {

      if(!($title === undefined || $title === "")){
        $scope.scene = {title:$title,id:"scene"+id_counter};
      
        scenes_collection.push($scope.scene);

        jQuery("#new_scene_title").val('');

        jQuery("#myModal").modal("hide");

        // alert("ff");

        id_counter ++;

        $scope.scene = {};

        $scope.title = "";


      }else{

        jQuery("#new_scene_title").focus();

        $scope.title = "";

      }

      $scope.scene_title = "";

    }

  });



  

  // callback function for np-repeat
  // reference from http://stackoverflow.com/questions/15207788/calling-a-function-when-ng-repeat-has-finished
  app.directive('onFinishRender', function ($timeout) {
    return {
        restrict: 'A',
        link: function (scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function () {
                    scope.$emit('ngRepeatFinished');
                });
            }
        }
    }
  });

	app.controller('MainController', function($scope) {
    $scope.sidebar = {
      hidden: false,
      toggle: function() {
        this.hidden = !this.hidden;
      }
    };
  });

  

  var scenes_collection = [
  	{
      id: "scene1",
  		title: 'scene 1',
  		
  	},
  	// {
      
  	// 	title: 'scene 2',
  		
  	// },

  ];



  
  jQuery(document).ready(function(){
    jQuery('#timeline-panel [data-toggle="tooltip"]').tooltip();   
	});

  ///////////////////////
})();


var width2 = jQuery(window).width() * 0.2;
var width8 = jQuery(window).width() * 0.8;
var width15 = jQuery(window).width() * 0.15;


jQuery(window).resize(function () {
  jQuery('html').css('height', jQuery(window).height());
  jQuery('body').css('height', jQuery(window).height());

  jQuery('#content').css('height', jQuery(window).height());

  jQuery('#scene-panel').css('height', jQuery('#content').height() * 0.6);
  jQuery('#timeline-panel').css('height', jQuery('#content').height()-jQuery('#scene-panel').height()-50);



  // jQuery('#sidebar').css('height', jQuery(window).height()-170);

  // jQuery('#sidebar').css('width', width2);
  jQuery('#content').css('width', jQuery(window).width()-jQuery('#sidebar').width());

  jQuery('#content').css('margin-left', jQuery('#sidebar').width());
  // jQuery('#cssmenu').css('width', jQuery(window).width() * 0.18);

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




jQuery(function(){ 
  jQuery(window).resize();
  jQuery('#hide-sidebar').click(function(event) {
    jQuery('#content').css('width', jQuery(window).width());
    jQuery('#content').css('margin-left', 0);
  });
  jQuery('#show-sidebar').click(function(event) {
    jQuery(window).resize();
  });

  jQuery("#link-add-category").hover(function(ev){
    jQuery("#link-add-category").stop().animate({width: ev.type=="mouseenter" ? 135 : 40}, 700, 'swing');
    jQuery('#add-category-text').stop().fadeToggle();
  });
  

});