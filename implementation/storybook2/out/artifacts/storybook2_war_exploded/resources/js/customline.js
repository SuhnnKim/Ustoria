fabric.CustomLine = fabric.util.createClass(fabric.Path, {

    type: 'CustomLine',

    initialize: function(points, options) {
        options || ( options = { });        
          
          if (!points) {
            points = [0, 0, 0, 0];
          }

        this.callSuper('initialize', points, options);
        options && this.set('conn', options.conn);
    },

    toObject: function() {
        return fabric.util.object.extend(this.callSuper('toObject'), {
            conn: this.conn
        });
    },

    _render: function(ctx) {
        this.callSuper('_render', ctx);
    }
});

fabric.CustomLine.fromObject = function(object, callback) {
    callback && callback(new fabric.CustomLine(object));
};

fabric.CustomLine.async = true;