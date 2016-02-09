
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
  })
  .directive('expandable', function() {
    return {
      link: function(scope, el) {
        function atBottom() {
          return el.css('position') === 'absolute';
        }
        function expand(expanded) {
          if (atBottom()) {
            el.css('bottom', expanded ? 0 : margin);
            el.css('margin-left', 0);
          }
          else {
            el.css('bottom', 'auto');
            el.css('margin-left', expanded ? 0 : margin);
          }
        }
        var margin = parseInt(el.css('margin-left')) || parseInt(el.css('bottom'));

        scope.$watch('sidebar.hidden', expand);

        var oldResize = window.onresize || angular.noop;
        window.onresize = function() {
          if (window.DeviceOrientationEvent) {
            expand(scope.sidebar.hidden);
          }
          oldResize();
        };
      }
    };
  });


  ////////////////////////

  
  


  var scences = [
  	{
  		title: 'scence 1',
  		content: 'qqqqqqqqqqqqqqqqqqqq',
  	},
  	{
  		title: 'scence 2',
  		content: 'wwwwwwwwwwwwwwwwwwww',
  	},
  	{
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
	  removeOnSpill: true
	});

  
  $(document).ready(function(){
    $('#timeline-panel [data-toggle="tooltip"]').tooltip();   
	});

  ///////////////////////
})();
