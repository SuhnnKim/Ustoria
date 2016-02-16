
(function(){

	var app = angular.module('playground', [ ]);

	app.controller('scenePanelController', function(){
  	this.scenes = scenes;
  });


	app
  .controller('MainController', function($scope) {
    $scope.sidebar = {
      hidden: false,
      toggle: function() {
        this.hidden = !this.hidden;
      }
    };
  });
  // .directive('expandable', function() {
  //   return {
  //     link: function(scope, el) {
  //       function atBottom() {
  //         return el.css('position') === 'absolute';
  //       }
  //       function expand(expanded) {
  //         if (atBottom()) {
  //           el.css('bottom', expanded ? 0 : margin);
  //           el.css('margin-left', 0);
  //         }
  //         else {
  //           el.css('bottom', 'auto');
  //           el.css('margin-left', expanded ? 0 : margin);
  //         }
  //       }
  //       var margin = parseInt(el.css('margin-left')) || parseInt(el.css('bottom'));

  //       scope.$watch('sidebar.hidden', expand);

  //       var oldResize = window.onresize || angular.noop;
  //       window.onresize = function() {
  //         if (window.DeviceOrientationEvent) {
  //           expand(scope.sidebar.hidden);
  //         }
  //         oldResize();
  //       };
  //     }
  //   };
  // });


  ////////////////////////

  
  


  var scenes = [
  	{
      id:1,
  		title: 'scene 1',
  		content: 'qqqqqqqqqqqqqqqqqqqq',
  	},
  	{
      id:2,
  		title: 'scene 2',
  		content: 'wwwwwwwwwwwwwwwwwwww',
  	},
  	{
      id:3,
  		title: 'scene 3',
  		content: 'eeeeeeeeeeeeeeeeeeee',
  	},

  ];

  ///////////////////////


  dragula([document.querySelector('#scene-panel'), document.querySelector('#timeline-panel')], {
    copy: function (el, source) {
      return source === document.querySelector('#scene-panel');
    },
    accepts: function (el, target) {
    	// remove panel-body in timeline-panel
    	angular.element( document.querySelector( '#timeline-panel .panel-body' ) ).remove();
      return target !== document.querySelector('#scene-panel');
    }
  });

  dragula([document.querySelector('#timeline-panel')], {
	  removeOnSpill: true,
	});

  
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
  jQuery('#content').css('height', jQuery(window).height()-100);

  jQuery('#scene-panel').css('height', jQuery('#content').height() * 0.7);
  jQuery('#timeline-panel').css('height', jQuery('#content').height() * 0.3);

  jQuery('#sidebar').css('height', jQuery(window).height()-170);

  jQuery('#sidebar').css('width', width2);
  jQuery('#content').css('width', width8);

  jQuery('#content').css('margin-left', width2);
  jQuery('#cssmenu').css('width', jQuery(window).width() * 0.18);

  jQuery('#show-sidebar').css('top', jQuery(window).height() * 0.5);

  jQuery('#hide-sidebar').css({

    'top': jQuery(window).height() * 0.5,
    'left':width2-20,

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