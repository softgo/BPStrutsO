/**
* 全选
* chkName checkbox的name
*/
function choiceAll(chkName)
{
	$("[name='"+chkName+"']").attr("checked",true);//全选
}

/**
* 反选
* chkName checkbox的name
*/
function choiceReverse(chkName)
{
	$.each( $("[name='"+chkName+"']"), //循环每个checkbox
        function(){ 
						$(this).attr("checked",!$(this).attr("checked"));
        }
  ); 
}

/**
*全选、反选
*/
function choiceOrReverse(clickName,chkName){
   if($("[name='"+clickName+"']").attr("checked")==true){
    $("[name='"+chkName+"']").attr("checked",true);//å¨é
    }else{
    $("[name='"+chkName+"']").attr("checked",false);//å¨é
    }
}

/**
* 得到id串
* chkName checkbox的name
*/
function getChoiceParam(chkName)
{
    var tempSel = "",checkboxIdStr = "";
    $.each( $("[name='"+chkName+"']"), //循环每个checkbox
        function(){ 
            if($(this).attr("checked")==true)
            {
                tempSel = tempSel + $(this).val() + ",";			    
                checkboxIdStr = tempSel.substring(0,tempSel.length-1);	
            }
        }
    ); 
    
    return checkboxIdStr;
}

function getChoiceParamValue(chkName)
{
    var tempSel = "",checkboxIdStr = "";
    $.each( $("[name='"+chkName+"']"), //循环每个checkbox
        function(){ 
            if($(this).attr("checked")==true)
            {
                tempSel = tempSel + $(this).val() + "###";			    
                checkboxIdStr = tempSel.substring(0,tempSel.length-3);	
            }
        }
    ); 
    
    return checkboxIdStr;
}

//--
function changeStyle(elementID,toStyle) {
  document.getElementById(elementID).className=toStyle;
}

function getRadioValue(formName,radioName) {
  var radios = eval("document."+formName+"."+radioName);
  for (var i = 0; i < radios.length; i++) {
    if (radios[i].checked) {
      return radios[i].value;
    }
  }
}

function getRadioValueByName(radioName) {
   var radioValues = document.getElementsByName(radioName);
   for (var i = 0; i < radioValues.length; i++) {
    if (radioValues[i].checked) {
      return radioValues[i].value;
    }
  }
}

function getCheckBoxValue(checkBoxName) {
  var checkBox = document.getElementById(checkBoxName);
  if (checkBox.checked) {
    return checkBox.value;
  }
  return "";
}

function getCheckBoxSingleValue(checkBoxId) {
  var checkBoxs = document.getElementsByName(checkBoxId);
  for (var i = 0; i < checkBoxs.length; i++) {
    if (checkBoxs[i].checked) {
      return checkBoxs[i].value;
    }
  }
  return "";
}

function getResponseXMLMsgs(responseXML) {
  return responseXML.getElementsByTagName("messages")[0];
}

function getResponseXMLMsgsCodeid(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  var x = messages.childNodes;
  for (i = 0; i < x.length; i++) {
    if (x[i].nodeName == "codeid") {
      return x[i].childNodes[0].nodeValue;
    }
  }
  return "E000";
}

function getResponseXMLMsgsMessage(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  var x = messages.childNodes;
  for (i = 0; i < x.length; i++) {
    if (x[i].nodeName == "message") {
      return x[i].childNodes[0].nodeValue;
    }
  }
  return "Error";
}

function getResponseXMLMsgsText(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  var x = messages.childNodes;
  for (i = 0; i < x.length; i++) {
    if (x[i].nodeName == "text") {
      return x[i].childNodes[0].nodeValue;
    }
  }
  return "";
}

function hiddenElement(elementName) {
  document.getElementById(elementName).style.display = "none";
}

function displayElement(elementName) {
  document.getElementById(elementName).style.display = "block";
}

function filterString(txt) {
  var re = /\+/;
  return txt.replace(re,"%2B");
}

function getElementValue(elementName) {
  return $(elementName).value;
}

function getElementFilterValue(elementName) {
  return filterString($(elementName).value);
}

function handleEnter(field, event) {
  var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
  if (keyCode == 13) {
    return false;
  }
  return true;
}

function startsWith(txt,tar) {
  if (txt.indexOf(tar) == 0) {
    return true;
  }
  else {
    return false;
  }
}

