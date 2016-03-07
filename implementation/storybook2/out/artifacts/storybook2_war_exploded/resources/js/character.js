jQuery("#saveForm").submit(function() {

  var relationList = new Array();
  var roleList = new Array();
  var attList = new Array();
  var sceneList = new Array();
  var name = jQuery("input[name=name]").val()
  var desc = jQuery("#desc").val()
  var id = jQuery("#uuid").val()


  jQuery("input[name=relaAtt]").each(function(){
    relationList.push(jQuery(this).val())
  });

  jQuery("input[name=roleAtt]").each(function(){
    roleList.push(jQuery(this).val())
  });

  jQuery("input[name=attAtt]").each(function(){
    attList.push(jQuery(this).val())
  });

  jQuery("input[name=sceneAtt]").each(function(){
    sceneList.push(jQuery(this).val())
  });

  var relation = JSON.stringify(relationList);
  var role = JSON.stringify(roleList);
  var attribute = JSON.stringify(attList);
  var scene = JSON.stringify(sceneList);

  //var attributeList = new Map();
  //attributeList.set(relation, "relationship");
  //attributeList.set(role, "role");
  //attributeList.set(attribute, "attribute");
  //attributeList.set(scene, "scene");
  //
  //var final = JSON.stringify(attributeList);
  //
  //alert(final)

  //Ajax for Servlet
  jQuery.ajax({
    method: "POST",
    url: 'saveCharacter',
    data: {
      id: id,
      name: name,
      desc: desc,
      relationList: relation,
      roleList: role,
      attributeList: attribute,
      sceneList: scene,
    },
    success: function (responseText) {


      jQuery('#character-list').empty();
      jQuery("#thumbnaillist").empty();



      jQuery.each(JSON.parse(responseText), function (idx, obj) {

        jQuery('#character-list').append('<li id=' + obj.name + '><a class="link" href="loadCharacter?id='+obj.id+'" >' + obj.name + '</a></li>');

        jQuery("#thumbnaillist").append("<img src='/storybook/resources/img/default-character-image.png' alt='character1' class='img-thumbnail' width='80' height='80' />");
        if(id == ""){jQuery("#uuid").val(obj.id);}

      });

    },
    error:function(request){
      alert(request.responseText);
      event.preventDefault();
    }
  });

});



jQuery("#new-character").on("click", function() {
  jQuery.ajax({
    method: "POST",
    url: 'newCharacter',
    success: function (responseText) {

      jQuery("input[name=name]").empty()
      desc = jQuery("#desc").empty()

      jQuery('#character-list').empty();
      jQuery("#thumbnaillist").empty();
      jQuery("#relationship").empty();
      jQuery("#relationship").append("<tr><td>Relationship</td></tr>");
      jQuery("#role").empty();
      jQuery("#role").append("<tr><td>Role</td></tr>");
      jQuery("#scene").empty();
      jQuery("#scene").append("<tr><td>Scene</td></tr>");
      jQuery("#att").empty();
      jQuery("#att").append("<tr><td>Attribute</td></tr>");
      jQuery("#name").val("");
      jQuery("#desc").val("");

      jQuery.each(JSON.parse(responseText), function (idx, obj) {

        jQuery('#character-list').append('<li id=' + obj.name + '><a href="loadCharacter?id="'+ obj.id+'>' + obj.name + '</a></li>');
        jQuery("#thumbnaillist").append("<img src='/storybook/resources/img/default-character-image.png' alt='character1' class='img-thumbnail' width='80' height='80' />");

        jQuery("#uuid").val(obj.id);

      });

    },
    error:function(request){
      alert(request.responseText);
      event.preventDefault();
    }
  })
});


jQuery("#pop").on("click", function() {
  // here asign the image to the modal when the user click the enlarge link
  jQuery('#imagepreview').attr('src', jQuery('#imageresource').attr('src'));
  // imagemodal is the id attribute assigned to the bootstrap modal, then i use the show function
  jQuery('#imagemodal').modal('show');
});

jQuery("#relationAddlist li").on("click", function() {
  jQuery("#relationship").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li><input type='hidden' name='relaAtt' id='relaAtt' value='"+jQuery(this).text()+"'/></td><td><img class='del' src='/storybook/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");


});



jQuery("#roleAddlist li").on("click", function() {
  jQuery("#role").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"<input type='hidden' name='roleAtt' value='"+ jQuery(this).text() +"'/></li></td><td><img class='del' src='/storybook/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

});

jQuery("#attAddlist li").on("click", function() {
  jQuery("#att").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li><input type='hidden' name='attAtt' value='"+ jQuery(this).text() +"'/></td><td><img class='del' src='/storybook/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");


});

jQuery("#sceneAddlist li").on("click", function() {
  jQuery("#scene").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li></td><input type='hidden' name='sceneAtt' value='"+ jQuery(this).text() +"'><td><img class='del' src='/storybook/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  //jQuery(".del").on("click", function() {
  //    jQuery(this).parent().parent().remove();
  //});

});

jQuery(".del").on("click", function() {
  jQuery(this).parent().parent().remove();
});

