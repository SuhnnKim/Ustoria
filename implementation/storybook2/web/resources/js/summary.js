/**
 * Created by AshirwadTank on 3/7/2016.
 */
var app = angular.module('summary', [ ]);
jQuery('.text-select').remove();
jQuery('#content').append('<div class=\'text-select\' style=\' visibility: hidden\' ></div>');


var selectedText;
jQuery('#summary-panel').on('selectstart', function () {
    jQuery(document).one('mouseup', function() {

        selectedText=this.getSelection();

        jQuery('.text-select').text(selectedText);


    });
});

//Add Summary Section

jQuery('#btnSummaryNameSave').click(function(){


    var summaryName = jQuery('#summaryName').val();

    var summaryText = jQuery('.text-select').html();


    jQuery.ajax({
        method : "POST",
        url : 'AddSummarySection',
        data : {
            summaryName : summaryName,
            summaryContent: summaryText
        },
        success : function(responseText) {

            jQuery('#summary-list').empty();
            jQuery('#summary-section-list').empty();
            jQuery.each(JSON.parse(responseText), function(idx, obj) {
                jQuery('#summary-list').append('<li id='+obj.name+'><a href=\'#\' >'+obj.name+'</a></li>');

                var cloneSummaryListItem= '<div id='+obj.name+' class=\'summary-section-wrapper\'><button type=\'button\' class=\'btn btn-info summary-section-button\'>'+obj.name+'</button><button type=\'button\' class=\'btn btn-success btn-small\'><span class=\'glyphicon glyphicon-pencil\' aria-hidden=\'true\'></span></button><button type=\'button\' class=\'btn btn-danger btn-small deleteSummary\'><span class=\'glyphicon glyphicon-remove\' aria-hidden=\'true\'></span></button></div>';

                jQuery('#summary-section-list').append(cloneSummaryListItem);


            });



            jQuery('#myModal').modal('hide');

        }
    });
});

//delete Summary Section

jQuery('#summary-section-list').on('click','.deleteSummary',function(){


    var summaryId = jQuery(this).closest('div').attr('id');

   //alert(summaryId);


    jQuery.ajax({
        method : "POST",
        url : 'deleteSummarySection',
        data : {
            summaryId : summaryId,

        },
        success : function(responseText) {

            jQuery('#summary-list').empty();
            jQuery('#summary-section-list').empty();
            jQuery.each(JSON.parse(responseText), function(idx, obj) {
                jQuery('#summary-list').append('<li id='+obj.name+'><a href=\'#\' >'+obj.name+'</a></li>');

                var cloneSummaryListItem= '<div id='+obj.name+' class=\'summary-section-wrapper\'><button type=\'button\' class=\'btn btn-info summary-section-button\'>'+obj.name+'</button><button type=\'button\' class=\'btn btn-success btn-small\'><span class=\'glyphicon glyphicon-pencil\' aria-hidden=\'true\'></span></button><button type=\'button\' class=\'btn btn-danger btn-small deleteSummary\'><span class=\'glyphicon glyphicon-remove\' aria-hidden=\'true\'></span></button></div>';

                jQuery('#summary-section-list').append(cloneSummaryListItem);


            });
        }
    });
});





// New Summary

jQuery('#save-story').on('click',function(){


    var summaryText = jQuery('#summary-panel').html();

    jQuery.ajax({
        method : "POST",
        url : 'SaveSummary',
        data : {
            summaryData : summaryText
        },
        success : function(responseText) {
            jQuery('#summary-panel').html(" ");

            jQuery('#summary-panel').html(responseText);
        }
    });
});

jQuery('#summary-section-list').on('click','.summary-section-button',function(){


    jQuery('#summary-panel').removeHighlight();
    var summaryText = jQuery(this).text();

    jQuery.ajax({
        method : "POST",
        url : 'getSummarySectionText',
        data : {
            summaryData : summaryText
        },
        success : function(responseText) {

            jQuery('#summary-panel').highlight(responseText);

        }
    });
});

function convertToHtml(plainText){
    return jQuery(this).html(plainText)
}


jQuery('div[contenteditable]').keydown(function(e) {
    jQuery('#summary-panel').removeHighlight();
    // trap the return key being pressed
    if (e.keyCode === 13) {
        // insert 2 br tags (if only one br tag is inserted the cursor won't go to the next line)
        document.execCommand('insertHTML', false, '<br><br>');
        // prevent the default behaviour of return key pressed
        return false;
    }
});