function endsWith(txt,tar) {
  var num = txt.lastIndexOf(tar);
  if ((num != -1) && (tar == txt.substring(num,txt.length))) {
    return true;
  }
  else {
    return false;
  }
}

function getActionMappingName(action) {
  var value = action;
  var question = action.indexOf("?");
  if (question >= 0) {
    value = value.substring(0, question);
  }

  var slash = value.lastIndexOf("/");
  var period = value.lastIndexOf(".");
  if ((period >= 0) && (period > slash)) {
    value = value.substring(0, period);
  }

  return startsWith(value,"/") ? value : ("/" + value);
}

function getActionMappingURL(action) {
  var value = contextPath;
  var servletMapping = servletMappingStr;
  var queryString;
  var question = action.indexOf("?");
  if (question >= 0) {
    queryString = action.substring(question);
  }

  var actionMapping = getActionMappingName(action);
  if (startsWith(servletMapping,"*.")) {
    value += actionMapping;
    value += servletMapping.substring(1);
  }
  else if (endsWith(servletMapping,"/*")) {
    value += servletMapping.substring(0, servletMapping.length - 2);
    value += actionMapping;
  }
  else if (servletMapping == "/") {
    value += actionMapping;
  }
  if (queryString != undefined) {
    value += queryString;
  }
  return value;
}

function getActionName(url) {
	var question = url.indexOf("?");
	if (question > 0) {
		return url.substring(0, question);
	}
	else {
		return url;
	}
}

function getActionPars(url) {
	var question = url.indexOf("?");
	if (question > 0) {
		return url.substring(question+1, url.length);
	}
	else {
		var d = new Date();
		var t = d.getTime();
		return "timestamp="+t;
	}
}

function getExtName(fileName){
    if(fileName.lastIndexOf(".")<0) return "";
    return fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
}

function showExeMsg() {
  var loade = document.getElementById("exeingdiv");
  if (loade == null) {
    var el = document.createElement('DIV');
    el.setAttribute("id","exeingdiv");
    el.className = "exeMsg";
    el.innerHTML = exeing;
    document.body.appendChild(el);
    loade = el;
  }

  loade.style.display = "block";
  loade.style.top = document.documentElement.scrollTop + 1 + "px";
}

function hiddenExeMsg() {
  var loade = document.getElementById("exeingdiv");
  if (loade != null) {
    loade.style.display = "none";
  }
}

function getResponseJsonMsgsCodeid(responseText) {
  var jsonObj = eval('(' + responseText + ')');
  return jsonObj.codeid;
}

var JsonMsgObj = function(responseText) {
  this.json = eval('(' + responseText + ')');
}

JsonMsgObj.prototype.getCodeid = function() {
  return this.json.codeid;
}

JsonMsgObj.prototype.getMessage = function() {
  return this.json.message;
}

JsonMsgObj.prototype.getText = function() {
  return this.json.text;
}

String.prototype.format = function() {
    var str = this;
    for(var i=0;i<arguments.length;i++) {
        var re = new RegExp('\\{' + (i) + '\\}','gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
}

function ismail(mail)
{
	if(!(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail)))
	{
		return false;
	}
	
	 return true;
}

function gohome(){
   window.location='Login!welcome.action';
}

function isPostcode(code)
{

  if (isNaN(code)) 
	{
		//alert("����Ĳ������֣�"); 
		return false;
	}  
  else if(code.length!=6) {return false;}
  
  return true;
} 

// �ж������Ƿ������֤
function isCardNo(num)
{
	if (isNaN(num)) 
	{
		//alert("����Ĳ������֣�"); 
		return false;
	}

	var len = num.length, re; 

	if (len == 15)
		re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
	else if (len == 18)
		re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);
	else 
	{
		//alert("���������λ��ԣ�"); 
		return false;
	}

	var a = num.match(re);

	if (a != null)
	{
		if (len==15)
		{
			var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
			var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
		}
		else
		{
			var D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
			var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
		}

		if (!B) 
	  {
			//alert("��������֤�� "+ a[0] +" ��������ڲ��ԣ�"); 
			return false;
	  }
	}

	return true;
}



// �ж��ϴ����ļ��Ƿ���Ҫ����ļ�����
// document : �ϴ��ļ���ֵ  		type :  Ҫ�������
// ��: <input type=file onchange="isType(this.value, 'doc')">
function isType(document, type)
{
	if(document.match(/^(.*)(\.)(.{1,8})$/)[3] == type)
		return true;
	else
		return false;
}




