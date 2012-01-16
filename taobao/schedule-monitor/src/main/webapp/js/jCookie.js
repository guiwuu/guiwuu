/**
* Cookie plugin
*
* Copyright www.ebiz163.com
* Dual licensed under the MIT and GPL licenses:
* http://www.opensource.org/licenses/mit-license.php
* http://www.gnu.org/licenses/gpl.html
*
*/

/**
* Create a cookie with the given name and value and other optional parameters.
*
* @example jQuery.cookie('the_cookie', 'the_value');
* @desc Set the value of a cookie.
* @example jQuery.cookie('the_cookie', 'the_value', { expires: 7, path: '/', domain: 'jquery.com', secure: true });
* @desc Create a cookie with all available options.
* @example jQuery.cookie('the_cookie', 'the_value');
* @desc Create a session cookie.
* @example jQuery.cookie('the_cookie', null);
* @desc Delete a cookie by passing null as value. Keep in mind that you have to use the same path and domain
*       used when the cookie was set.
*
* @param String name The name of the cookie.
* @param Object value The value of the cookie.
* @param Object options An object literal containing key/value pairs to provide optional cookie attributes.
* @option Number|Date expires Either an integer specifying the expiration date from now on in days or a Date object.
*                             If a negative value is specified (e.g. a date in the past), the cookie will be deleted.
*                             If set to null or omitted, the cookie will be a session cookie and will not be retained
*                             when the the browser exits.
* @option String path The value of the path atribute of the cookie (default: path of page that created the cookie).
* @option String domain The value of the domain attribute of the cookie (default: domain of page that created the cookie).
* @option Boolean secure If true, the secure attribute of the cookie will be set and the cookie transmission will
*                        require a secure protocol (like HTTPS).
* @type undefined
*
* @name jQuery.cookie
* @cat Plugins/Cookie
* @author Klaus Hartl/klaus.hartl@stilbuero.de
*/

/**
* Get the value of a cookie with the given name.
*
* @example jQuery.cookie('the_cookie');
* @desc Get the value of a cookie.
*
* @param String name The name of the cookie.
* @return The value of the cookie.
* @type String
*
* @name jQuery.cookie
* @cat Plugins/Cookie
* @author Klaus Hartl/klaus.hartl@stilbuero.de
*/


jQuery.cookie = function(key, value, options) {

if (typeof value != 'undefined') { // name and value given, set cookie         
         options = options || {};
   if (value === null) {
            value = '';
            options.expires = -1;
        }else
    value = encodeValue(value);


        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        // CAUTION: Needed to parenthesize options.path and options.domain
        // in the following expressions, otherwise they evaluate to undefined
        // in the packed version for some reason...
        var path = options.path ? '; path=' + (options.path) : '';
        var domain = options.domain ? '; domain=' + (options.domain) : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [key, '=', value, expires, path, domain, secure].join('');

}else {
   var cookies = {};
   var c = document.cookie + ";";

   var re = /\s?(.*?)=(.*?);/g;
   var matches;
   while ((matches = re.exec(c)) != null) {
    var name = matches[1];
    var value = matches[2];
    if (name == key) {
     return decodeValue(value);
    }
   }
   return null;
}

/** -----------------------private-------------------------------------- function */


function encodeValue(v) {
        var enc;
if (typeof v == "number") {
   enc = "n:" + v;
} else if (typeof v == "boolean") {
   enc = "b:" + (v ? "1" : "0");
} else if (v instanceof Date) {
   enc = "d:" + v.toGMTString();
} else if (v instanceof Array) {
   var flat = "";
   for (var i = 0, len = v.length; i < len; i++) {
    flat += encodeValue(v[i]);
    if (i != len - 1)
     flat += "^";
   }
   enc = "a:" + flat;
} else if (typeof v == "object") {
   var flat = "";
   for (var key in v) {
    if (typeof v[key] != "function" && v[key] !== undefined) {
     flat += key + "=" + encodeValue(v[key]) + "^";
    }
   }
   enc = "o:" + flat.substring(0, flat.length - 1);
} else {
   enc = "s:" + v;
}
return escape(enc);

}

function decodeValue (cookie) {
var re = /^(a|n|d|b|s|o)\:(.*)$/;
var matches = re.exec(unescape(cookie));
if (!matches || !matches[1])
   return; // non state cookie
var type = matches[1];
var v = matches[2];
switch (type) {
   case "n" :
    return parseFloat(v);
   case "d" :
    return new Date(Date.parse(v));
   case "b" :
    return (v == "1");
   case "a" :
    var all = [];
    var values = v.split("^");
    for (var i = 0, len = values.length; i < len; i++) {
     all.push(decodeValue(values[i]));
    }
    return all;
   case "o" :
    var all = {};
    var values = v.split("^");
    for (var i = 0, len = values.length; i < len; i++) {
     var kv = values[i].split("=");
     all[kv[0]] = decodeValue(kv[1]);
    }
    return all;
   default :
    return v;
}
}

}


