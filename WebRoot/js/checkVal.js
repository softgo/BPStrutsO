//check the user's input value .
/**
1.new RegExp("");  直接使用,有""的！

只能输入数字："^[0-9]*$"。                                  var temp = new RegExp(^[0-9]*$);             var temp = new RegExp(/\D+/g);
只能输入n位的数字："^\d{n}$"。                               var temp = new RegExp(^\d{n}$);
只能输入至少n位的数字："^\d{n,}$"。                          var temp = new RegExp(^\d{n,}$);
只能输入m~n位的数字：。"^\d{m,n}$"                       var temp = new RegExp(^\d{m,n}$);
只能输入零和非零开头的数字："^(0|[1-9][0-9]*)$"。           var temp = new RegExp(^(0|[1-9][0-9]*)$);
只能输入有两位小数的正实数："^[0-9]+(.[0-9]{2})?$"。        var temp = new RegExp(^[0-9]+(.[0-9]{2})?$);
只能输入有1~3位小数的正实数："^[0-9]+(.[0-9]{1,3})?$"。    var temp = new RegExp(^[0-9]+(.[0-9]{1,3})?$);
只能输入非零的正整数："^\+?[1-9][0-9]*$"。                  var temp = new RegExp(^\+?[1-9][0-9]*$);
只能输入非零的负整数："^\-[1-9][]0-9"*$。                   var temp = new RegExp(^\-[1-9][]0-9"*$);
只能输入长度为3的字符："^.{3}$"。                            var temp = new RegExp(^.{3}$);
只能输入由26个英文字母组成的字符串："^[A-Za-z]+$"。          var temp = new RegExp(^[A-Za-z]+$);
只能输入由26个大写英文字母组成的字符串："^[A-Z]+$"。          var temp = new RegExp(^[A-Z]+$);
只能输入由26个小写英文字母组成的字符串："^[a-z]+$"。          var temp = new RegExp(^[a-z]+$);
只能输入由数字和26个英文字母组成的字符串："^[A-Za-z0-9]+$"。 var temp = new RegExp(^[A-Za-z0-9]+$);
只能输入由数字、26个英文字母或者下划线组成的字符串："^\w+$"。  var temp = new RegExp(^\w+$);                var temp = new RegExp(/[^\w\.\/]/ig);    
验证用户密码："^[a-zA-Z]\w{5,17}$"正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线。 var temp = new RegExp(^[a-zA-Z]\w{5,17}$); 
验证是否含有^%&',;=?$\"等字符："[^%&',;=?$\x22]+"。       var temp = new RegExp();
只能输入汉字："^[\u4e00-\u9fa5]{0,}$" .                 var temp = new RegExp(^[\u4e00-\u9fa5]{0,}$);var temp = new RegExp(/[^\u4E00-\u9FA5]/g);
只允许输入英文字母、数字和=@# .                           var temp = new RegExp(/[^\w=@#]|_/ig);

2.RegExp 是正则表达式的缩写,当您检索某个文本时，可以使用一种模式来描述要检索的内容。RegExp 就是这种存储模式。该对象有 3 个方法：test()、exec() 以及 compile()。
a.test() 方法检索字符串中的指定值。返回值是 true 或 false。
b.exec() 方法检索字符串中的指定值。返回值是被找到的值。如果没有发现匹配，则返回 null。 
c.compile() 方法用于改变 RegExp, compile() 既可以改变检索模式，也可以添加或删除第二个参数。
*/

String.prototype.Trim = function() { 
		return this.replace(/(^\s*)|(\s*$)/g, "");
	} 

/**
  * 是否是正确IP,
  * true 是,false 否
  */
function isIp(ipStr) {
	var pattern = /^(25[0-4]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-4]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-4]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-4]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])$/;
	if (pattern.test(ipStr)) {
		return true;
	} else {
		return false;
	}
} 

/**
 * mac地址验证,
  * true 是,false 否
 */
