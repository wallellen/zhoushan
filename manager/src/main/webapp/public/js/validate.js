/*
 * jQuery.validity ﻿v1.3.1
 * http://validity.thatscaptaintoyou.com/
 * https://github.com/whatgoodisaroad/validity
 * 
 * Dual licensed under MIT and GPL
 *
 * Date: 2013-01-28 (Monday, 28 January 2013)
 */
(function(a,e){function d(b,c,h){var d=[];(b.reduction||b).filter(a.validity.settings.elementSupport).each(function(){c(this)?d.push(this):j(this,f(h,{field:q(this)}))});b.reduction=a(d);return b}function g(){if(a.validity.isValidating())a.validity.report.errors++,a.validity.report.valid=!1}function j(b,c){g();a.validity.out.raise(a(b),c)}function i(b,c){g();a.validity.out.raiseAggregate(b,c)}function o(b){var a=0;b.each(function(){var b=parseFloat(this.value);a+=isNaN(b)?0:b});return a}function f(b,
a){for(var h in a)a.hasOwnProperty(h)&&(b=b.replace(RegExp("#\\{"+h+"\\}","g"),a[h]));return p(b)}function q(b){var b=a(b),c=b.prop("id"),h=a.validity.settings.defaultFieldName;if(b.prop("title").length)h=b.prop("title");else if(/^([A-Z0-9][a-z]*)+$/.test(c))h=c.replace(/([A-Z0-9])[a-z]*/g," $&");else if(/^[a-z0-9]+(_[a-z0-9]+)*$/.test(c)){b=c.split("_");for(c=0;c<b.length;++c)b[c]=p(b[c]);h=b.join(" ")}return a.trim(h)}function p(b){return b.substring?b.substring(0,1).toUpperCase()+b.substring(1,
b.length):b}var r;a.validity={settings:a.extend({outputMode:"tooltip",scrollTo:!1,modalErrorsClickable:!0,defaultFieldName:"This field",elementSupport:":text, :password, textarea, select, :radio, :checkbox, input[type='hidden'], input[type='tel'], input[type='email']",argToString:function(b){return b.getDate?[b.getMonth()+1,b.getDate(),b.getFullYear()].join("/"):b+""},debugPrivates:!1},{}),patterns:{integer:/^\d+$/,date:/^((0?\d)|(1[012]))[\/-]([012]?\d|30|31)[\/-]\d{1,4}$/,email:/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
usd:/^\$?((\d{1,3}(,\d{3})*)|\d+)(\.(\d{2})?)?$/,url:/^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i,
number:/^[+-]?(\d+(\.\d*)?|\.\d+)([Ee]-?\d+)?$/,zip:/^\d{5}(-\d{4})?$/,phone:/^[2-9]\d{2}-\d{3}-\d{4}$/,guid:/^(\{?([0-9a-fA-F]){8}-(([0-9a-fA-F]){4}-){3}([0-9a-fA-F]){12}\}?)$/,time12:/^((0?\d)|(1[012])):[0-5]\d?\s?[aApP]\.?[mM]\.?$/,time24:/^(20|21|22|23|[01]\d|\d)(([:][0-5]\d){1,2})$/,nonHtml:/^[^<>]*$/},messages:{require:"#{field} is required.",match:"#{field} is in an invalid format.",integer:"#{field} must be a positive, whole number.",date:"#{field} must be formatted as a date. (mm/dd/yyyy)",
email:"#{field} must be formatted as an email.",usd:"#{field} must be formatted as a US Dollar amount.",url:"#{field} must be formatted as a URL.",number:"#{field} must be formatted as a number.",zip:"#{field} must be formatted as a zipcode ##### or #####-####.",phone:"#{field} must be formatted as a phone number ###-###-####.",guid:"#{field} must be formatted as a guid like {3F2504E0-4F89-11D3-9A0C-0305E82C3301}.",time24:"#{field} must be formatted as a 24 hour time: 23:00.",time12:"#{field} must be formatted as a 12 hour time: 12:00 AM/PM",
lessThan:"#{field} must be less than #{max}.",lessThanOrEqualTo:"#{field} must be less than or equal to #{max}.",greaterThan:"#{field} must be greater than #{min}.",greaterThanOrEqualTo:"#{field} must be greater than or equal to #{min}.",range:"#{field} must be between #{min} and #{max}.",tooLong:"#{field} cannot be longer than #{max} characters.",tooShort:"#{field} cannot be shorter than #{min} characters.",nonHtml:"#{field} cannot contain HTML characters.",alphabet:"#{field} contains disallowed characters.",
minCharClass:"#{field} cannot have more than #{min} #{charClass} characters.",maxCharClass:"#{field} cannot have less than #{min} #{charClass} characters.",equal:"Values don't match.",distinct:"A value was repeated.",sum:"Values don't add to #{sum}.",sumMax:"The sum of the values must be less than #{max}.",sumMin:"The sum of the values must be greater than #{min}.",radioChecked:"The selected value is not valid.",generic:"Invalid."},out:{start:function(){this.defer("start")},end:function(b){this.defer("end",
b)},raise:function(b,a){this.defer("raise",b,a)},raiseAggregate:function(b,a){this.defer("raiseAggregate",b,a)},defer:function(b){var c=a.validity,c=c.outputs[c.settings.outputMode];c[b].apply(c,Array.prototype.slice.call(arguments,1))}},charClasses:{alphabetical:/\w/g,numeric:/\d/g,alphanumeric:/[A-Za-z0-9]/g,symbol:/[^A-Za-z0-9]/g},outputs:{},__private:e,setup:function(b){this.settings=a.extend(this.settings,b);this.__private=this.settings.debugPrivates?r:e},report:null,isValidating:function(){return!!this.report},
start:function(){this.out.start();this.report={errors:0,valid:!0}},end:function(){var b=this.report||{errors:0,valid:!0};this.report=null;this.out.end(b);return b},clear:function(){this.start();this.end()}};a.fn.extend({validity:function(b){return this.each(function(){if(this.tagName.toLowerCase()=="form"){var c=null;typeof b=="string"?c=function(){a(b).require()}:a.isFunction(b)&&(c=b);b&&a(this).bind("submit",function(){a.validity.start();c();return a.validity.end().valid})}})},require:function(b){return d(this,
function(b){return!!a(b).val().length},b||a.validity.messages.require)},match:function(b,c){if(!c)c=a.validity.messages.match,typeof b==="string"&&a.validity.messages[b]&&(c=a.validity.messages[b]);typeof b=="string"&&(b=a.validity.patterns[b]);return d(this,a.isFunction(b)?function(a){return!a.value.length||b(a.value)}:function(a){if(b.global)b.lastIndex=0;return!a.value.length||b.test(a.value)},c)},range:function(b,c,h){return d(this,b.getTime&&c.getTime?function(a){a=new Date(a.value);return a>=
new Date(b)&&a<=new Date(c)}:b.substring&&c.substring&&Big?function(a){a=new Big(a.value);return a.greaterThanOrEqualTo(new Big(b))&&a.lessThanOrEqualTo(new Big(c))}:function(a){a=parseFloat(a.value);return a>=b&&a<=c},h||f(a.validity.messages.range,{min:a.validity.settings.argToString(b),max:a.validity.settings.argToString(c)}))},greaterThan:function(b,c){return d(this,b.getTime?function(a){return new Date(a.value)>b}:b.substring&&Big?function(a){return(new Big(a.value)).greaterThan(new Big(b))}:
function(a){return parseFloat(a.value)>b},c||f(a.validity.messages.greaterThan,{min:a.validity.settings.argToString(b)}))},greaterThanOrEqualTo:function(b,c){return d(this,b.getTime?function(a){return new Date(a.value)>=b}:b.substring&&Big?function(a){return(new Big(a.value)).greaterThanOrEqualTo(new Big(b))}:function(a){return parseFloat(a.value)>=b},c||f(a.validity.messages.greaterThanOrEqualTo,{min:a.validity.settings.argToString(b)}))},lessThan:function(b,c){return d(this,b.getTime?function(a){return new Date(a.value)<
b}:b.substring&&Big?function(a){return(new Big(a.value)).lessThan(new Big(b))}:function(a){return parseFloat(a.value)<b},c||f(a.validity.messages.lessThan,{max:a.validity.settings.argToString(b)}))},lessThanOrEqualTo:function(b,c){return d(this,b.getTime?function(a){return new Date(a.value)<=b}:b.substring&&Big?function(a){return(new Big(a.value)).lessThanOrEqualTo(new Big(b))}:function(a){return parseFloat(a.value)<=b},c||f(a.validity.messages.lessThanOrEqualTo,{max:a.validity.settings.argToString(b)}))},
maxLength:function(b,c){return d(this,function(a){return a.value.length<=b},c||f(a.validity.messages.tooLong,{max:b}))},minLength:function(b,c){return d(this,function(a){return a.value.length>=b},c||f(a.validity.messages.tooShort,{min:b}))},alphabet:function(b,c){var h=[];return d(this,function(a){for(var c=0;c<a.value.length;++c)if(b.indexOf(a.value.charAt(c))==-1)return h.push(a.value.charAt(c)),!1;return!0},c||f(a.validity.messages.alphabet,{chars:h.join(", ")}))},minCharClass:function(b,c,h){typeof b==
"string"&&(b=b.toLowerCase(),a.validity.charClasses[b]&&(b=a.validity.charClasses[b]));return d(this,function(a){return(a.value.match(b)||[]).length>=c},h||f(a.validity.messages.minCharClass,{min:c,charClass:b}))},maxCharClass:function(b,c,h){typeof b=="string"&&(b=b.toLowerCase(),a.validity.charClasses[b]&&(b=a.validity.charClasses[b]));return d(this,function(a){return(a.value.match(b)||[]).length<=c},h||f(a.validity.messages.maxCharClass,{max:c,charClass:b}))},nonHtml:function(b){return d(this,
function(b){return a.validity.patterns.nonHtml.test(b.value)},b||a.validity.messages.nonHtml)},equal:function(b,c){var d=(this.reduction||this).filter(a.validity.settings.elementSupport),e=function(a){return a},g=a.validity.messages.equal;if(d.length){a.isFunction(b)?(e=b,typeof c=="string"&&(g=c)):typeof b=="string"&&(g=b);var f=a.map(d,function(a){return e(a.value)}),l=f[0],m=!0,k;for(k in f)f[k]!=l&&(m=!1);if(!m)i(d,g),this.reduction=a([])}return this},distinct:function(b,c){var d=(this.reduction||
this).filter(a.validity.settings.elementSupport),e=function(a){return a},g=a.validity.messages.distinct,f=[],l=[],m=!0;if(d.length){a.isFunction(b)?(e=b,typeof c=="string"&&(g=c)):typeof b=="string"&&(g=b);for(var k=a.map(d,function(a){return e(a.value)}),n=0;n<k.length;++n)if(k[n].length){for(var j=0;j<f.length;++j)f[j]==k[n]&&(m=!1,l.push(k[n]));f.push(k[n])}if(!m){l=a.unique(l);f=0;for(m=l.length;f<m;++f)i(d.filter("[value='"+l[f]+"']"),g);this.reduction=a([])}}return this},sum:function(b,c){var d=
(this.reduction||this).filter(a.validity.settings.elementSupport);if(d.length&&b!=o(d))i(d,c||f(a.validity.messages.sum,{sum:b})),this.reduction=a([]);return this},sumMax:function(b,c){var d=(this.reduction||this).filter(a.validity.settings.elementSupport);if(d.length&&b<o(d))i(d,c||f(a.validity.messages.sumMax,{max:b})),this.reduction=a([]);return this},sumMin:function(b,c){var d=(this.reduction||this).filter(a.validity.settings.elementSupport);if(d.length&&b>o(d))i(d,c||f(a.validity.messages.sumMin,
{min:b})),this.reduction=a([]);return this},radioChecked:function(b,c){var d=(this.reduction||this).filter(a.validity.settings.elementSupport);d.is(":radio")&&d.find(":checked").val()!=b&&i(d,c||a.validity.messages.radioChecked)},radioNotChecked:function(b,c){var d=(this.reduction||this).filter(a.validity.settings.elementSupport);d.is(":radio")&&d.filter(":checked").val()==b&&i(d,c||a.validity.messages.radioChecked)},checkboxChecked:function(b){var c=(this.reduction||this).filter(a.validity.settings.elementSupport);
c.is(":checkbox")&&!c.is(":checked")&&i(c,b||a.validity.messages.radioChecked)},assert:function(b,c){var e=this.reduction||this;if(e.length)if(a.isFunction(b))return d(this,b,c||a.validity.messages.generic);else if(!b)i(e,c||a.validity.messages.generic),this.reduction=a([]);return this},fail:function(a){return this.assert(!1,a)}});r={validate:d,addToReport:g,raiseError:j,raiseAggregateError:i,numericSum:o,format:f,infer:q,capitalize:p}})(jQuery);(function(a){a.validity.outputs.tooltip={tooltipClass:"validity-tooltip",start:function(){a("."+a.validity.outputs.tooltip.tooltipClass).remove()},end:function(e){!e.valid&&a.validity.settings.scrollTo&&a("body").scrollTop(a("."+a.validity.outputs.tooltip.tooltipClass).offset().top)},raise:function(e,d){var g=e.offset();g.left+=e.width()+18;g.top+=8;a('<div class="validity-tooltip">'+d+'<div class="validity-tooltip-outer"><div class="validity-tooltip-inner"></div></div></div>').click(function(){e.focus();
a(this).fadeOut()}).css(g).hide().appendTo("body").fadeIn()},raiseAggregate:function(a,d){a.length&&this.raise(a.filter(":last"),d)}}})(jQuery);
(function(a){function e(a){return a.attr("id").length?a.attr("id"):a.attr("name")}a.validity.outputs.label={cssClass:"error",start:function(){a("."+a.validity.outputs.label.cssClass).remove()},end:function(d){if(!d.valid&&a.validity.settings.scrollTo)location.hash=a("."+a.validity.outputs.label.cssClass+":eq(0)").attr("for")},raise:function(d,g){var j="."+a.validity.outputs.label.cssClass+"[for='"+e(d)+"']";a(j).length?a(j).text(g):a("<label/>").attr("for",e(d)).addClass(a.validity.outputs.label.cssClass).text(g).click(function(){d.length&&
d[0].select()}).insertAfter(d)},raiseAggregate:function(d,e){d.length&&this.raise(a(d.get(d.length-1)),e)}}})(jQuery);
(function(a){a.validity.outputs.modal={start:function(){a(".validity-modal-msg").remove()},end:function(e){if(!e.valid&&a.validity.settings.scrollTo)location.hash=a(".validity-modal-msg:eq(0)").attr("id")},raise:function(e,d){if(e.length){var g=e.offset();e.get(0);g={left:parseInt(g.left+e.width()+4,10)+"px",top:parseInt(g.top-10,10)+"px"};a("<div/>").addClass("validity-modal-msg").css(g).text(d).click(a.validity.settings.modalErrorsClickable?function(){a(this).remove()}:null).appendTo("body")}},
raiseAggregate:function(e,d){e.length&&this.raise(a(e.get(e.length-1)),d)}}})(jQuery);
(function(a){var e=[];a.validity.outputs.summary={start:function(){a(".validity-erroneous").removeClass("validity-erroneous");e=[]},end:function(){a(".validity-summary-container").hide().find("ul").html("");if(e.length){for(var d=0;d<e.length;++d)a("<li/>").text(e[d]).appendTo(".validity-summary-container ul");a(".validity-summary-container").show();if(a.validity.settings.scrollTo)location.hash=a(".validity-erroneous:eq(0)").attr("id")}},raise:function(a,g){e.push(g);a.addClass("validity-erroneous")},
raiseAggregate:function(a,e){this.raise(a,e)},container:function(){document.write('<div class="validity-summary-container">The form didn\'t submit for the following reason(s):<ul></ul></div>')}}})(jQuery);