jQuery.fn.decookie = function(){
var tag = jQuery(this).attr("tagName");
switch (tag)
{
case 'FORM' :
     jQuery(this).decookieForm();
     break;
case 'UL':
        jQuery(this).decookieOrder();
     break;
default:
   alert('不支持此DOM的cookie操作');
     break;
}

}


jQuery.fn.cookie = function(){
var tag = jQuery(this).attr("tagName");
switch (tag)
{
case 'FORM' :
     jQuery(this).cookieForm();
     break;
case 'UL':
        jQuery(this).cookieOrder();
     break;
default:
   alert('不支持此DOM的cookie操作');
     break;
}
}


/**
* 从cookie中还原Form
*/
jQuery.fn.decookieForm = function(){
if(jQuery(this).attr("tagName") != 'FORM'){
       alert('此方法只支持form的cookie操作');
    return;
}


var object 

var formName = jQuery(this).attr("name");


cookies = jQuery.cookie(formName);


if(!cookies || cookies.length <= 0)
     return;

jQuery('input, textarea, select,', this).each(function() {
   var elementID = this.id;

   var type = this.type;
  
   disabled = this.disabled;
  
   val = cookies[elementID] || "";
  
   if (!disabled) {
    switch (type) {
     case 'textarea':
     case 'text' :
      this.value = val;
      break;
     case 'select-one' :
     case 'select-multiple' :
      jQuery(this).select(val);
      break;
     case 'radio' :
     case 'checkbox' :
      this.checked = !!val;
      break;
     default :
      break;
    }
   }
});


}


/**
* 保存Form的各个Field值到cookie中
*/
jQuery.fn.cookieForm = function(){
if(jQuery(this).attr("tagName") != 'FORM'){
       alert('此方法只支持form的cookie操作');
     return;
}

var formName = jQuery(this).attr("name");
var formObj = {};
jQuery('input, textarea, select,', this).each(function() {
        var type = this.type;

   if (this.tagName == 'INPUT'&& type != 'text' && type != 'radio' && type != 'checkbox')
   {
    return;
   }
  
   var value = this.value;

   if(type == 'select-multiple'){
    value = jQuery(this).select();
   }
  
   var elementID = this.id;
  
   if (!elementID) {
    alert("this element didn't have ID, could not be cookied,name:"
     + name);
    return;
   }
  
   if ((type == 'radio' || type == 'checkbox') && !this.checked){
     value = '';
   }
  
   formObj[this.id] = value;
});

jQuery.cookie(formName, formObj);


}


/**
* 如果不输入values参数，函数返回下拉框的单选或多选的值
* 如果输入values参数, 则用values选中下拉框中符合的值
*/
jQuery.fn.select = function(values) {

if(!values){
    values = [];
    jQuery('option:selected', this).each(function(){
         values.push(this.value);
    });

    return values;
}

if (!(values instanceof Array)) {
   values = [values];
}

jQuery('option', this).each(function(){
   this.selected = false;

   if (jQuery.inArray(this.value, values) >= 0 ){
    this.selected = true;
   }
});
}



/**
* 将list的顺序记录到cookie中
*/
jQuery.fn.cookieOrder = function() {
// save custom order to cookie
var key = jQuery(this).attr("id");
jQuery.cookie(key, jQuery(this).sortable("toArray"));
}

/**
* 从cookie中恢复list的顺序
*/
jQuery.fn.decookieOrder = function() {
var list = jQuery(this);
if (list == null) return

// fetch the cookie value (saved order)
var key = jQuery(this).attr("id");

var IDs = jQuery.cookie(key);


if (!IDs) return;
//IDs = IDs.split(",");

// fetch current order
var items = list.sortable("toArray");

// make array from current order
var rebuild = new Array();
for ( var v=0, len=items.length; v<len; v++ ){
   rebuild[items[v]] = items[v];
}

for (var i = 0, n = IDs.length; i < n; i++) {
  
   // item id from saved order
   var itemID = IDs[i];
  
   if (itemID in rebuild) {
  
    // select item id from current order
    var item = rebuild[itemID];
   
    // select the item according to current order
    var child = jQuery("ul.ui-sortable").children("#" + item);
   
    // select the item according to the saved order
    var savedOrd = jQuery("ul.ui-sortable").children("#" + itemID);
   
    // remove all the items
    child.remove();
   
    // add the items in turn according to saved order
    // we need to filter here since the "ui-sortable"
    // class is applied to all ul elements and we
    // only want the very first! You can modify this
    // to support multiple lists - not tested!
    jQuery("ul.ui-sortable").filter(":first").append(savedOrd);
   }
}
}