function isMac(str) {
	var regMac = "[A-F0-9][A-F0-9]-[A-F0-9][A-F0-9]-[A-F0-9][A-F0-9]-[A-F0-9][A-F0-9]-[A-F0-9][A-F0-9]-[A-F0-9][A-F0-9]";
	var reg = new RegExp(regMac);
	if (reg.test(str)) {
		return true;
	}else{
		return false;
	}
}

/**
 * 验证子网掩码,
 * @param str
 * @returns {Boolean}
 */
function isSubetMask(str) {
	var myReg = /(255|254|252|248|240|224|192|128|0)\.0\.0\.0|255\.(255|254|252|248|240|224|192|128|0)\.0\.0|255\.255\.(255|254|252|248|240|224|192|128|0)\.0|255\.255\.255\.(255|254|252|248|240|224|192|128|0)/;
	var temp = new RegExp(myReg);
	if(temp.test(str)){
		return true;
	}else{
		return false;		
	}
}

/**
 * 邮箱验证,
 * true 是,false 否
 */
function isEmail(emailStr) {
	var regEmail = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	if (regEmail.test(emailStr)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 电话号码验证,
 * true 是,false 否
 */
function isPhone(phoneStr){
	var phone=/^(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	if(phone.test(phoneStr)){
		return true;
	}else{
		return false;
	}
}

/**
 * 判断是否为正整数,
 * true 是,false 否
 */
function isNum(str) {
	var ismin = /^[1-9]\d*|0$/;
	if (!isNaN(str)) {
		var min = parseInt(str);
		if (ismin.test(str) &&  min >0 ) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}

/**
 * 是否是个合法的密码,
 * true 是,false 否
 */
function isPwd(pwdStr) {
	var isPsw = /^[\u4e00-\u9fa5\da-zA-Z\-\_]+$/;
	var reg = new RegExp(isPsw);
	if (reg.test(pwdStr)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 是否是个年龄,
 * true 是,false 否
 */
function isAge(ageStr) {
	var isAge = /^[0-9]*[1-9][0-9]*$/;
	if (!isNaN(ageStr)) {
		var age = parseInt(ageStr);
		if (isAge.test(ageStr) && age >= 1 && age <= 100) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}

/**
 * 是否是数字或字母,
 * true 是,false 否
 */
function isNumOrLetter(str) {
	var regu = "^[0-9a-zA-Z_]+$";
	var reg = new RegExp(regu);
	if (reg.test(str)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 只能输入字母,
 * true 是,false 否
 */
function isLetter(str) {
	var regu = "^[a-zA-Z]+$";
	var reg = new RegExp(regu);
	if (reg.test(str)) {
		return true;
	} else {
		return false;
	}
}



/**
 * 是否是数字,字母或下划线
 * true 是,false 否
 */
function isString(str){
	var temp = new RegExp("/[^\w\.\/]/ig"); 
	if(temp.test(str)){
		return false;
	}else{
		return false;
	}
}

/**
 * 是否为空,
 * true 是,false 否
 */
function isNull(str) {
	if (str == null || str == "") {
		return false;
	} else {
		return true;
	}
}

/***
 * 只能输入汉字,
 * true 是,false 否
 */
function isWord(str){
	var temp = new RegExp("^[\u4e00-\u9fa5]{0,}$");
	if(temp.test(str)){
		return true;
	}else{
		return false;
	}
}

/**
 * 身份证号码,
 * true 是,false 否
 */
function isManId(str){
	var temp = new RegExp("^\d{15}|\d{18}$");
	if(temp.test(str)){
		return true;
	}else{
		return false;
	}
}

/**
 * 匹配url表达,
 * true 是,false 否
 */
function isUrl(str){
	var temp = new RegExp("[a-zA-z]+://[^\s]*");
	if(temp.test(str)){
		return true;
	}else{
		return false;
	}
}



