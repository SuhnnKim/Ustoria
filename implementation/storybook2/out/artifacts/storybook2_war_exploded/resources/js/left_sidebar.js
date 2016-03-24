/**
 * Created by Zhipe on 2016-03-17.
 */

jQuery(function(){
    // left sidebar menu item clicked animation
    //jQuery('#cssmenu li.active').addClass('open').children('ul').show();
    //jQuery('#cssmenu li.has-sub>a').on('click', function(){
    //    jQuery(this).removeAttr('href');
    //    var element = jQuery(this).parent('li');
    //    if (element.hasClass('open')) {
    //        element.removeClass('open');
    //        element.find('li').removeClass('open');
    //        element.find('ul').slideUp(200);
    //    }
    //    else {
    //        element.addClass('open');
    //        element.children('ul').slideDown(200);
    //        element.siblings('li').find('li').removeClass('open');
    //    }
    //});

    jQuery('#hide-sidebar').click(function(event) {
        jQuery('#content').css('width', jQuery(window).width());
        jQuery('#content').css('margin-left', 0);
    });

    jQuery('#show-sidebar').click(function(event) {
        jQuery('#content').css('width', jQuery(window).width()-jQuery('#sidebar').width());
        jQuery(window).resize();
    });

    // hover toggle function
    jQuery("#link-add-category").hover(function(ev){
        jQuery("#link-add-category").stop().animate({width: ev.type=="mouseenter" ? 135 : 40}, 700, 'swing');
    });
    jQuery(window).resize();
});



//jQuery(window).resize(function () {
//    jQuery('#sidebar').css('width', 150);
//    jQuery('#content').width(jQuery(window).width() - jQuery('#left_sidebar').width());
//    jQuery('#show-sidebar').css('top', jQuery(window).height() * 0.5);
//
//    jQuery('#hide-sidebar').css({
//
//        'top': jQuery(window).height() * 0.5,
//        'left':jQuery('#sidebar').width()-20,
//
//    });
//    jQuery('#link-playground').css({
//
//        'bottom': 0,
//        'left':0,
//        'width': jQuery('#sidebar').width(),
//
//    });
//    jQuery('#link-add-category').css({
//
//        'bottom': jQuery('#link-playground').height()*2.5,
//
//    });
//});



app.controller('LeftSidebarController', function($scope) {
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