jQuery(function(){


	makeSceneDroppable();
	makeSceneTitleDraggable();
	
	
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
        	
        	// ui.item.css('float','left');
            if(removeIntent == true){
                ui.item.remove();   
            }
        },
        stop:function(event, ui){
        	
        },
     //    placeholder: {
	    //     element: function(currentItem) {
	    //         return jQuery("<li><em>test</em></li>")[0];
	    //     },
	    //     update: function(container, p) {
	    //         return;
	    //     }
	    // }
	    // start: function( event, ui ) {
     //        clone = jQuery(ui.item[0].outerHTML).clone();
     //    },
     //    placeholder: {
     //        element: function(clone, ui) {
     //            return jQuery('<li class="scene_title_placeholder">'+clone[0].innerHTML+'</li>');
                
     //        },
     //        update: function() {
     //            return;
     //        }
     //    },
        distance: 1,
        delay: 10,
	});


});