//		�ж�����ĵ绰�����Ƿ��ϸ�ʽ
//����(1)�绰���������֡�"("��")"��"-"����
//����(2)�绰����Ϊ3��8λ
//����(3)���绰�����а�����ţ���ô���Ϊ��λ����λ
//����(4)�����"("��")"��"-"������ָ�
//����(5)�ƶ��绰����Ϊ11��12λ�����Ϊ12λ,��ô��һλΪ0
//����(6)11λ�ƶ��绰����ĵ�һλ�͵ڶ�λΪ"13"
//����(7)12λ�ƶ��绰����ĵڶ�λ�͵���λΪ"13"
function PhoneCheck(s) 
{
	var str=s;
	var reg=/(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/
	
	if(!(reg.test(str)))
	{
		return false;
	}
	else
		return true;
}




//У���ֻ���룺���������ֿ�ͷ���������⣬�ɺ��С�-��
function isMobile(s)
{
	var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	if (!patrn.exec(s)) 
	{	
		//alert("�������");
		return false
	}
	else
		return true
}


function   is_int(field)  
{   
  var   Ret   =   true;   
  var   NumStr="0123456789";   
  var   chr;   
    
  for   (i=0;i<field.length;++i)   
  {   
  chr=field.charAt(i);   
  if   (NumStr.indexOf(chr,0)==-1)   
  {   
  Ret=false;   
  }   
  }   
  return(Ret);   
} 
function   is_float(field)  
{   
  var   Ret   =   true;   
  var   NumStr="0123456789.";   
  var   chr;   
    
  for   (i=0;i<field.length;++i)   
  {   
  chr=field.charAt(i);   
  if   (NumStr.indexOf(chr,0)==-1)   
  {   
  Ret=false;   
  }   
  }   
  return(Ret);   
}


function getPageCount(pageSize, count)
{
	if(count % pageSize == 0) 
		return count / pageSize; 
    else 
		return count / pageSize + 1;  
}


function checkTable(tableName, keybox)
{
	var product = document.getElementById(keybox); 
	if(product.checked)
	{
		SelAllClick(tableName);
	}
	else
	{
		RevertAllClick(tableName);
	}
} 
 
 

//???????????????
function SelAllClick(tabID)
{
	var Tab = document.all(tabID);
	if(Tab == null)
		return;
	var Chks = Tab.all.tags("input");
	if(Chks.length)
	{
		for(i=0;i<Chks.length;i++)
		{
			if(Chks[i].type=="checkbox")
			{
				Chks[i].checked = true;
			}
		}
	}
	else if(Chks)
	{
		if(Chks.type == "checkbox")
		{
			Chks.checked = true;
		}
	}
		
}


//???????????????
function RevertAllClick(tabID)
{
	var Tab = document.all(tabID);
	if(Tab == null)
		return;
	var Chks = Tab.all.tags("input");
	if(Chks.length)
	{
		for(i=0;i<Chks.length;i++)
		{
			if(Chks[i].type=="checkbox")
			{
				Chks[i].checked = false;
			}
		}
	}
	else if(Chks)
	{
		if(Chks.type == "checkbox")
		{
			Chks.checked = false;
		}
	}
		
}



//???????????????
function RevertClick(tabID)
{
	var Tab = document.all(tabID);
	if(Tab == null)
		return;
	var Chks = Tab.all.tags("input");
	if(Chks.length)
	{
		for(i=0;i<Chks.length;i++)
		{
			if(Chks[i].type=="checkbox")
			{
				Chks[i].checked = !Chks[i].checked;
			}
		}
	}
	else if(Chks)
	{
		if(Chks.type == "checkbox")
		{
			Chks.checked = !Chks.checked;
		}
	}
}

// ???????????????????????????????????????????????????
function isMax(text, max)
{
	if(!(text >= max))
	{
		alert("?????????????????????");
		return;
	}
}


// ???????????????????????????????????????????????????
function isMin(text, min)
{
	if(!(text <= min))
	{
		alert("?????????????????????");
		return;
	}
}

//????????????????????????
function isEmpty(str) 
{
    if ((jtrim(str)==null)||((jtrim(str)).length==0)) 
    {
	return false;
    }
    else 
	return true;
}




//??????????????????????????????
function jtrim(str)
{     
     while (str.charAt(0)==" ")
          {str=str.substr(1);}      
     while (str.charAt(str.length-1)==" ")
         {str=str.substr(0,str.length-1);}
     return(str);
}

function DateDiff(sDate1, sDate2){  //sDate1和sDate2是2002-12-18格式

    var aDate, oDate1, oDate2, iDays

    aDate = sDate1.split("-")

    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])  //转换为12-18-2002格式

    aDate = sDate2.split("-")

    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0])

    //alert(oDate1 - oDate2)

    //iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 /24)  //把相差的毫秒数转换为天数
    iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 /24)  //把相差的毫秒数转换为天数

    return iDays
  } 

