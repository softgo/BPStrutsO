/*
* 获取HTML传输来的参数的值.
*/
function request(params) {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = { };
	for (i=0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[params.toLowerCase()];
	if (typeof (returnValue) == "undefined") {
		return "";
	} else {
		return returnValue;
	}
}

/*
* 设置 cookie 的值.
*/
function addCookie(objName, objValue,objTime,tag){//添加cookie 
    var str = objName + "=" + escape(objValue);
    var date = new Date();
    if(tag=="D" || tag=="d"){
        if (objTime > 0) {
            var ms = objTime*24*60*60*1000;
            date.setTime(date.getTime() + ms);
            str += "; expires=" + date.toGMTString();
        }  
    }
    if (tag=="H" || tag=="h") {
        if (objTime > 0) {
            var ms = objTime*60*60*1000;
            date.setTime(date.getTime() + ms);
            str += "; expires=" + date.toGMTString();
        }
    }
    if (tag=="M" || tag=="m"){
        if (objTime > 0) {
            var ms = objTime*60*1000;
            date.setTime(date.getTime() + ms);
            str += "; expires=" + date.toGMTString();
        }
    }
    // 设置cookie.
    document.cookie = str;
};

/*
* 读取 cookie 的值.
*/
function getCookie(objName){//获取指定名称的cookie的值 
    var arrStr = document.cookie.split("; "); 
    for (var i = 0; i < arrStr.length; i++) { 
        var temp = arrStr[i].split("="); 
        if (temp[0] == objName) {
            return unescape(temp[1]); 
        }
    } 
};

/*
* 删除 cookie 的值.
*/
function delCookie(name,tag) {  
    addCookie(name, "", -1,tag);  
};
