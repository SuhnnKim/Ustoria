jQuery(function(){


	// makeSceneDroppable();
	// makeSceneTitleDraggable();

	jQuery('#content').css('width', jQuery(window).width()-jQuery('#sidebar').width());
	jQuery('#timeline').width(jQuery('#content').width());

	var mySlySlider;

	// initialize the timeline scroll animation ( from sly library)
	(function () {
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
	}());
	
	
	jQuery(".menu_item_list").sortable({
	    
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



	var removeIntent = false;
	

	jQuery("#timeline").sortable({
		opacity: 0.5,
		// items: "> li",
	    over: function (event, ui) {
	    	// ui.helper.width(100); 
            removeIntent = false;
        },
        out: function () {

            removeIntent = true;

            
        },
        beforeStop: function (event, ui) {
        	
            if(removeIntent == true){
                ui.item.remove();  
                // reload the timeline slider since item was removed
                mySlySlider.reload();
            }
        },
        stop:function(event, ui){
        	
        },
        update: function (event, ui) {
        	// reload the timeline slider since item was added
	        mySlySlider.reload();
	    },
	});


});