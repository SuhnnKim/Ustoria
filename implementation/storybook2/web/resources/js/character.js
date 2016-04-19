var app = angular.module('character', [ ]);

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

        jQuery('#character-list').append('<li id=' + obj.name + 'class="menu_item ng-scope" ng-repeat="subMenuItem in menuItem.children" on-menu-finish-render on-finish-render><a class="link" href="loadCharacter?id='+obj.id+'" >' + obj.name + '</a></li>');

        jQuery("#thumbnaillist").append("<a title="+ obj.name +" id="+ obj.id +"><img src='/Ustoria/resources/img/default-character-image.png' alt='character1' class='img-thumbnail' width='80' height='80' /></a>");
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

        jQuery('#character-list').append('<li id=' + obj.name + 'class="menu_item ng-scope" ng-repeat="subMenuItem in menuItem.children" on-menu-finish-render on-finish-render><a href="loadCharacter?id="'+ obj.id+'>' + obj.name + '</a></li>');
        jQuery("#thumbnaillist").append("<a title="+ obj.name +" id="+ obj.id +"><img src='/Ustoria/resources/img/default-character-image.png' alt='character1' class='img-thumbnail' width='80' height='80' /></a>");

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
  jQuery("#relationship").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li><input type='hidden' name='relaAtt' id='relaAtt' value='"+jQuery(this).text()+"'/></td><td><img class='del' src='/Ustoria/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  jQuery(".del").on("click", function() {
    jQuery(this).parent().parent().remove();
  });


});



jQuery("#roleAddlist li").on("click", function() {
  jQuery("#role").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"<input type='hidden' name='roleAtt' value='"+ jQuery(this).text() +"'/></li></td><td><img class='del' src='/Ustoria/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  jQuery(".del").on("click", function() {
    jQuery(this).parent().parent().remove();
  });


});

jQuery("#attAddlist li").on("click", function() {
  jQuery("#att").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li><input type='hidden' name='attAtt' value='"+ jQuery(this).text() +"'/></td><td><img class='del' src='/Ustoria/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  jQuery(".del").on("click", function() {
    jQuery(this).parent().parent().remove();
  });



});

jQuery("#sceneAddlist li").on("click", function() {
  jQuery("#scene").append("<tr><td><li class='list-group-item'>"+jQuery(this).text()+"</li></td><input type='hidden' name='sceneAtt' value='"+ jQuery(this).text() +"'><td><img class='del' src='/Ustoria/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' /></td></tr>");

  jQuery(".del").on("click", function() {
    jQuery(this).parent().parent().remove();
  });



});

jQuery(".del").on("click", function() {
  jQuery(this).parent().parent().remove();
});


jQuery("#fileupload").fileupload({
  dataType: 'json',
  done: function (e,data) {
    jQuery("tr:has(td)").remove();
    jQuery.each(data.result, function (index, file) {
      jQuery("#uploaded-files").append(
          jQuery("<tr>")
              .append(jQuery('<td/>').text(file.fileName))
              .append(jQuery('<td/>').text(file.fileSize))
              .append(jQuery('<td/>').text(file.fileType))
              .append(jQuery('<td/>').html("<a href='get/"+index+"'>Click</a>"))
      )
    });
  },

  progressall: function (e, data) {
    var progress = parseInt(data.loaded / data.total * 100, 10);
    jQuery('#progress .bar').css(
        'width',
        progress + '%'
    );
  },

  dropZone: jQuery('#dropzone')

});


jQuery("#s").click(function () {
  var test = {
    "result": [{
      "sources": [{
        "id": "1",
        "name": "Source 1",
        "label": "Web Form",
        "con": "1"
      }, {
        "id": "2",
        "name": "Source 2",
        "label": "Edition Email",
        "con": "1"
      }, {
        "id": "3",
        "name": "Source 3",
        "label": "Inbound Phone",
        "con": "1"
      }]
    }, {
      "campaign": [{
        "id": "1",
        "name": "sample campaign",
        "label": "Campaign",

      }]
    }, {
      "targets": [{
        "id": "1",
        "name": "target1",
        "label": "Web Page"
      }]
    }, {
      "editions": [{
        "id": "1",
        "name": "edition1",
        "label": "Kick Off",
        "level": "1",
        "tcon": "1",
        "scon": "3"
      }, {
        "id": "2",
        "name": "edition 2",
        "label": "Not Opend 1",
        "level": "1",
        "tcon": null,
        "scon": null
      }, {
        "id": "3",
        "name": "Edition 2",
        "label": "Not Opened 2",
        "level": "1",
        "tcon": null,
        "scon": null
      }, {
        "id": "4",
        "name": "Edition 5",
        "label": "Clicked 1",
        "level": "2",
        "tcon": null,
        "scon": null
      }]
    }]
  }

  var q = JSON.stringify(test);

  jQuery.ajax({
    method: "POST",
    url: 'test',
    data: {
      id: q,
    },
    dataType: 'JSONObject',
    success: function (data) {
      alert("Good!");
    },
    error: function (data) {
      alert("error!");
    }
  });
});




