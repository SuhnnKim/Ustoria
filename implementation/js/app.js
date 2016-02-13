
(function(){

	var app = angular.module('playground', [ ]);

	app.controller('ScencePanelController', function(){
  	this.scences = scences;
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

  
  


  var scences = [
  	{
      id:1,
  		title: 'scence 1',
  		content: 'qqqqqqqqqqqqqqqqqqqq',
  	},
  	{
      id:2,
  		title: 'scence 2',
  		content: 'wwwwwwwwwwwwwwwwwwww',
  	},
  	{
      id:3,
  		title: 'scence 3',
  		content: 'eeeeeeeeeeeeeeeeeeee',
  	},

  ];

  ///////////////////////


  dragula([document.querySelector('#scence-panel'), document.querySelector('#timeline-panel')], {
    copy: function (el, source) {
      return source === document.querySelector('#scence-panel');
    },
    accepts: function (el, target) {
    	// remove panel-body in timeline-panel
    	angular.element( document.querySelector( '#timeline-panel .panel-body' ) ).remove();
      return target !== document.querySelector('#scence-panel');
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
jQuery(window).resize(function () {
  jQuery('html').css('height', jQuery(window).height());
  jQuery('body').css('height', jQuery(window).height());
  jQuery('#content').css('height', jQuery(window).height());
  jQuery('#scence-panel').css('height', jQuery(window).height() * 0.75);
  jQuery('#timeline-panel').css('height', jQuery(window).height() * 0.25);

  jQuery('#sidebar').css('width', jQuery(window).width() * 0.2);
  jQuery('#content').css('width', jQuery(window).width() * 0.8-2);

  jQuery('#content').css('margin-left', jQuery(window).width() * 0.2+5);
  jQuery('#cssmenu').css('width', jQuery(window).width() * 0.18);

  jQuery('#show-sidebar').css('top', jQuery(window).height() * 0.5);

  jQuery('#hide-sidebar').css({

    'top': jQuery(window).height() * 0.5,
    'left':jQuery(window).width() * 0.2-20,

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
});