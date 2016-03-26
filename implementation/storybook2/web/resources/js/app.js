(function(){

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

  app.controller('MainController', function($scope) {
    // toggle the sidebar
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


    $scope.menuItems = [{name: 'Character', children: []}, {name: 'Summary', children: []}];
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





  jQuery(document).ready(function(){
    jQuery('#timeline_panel [data-toggle="tooltip"]').tooltip();   
	});

})();


jQuery(window).resize(function () {
  // jQuery('html').css('height', jQuery(window).height());
  // jQuery('body').css('height', jQuery(window).height());

  // setting #sidebar's width
  jQuery('#sidebar').css('width', 150);
  // setting #content's width
  jQuery('#content').css('width', jQuery(window).width() - jQuery('#sidebar').width());
  // setting #content's height
  jQuery('#content').css('height', jQuery(window).height() - jQuery('#navbar_playgrond').height());

  jQuery('#content').css('margin-left', jQuery('#sidebar').width());

  jQuery('#play_wrap').css('height', (jQuery('#content').height()-50));

  jQuery('#play_wrap').css('border-bottom', 'solid 1px black');

  jQuery('#scene-panel').css('height', jQuery('#play_wrap').height() * 0.6);

  jQuery('#timeline_panel').css('height', jQuery('#play_wrap').height()-jQuery('#scene-panel').height());

  jQuery('#timeline_wrapper').css('height', jQuery('#timeline_panel').height());

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

  // hover toggle function
  jQuery("#link-add-category").hover(function(ev){
    jQuery("#link-add-category").stop().animate({width: ev.type=="mouseenter" ? 135 : 40}, 700, 'swing');
  });
  

});