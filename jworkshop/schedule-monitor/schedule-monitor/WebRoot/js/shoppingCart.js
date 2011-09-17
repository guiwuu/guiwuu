var cookie={ 
   //读取COOKIES,n为COOKIE名 
   Get:function(n){ 
   var re=new RegExp(n+'=([^;]*);?','gi'); 
   var r=re.exec(document.cookie)||[]; 
   return (r.length>1?r[1]:null) 
   }, 
   Get1:function(n){ 
   var re=new RegExp(n+'=([^;]*);?','gi'); 
   var r=re.exec(document.cookie)||[]; 
   return unescape(r.length>1?r[1]:null) 
   }, 
   //写入COOKIES,n为Cookie名，v为value 
   Set:function(n,v,e,p,d,s){ 
   var t=new Date; 
   if(e){ 
   // 8.64e7 一天 3.6e6 一小时 
   t.setTime(t.getTime() + (e*8.64e7)); 
   
   } 
   document.cookie=n+'='+v+'; '+(!e?'':'; expires='+t.toUTCString())+(!p?'':'; path='+p)+(!d?'':'; domain='+d)+(!s?'':'; secure') 

// Set cookie 
   }, 
   Set1:function(n,v,e,p,d,s){ 
   var t=new Date; 
   if(e){ 
   // 8.64e7 一天 3.6e6 一小时 
   t.setTime(t.getTime() + (e*3.6e6)); 
   
   } 
   document.cookie=n+'='+escape(v)+'; '+(!e?'':'; expires='+t.toUTCString())+(!p?'':'; path='+p)+(!d?'':'; domain='+d)+(!s?'':'; secure') 
   // Set cookie 
   }, 
   Del:function(n,p,d){ 
   var t=cookie.Get(n); 
   document.cookie=n+'='+(!p?'':'; path='+p)+(!d?'':'; domain='+d)+'; expires=Thu, 01-Jan-70 00:00:01 GMT'; 
   return t 
   } 
   }; 

  var TotalPro = cookie.Get("TotalPro"); //当前车内含有商品的总数 
