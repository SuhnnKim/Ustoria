// make menu item sortable and draggable
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
}
jQuery(function(){

	// initialize the width of #content and #timeline
	jQuery('#content').css('width', jQuery(window).width()-jQuery('#sidebar').width());
	jQuery('#timeline').width(jQuery('#content').width());


	// timeline scrolling animation
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


	var removeIntent = false;
	

	// make timeline sortable
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


});