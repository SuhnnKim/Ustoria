(function () {
	'use strict';

	fabric.Zline = fabric.util.createClass(fabric.Line, {

  		type: 'zline',

  		initialize: function(element, options) {

    		this.callSuper('initialize', element, options);

    		options && this.set({
    			id       : options.id,
    			sourceID : options.sourceID,
          targetID : options.targetID,
          arrow    : options.arrow
    		});
  		},

  		toObject: function() {
    		return fabric.util.object.extend(this.callSuper('toObject'), { 
    			id       : this.id,
          sourceID : this.sourceID,
          targetID : this.targetID,
          arrow    : this.arrow
    		});
  		},

  		_render : function(ctx) {
	        this.callSuper('_render', ctx);
	    }
	});

	fabric.Zline.fromObject = function(object) {
		var points = [object.x1, object.y1, object.x2, object.y2];
		return new fabric.Zline(points, object);
	};

	fabric.Zline.async = false;

	// cusomized rectangle class --- Zrect
	fabric.Zrect = fabric.util.createClass(fabric.Rect, {

  		type: 'zrect',

  		initialize: function(element, options) {
    		this.callSuper('initialize', element, options);
    		options && this.set({
    			id      : options.id,
          boxType : options.boxType,
          text    : options.text,
          speaker : options.speaker,
          connections_I_am_source: options.connections_I_am_source,
          connections_I_am_target: options.connections_I_am_target
    		});
  		},

  		toObject: function() {
    		return fabric.util.object.extend(this.callSuper('toObject'), { 
    			id      : this.id,
          boxType : this.boxType,
          text    : this.text,  
          speaker : this.speaker,
          connections_I_am_source: this.connections_I_am_source,
          connections_I_am_target: this.connections_I_am_target 
    		});
  		},

  		_render : function(ctx) {
	        this.callSuper('_render', ctx);
	    }
	});

	fabric.Zrect.fromObject = function(object) {
		return new fabric.Zrect(object);
	};

	fabric.Zrect.async = false;



})();