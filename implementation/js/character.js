jQuery("#save").on("click", function() {
  jQuery("#character-list").append("<img src='' alt=''character1' class='img-thumbnail' width='80' height='80' />");

  jQuery("#saveListLeft").append("<li><a href='#'>"+jQuery("#name").val()+ "</a></li>");
});

jQuery("#del").on("click", function() {
  jQuery(this).parent().parent().remove();
});


jQuery("#pop").on("click", function() {
  // here asign the image to the modal when the user click the enlarge link
  jQuery('#imagepreview').attr('src', jQuery('#imageresource').attr('src'));
  // imagemodal is the id attribute assigned to the bootstrap modal, then i use the show function
  jQuery('#imagemodal').modal('show');
});

jQuery("#relationAddlist li").on("click", function() {
  jQuery("#relationship").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li></td><td><img id='del' src='img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  jQuery("#del").on("click", function() {
      jQuery(this).parent().parent().remove();
  });

});

jQuery("#roleAddlist li").on("click", function() {
  jQuery("#role").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li></td><td><img id='del' src='img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  jQuery("#del").on("click", function() {
      jQuery(this).parent().parent().remove();
  });

});

jQuery("#attAddlist li").on("click", function() {
  jQuery("#att").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li></td><td><img id='del' src='img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  jQuery("#del").on("click", function() {
      jQuery(this).parent().parent().remove();
  });

});

jQuery("#sceneAddlist li").on("click", function() {
  jQuery("#scene").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li></td><td><img id='del' src='img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  jQuery("#del").on("click", function() {
      jQuery(this).parent().parent().remove();
  });

});
