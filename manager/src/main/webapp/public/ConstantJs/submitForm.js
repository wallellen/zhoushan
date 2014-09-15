/**
该方法动态地创建一个form然后提交
使用方法如下：

$.form('/index')

<form action="/index" method="POST"></form>
$.form('/new', { title: 'Hello World', body: 'Foo Bar' })

<form action="/index" method="POST">
    <input type="hidden" name="title" value="Hello World" />
    <input type="hidden" name="body" value="Foo Bar" />
</form>
$.form('/info', { userIds: [1, 2, 3, 4] }, 'GET')

<form action="/info" method="GET">
    <input type="hidden" name="userIds[]" value="1" />
    <input type="hidden" name="userIds[]" value="2" />
    <input type="hidden" name="userIds[]" value="3" />
    <input type="hidden" name="userIds[]" value="4" />
</form>
$.form('/profile', { sender: { first: 'John', last: 'Smith', postIds: null },
                     receiver: { first: 'Foo', last: 'Bar', postIds: [1, 2] })

<form action="/profile" method="POST">
    <input type="hidden" name="sender[first]" value="John">
    <input type="hidden" name="sender[last]" value="Smith">
    <input type="hidden" name="receiver[first]" value="John">
    <input type="hidden" name="receiver[last]" value="Smith">
    <input type="hidden" name="receiver[postIds][]" value="1">
    <input type="hidden" name="receiver[postIds][]" value="2">
</form>
*/
;(function(){
    jQuery.extend({
    form: function(url, data, method) {
        if (method == null) method = 'POST';
        if (data == null) data = {};

        var form = jQuery('<form>').attr({
            method: method,
            action: url
         }).css({
            display: 'none'
         });

        var addData = function(name, data) {
            if (jQuery.isArray(data)) {
                for (var i = 0; i < data.length; i++) {
                    var value = data[i];
                    addData(name + '[]', value);
                }
            } else if (typeof data === 'object') {
                for (var key in data) {
                    if (data.hasOwnProperty(key)) {
                        addData(name + '[' + key + ']', data[key]);
                    }
                }
            } else if (data != null) {
                form.append(jQuery('<input>').attr({
                  type: 'hidden',
                  name: String(name),
                  value: String(data)
                }));
            }
        };

        for (var key in data) {
            if (data.hasOwnProperty(key)) {
                addData(key, data[key]);
            }
        }

        return form.appendTo('body');
    }
    }); 
})();