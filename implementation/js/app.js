(function(){

	var app = angular.module('playground', [ ]);

	app.controller('scenePanelController', function($scope){

  	this.scenes = scenes_collection;

    $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
        makeSceneDroppable();
        makeSceneTitleDraggable();
    });
    
  });

  app.controller('TimeLineController', function($scope){
    $scope.timeline = {
      hidden: false,
      toggle: function() {
        this.hidden = !this.hidden;
        timeline_wrapper.hidden = !timeline_wrapper.hidden;
        timeline_hide.hidden = !timeline_hide.hidden;
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

	app.controller('MainController', function($scope) {

    $scope.sidebar = {
      hidden: false,
      toggle: function() {
        this.hidden = !this.hidden;
      }
    };

    $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
      // bind the ngRepeatFinished event with following function
      // make sure the new added item gets binded
      sidebarAnimate();
      makeMenuItemSortable();
    });


    $scope.menuItems = predefinedMenuItem;
    $scope.menuSubItems = [];

    $scope.addMenuItem = function(newMenuItem){
      
      if(newMenuItem.name === undefined || newMenuItem.name === ''){
        $scope.missingNewMenuItemError = "Category needs a name";
        return;
      }
      $scope.menuItems.push({
        name: newMenuItem.name,
        children: $scope.menuSubItems
      });
      $scope.missingNewMenuItemError = "";
      jQuery("#addCategoryModal").modal("hide");
      newMenuItem.name = null;
      $scope.menuSubItems = [];
      
      return;
    };

    $scope.addSubMenuItem = function(menuSubItem){
      if(!(menuSubItem.name === undefined || menuSubItem.name === '')){
        $scope.menuSubItems.push({
          name: menuSubItem.name
        });
        menuSubItem.name = null;
      }
    };

    $scope.removeSubMenuItem = function(subItem){
      var index = $scope.menuSubItems.indexOf(subItem);
      $scope.menuSubItems.splice(index, 1);
    };

    $scope.cancelCategoryModal = function(newMenuItem, newSubMenuItems){
      newMenuItem = null;
      $scope.menuSubItems = [];
      jQuery("#addCategoryModal").modal("hide");
    };

  });

  

  var predefinedMenuItem = [
    {
      name: "Charactor",
      children: [{name: "Diana"},{name: "Prannoy"}]
    },
    {
      name: "Location",
      children: []
    },
    {
      name: "Summary",
      children: []
    }
  ];

  var scenes_collection = [
  	{
      id: "scene1",
  		title: 'scene 1',
  		
  	},

  ];



  
  jQuery(document).ready(function(){
    jQuery('#timeline_panel [data-toggle="tooltip"]').tooltip();   
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

  



  // jQuery('#sidebar').css('height', jQuery(window).height()-170);

  // jQuery('#sidebar').css('width', width2);

  jQuery('#content').css('margin-left', jQuery('#sidebar').width());
  jQuery('#content').css('width', jQuery(window).width()-150);

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
  jQuery('#scene-panel').css('height', jQuery('#content').height() * 0.6);
  jQuery('#timeline_panel').css('height', jQuery('#content').height()-jQuery('#scene-panel').height()-50);

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