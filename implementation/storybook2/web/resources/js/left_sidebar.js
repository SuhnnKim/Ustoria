/**
 * Created by Zhipe on 2016-03-17.
 */


// left sidebar menu item clicked animation
jQuery('#cssmenu li.active').addClass('open').children('ul').show();
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