var Common = { 
   
   //移除数组中指定项 
   
   delArr:function(ar,n) { //n表示第几项，从0开始算起。 
   if(n<0) //如果n<0，则不进行任何操作。 
   return ar; 
   else 
   return ar.slice(0,n).concat(ar.slice(n+1,ar.length)); 
   }, 
   
   //添加至购物车 
   intoCar:function(proid,quantity,proname,imgurl,_price) { 
   if(proid != "" && proname != "") { 
   var ProIDList = cookie.Get("carList"); //车内商品ID列表 
   if(ProIDList!=null && ProIDList!="" && ProIDList!="null") 
   { 
   if(Common.hasOne(proid)) 
   { 
   ProIDList += "&"+proid+"="+proid+"|"+quantity+"|"+escape(proname)+"|"+escape(imgurl)+"|"+_price; 
   cookie.Set("carList",ProIDList,2,"/");//更新购物车清单 
   TotalPro = cookie.Get("TotalPro"); //当前车内含有商品的总数 
   TotalPro++; //总数+1 
   cookie.Set("TotalPro",TotalPro,2,"/"); 
   } 
   else 
   { 
   alert("购物车中已含有此商品"); 
   } 
   } 
   else { 
   
   ProIDList=proid+"="+proid+"|"+quantity+"|"+escape(proname)+"|"+escape(imgurl)+"|"+_price; 
   cookie.Set("carList",ProIDList,2,"/");//更新购物车清单 
   
   cookie.Set("TotalPro",1,2,"/"); 
   } 
   Common.reloadcar();//更新顶部个数显示 
   //alert(ProIDList); 
   } 
   
   }, //添加物品结束 
   
   //重置购物车内个数 
   reloadcar:function() 
   { 
   var t=cookie.Get("TotalPro"); 
   if(t!=""&&t!="null") 
   document.getElementById("cart_num").innerText="(" + cookie.Get("TotalPro") + ")"; 
   else 
   document.getElementById("cart_num").innerText="(0)"; 
   }, //重置结束 
   
   //检验购物车内是否已经含有该商品 
   hasOne:function(pid){ 
   ProIDList = cookie.Get("carList"); //车内商品ID列表 
   if(ProIDList.lastIndexOf("&") != -1){ 
   var arr=ProIDList.split("&"); 
   for(i=0;i<arr.length;i++) 
   { 
   //alert(arr.indexOf("=")); 
   if(arr.substr(0,arr.indexOf("="))==pid) 
   { 
   
   return false; 
   } 
   } 
   } 
   else if(ProIDList!="null"&&ProIDList!="") 
   { 
   if(ProIDList.substr(0,ProIDList.indexOf("="))==pid) 
   return false; 
   } 
   return true; 
   }, //检测结束 
   
   //移除某商品 
   reMoveOne:function(proid){ 
   
   if(!Common.hasOne(proid)){ 
   if(ProIDList.lastIndexOf("&") != -1){ 
   var arr=ProIDList.split("&"); 
   for(i=0;i<arr.length;i++){ 
   if(arr.substr(0,arr.indexOf("="))==proid) { 
   
   var arr2=Common.delArr(arr,i); 
   var tempStr=arr2.join("&"); //由数组重组字符串 
   
   cookie.Set("carList",tempStr,2,"/");//更新购物车清单 
   var t=cookie.Get("TotalPro"); 
   cookie.Set("TotalPro",t-1,2,"/");//更新Cookies中的个数 
   // Common.reloadcar();//更新顶部个数显示 
   return; 
   } 
   } 
   } 
   else{ 
   
   cookie.Set("carList","null");//更新购物车清单 
   var t=cookie.Get("TotalPro"); 
   cookie.Set("TotalPro",0,2,"/");//更新购物车清单 
   // Common.reloadcar();//更新顶部个数显示 
   } 
   } 
   
   }, //移除物品结束 
   
   //修改某物品数量 
   updateQuantity:function(proid,quantity){ 
   ProIDList = cookie.Get("carList"); //车内商品ID列表 
   if(ProIDList.lastIndexOf("&") != -1) { 
   var arr=ProIDList.split("&"); 
   var sub=Common.getSubPlace(ProIDList,proid);//获取该物品在COOKIE数组中的下标位置 
   var arr2=arr[sub].split("|"); 
   arr2[1]=quantity; 
   var tempStr=arr2.join("|");//由数组重组字符串 
   arr[sub] = tempStr; 
   var newProList = arr.join("&");//由数组重组字符串 
   cookie.Set("carList",newProList,2,"/");//更新购物车清单 
   //alert(newProList); 
   } 
   else { 
   
   var arr=ProIDList.split("|"); 
   arr[1]=quantity; 
   var newProList=arr.join("|"); 
   cookie.Set("carList",newProList,2,"/");//更新购物车清单 
   //alert(newProList); 
   } 
   
   
   }, //修改物品结束 
   
   //返回指定物品所在数组的下标位置 
   getSubPlace:function(list,proid){ 
   var arr=list.split("&"); 
   for(i=0;i<arr.length;i++){ 
   if(arr.substr(0,arr.indexOf("="))==proid) { 
   return i; 
   } 
   } 
   }, //返回下标结束 
   
   //返回购物车中商品总数（大类别）
   getCartNum:function(){
   var TotalPro = cookie.Get("TotalPro");
	return TotalPro;
   },
   
   //返回商品对象序列化形成的字符串
   getProductsString:function(){
   ProIDList = cookie.Get("carList"); //车内商品ID列表 
   var productsArr=new Array();//商品数组
   var arr=ProIDList.split("&");
   for(i=0;i<arr.length;i++)
   {
   	productsArr[i]=new Array();
   	var arrTemp=arr[i].split("|");
   	for(j=0;j<arrTemp.length;j++)
   	{
   	if(j==0)
   		productsArr[i][j]=arr[i].substr(0,arr[i].indexOf("="));
   	else
   		productsArr[i][j]=arrTemp[j];
   	}
   }
   return productsArr;
   }
 }; 

