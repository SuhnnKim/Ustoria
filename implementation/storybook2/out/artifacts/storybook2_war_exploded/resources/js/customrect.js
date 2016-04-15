fabric.CustomRect = fabric.util.createClass(fabric.Rect, {
    type : 'CustomRect',

    initialize : function(options) {
        options || ( options = { });

        this.callSuper('initialize', options);
        options && this.set('id', options.id) && this.set('custype', options.custype);
    },

    toObject : function() {
        return fabric.util.object.extend(this.callSuper('toObject'), { id: this.id }, { custype: this.custype });
    },

    clone: function () {
        var clone = fabric.util.object.clone(this)
        return clone;
    },

    _render : function(ctx) {
        this.callSuper('_render', ctx);
    }
});

fabric.CustomRect.fromObject = function(object, callback) {
    callback && callback(new fabric.CustomRect(object));
};

fabric.CustomRect.async = true;