//--ajax--ajax--ajax--ajax--ajax----ajax----ajax--
var XMLHttpReq;




 	//??????XMLHttpRequest??????       
function createXMLHttpRequest() {
    if (window.XMLHttpRequest) { //Mozilla ?????????
        XMLHttpReq = new XMLHttpRequest();
    } else {
        if (window.ActiveXObject) { // IE?????????
            try {
                XMLHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
            }
            catch (e) {
                try {
                    XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
                }
                catch (e) {
                }
            }
        }
    }
}

	//??????????????????
function sendRequest(url) {
    createXMLHttpRequest();
    XMLHttpReq.open("GET", url, true);
    XMLHttpReq.onreadystatechange = processResponse;//??????????????????
    XMLHttpReq.send(null);  // ????????????
}

	// ????????????????????????
function processResponse() {
    
    if (XMLHttpReq.readyState == 4) { // ??????????????????
        if (XMLHttpReq.status == 200) { // ?????????????????????????????????????????????
            
             var temp = XMLHttpReq.responseText;
             
             var res = temp.split(",");
            if (res[0] == 1) {
            	alert(res[1]);
            	if(window.paginator)
	              paginator.research();
	            else
	              research();
            }else{
            	alert(res[1]);      	
            }
        } else { //???????????????
            alert(res[1]);
        }
    }
}


function deleteAll(url,msg)
	{
	  //alert("aaaaa"); 
      var formObj=document.forms[0];
      //alert(formObj.name);
      var tempSel="";
  
	  for (i=0; i< formObj.length;i++)
	  {
		if (formObj.item(i).type == "checkbox")
		{
			if (formObj.item(i).checked == true)
			{   
			    
				tempSel = tempSel + formObj.item(i).value+',';	
					
			}
	    }
	  }
	
	if (tempSel != "")
	{  
	    // alert(tempSel)	
	    var idArray=tempSel.split(",");
	    for(i=0;i<idArray.length;i++)
	    {   
	        if(idArray[i]!="")
	        {  
	           url = url + "&id="+idArray[i]
	        } 
	    }
	    //alert(url);
	    sendRequest(url);
	    
	}
	else
	{
	    alert(msg);
	}
}

function deleteAll2(url,msg,checkboxname)
	{
	   
      //var formObj=document.forms[0];
      var   formObj=document.getElementsByName(checkboxname); 
      //alert(formObj.name);
      var tempSel="";
  
	  for (i=0; i< formObj.length;i++)
	  {
		if (formObj.item(i).checked == true)
			{   
			    
				tempSel = tempSel + formObj.item(i).value+',';	
			}
	 }
	
	if (tempSel != "")
	{  
	    // alert(tempSel)	
	    var idArray=tempSel.split(",");
	    for(i=0;i<idArray.length;i++)
	    {   
	        if(idArray[i]!="")
	        {  
	           url = url + "&id="+idArray[i]
	        } 
	    }
	    //alert(url);
	    sendRequest(url);
	    
	}
	else
	{
	    alert(msg);
	}
}

function deleteOne(url,id)
{
   sendRequest(url + "&id="+id);
}


function checkAll(e, itemName)
{
  var aa = document.getElementsByName(itemName);
  for (var i=0; i<aa.length; i++)
   aa[i].checked = e.checked;
}
function checkItem(e, allName)
{
  var all = document.getElementsByName(allName)[0];
  if(!e.checked) all.checked = false;
  else
  {
    var aa = document.getElementsByName(e.name);
    for (var i=0; i<aa.length; i++)
     if(!aa[i].checked) return;
    all.checked = true;
  }
}