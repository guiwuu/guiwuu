var array=new Array();

//树形结构的递归调用方法
//jQuery(document).ready(function(){
	
//初始的加载	
//recursionJson();
//})
//AJAX调用数据库里的资源
function recursionJson(){

	jQuery.ajax({
          type: "POST",
          url: "class!findJsonClass.action",
          dataType: "json",
          success: function(json){
          recursionChange(json);
          getValue();
          
          },
          error: function(){}
      }); 
}
//最高级的父节点，递归调用
function recursionChange(json){
	var option="";
	jQuery.each(json.whuClassList,function(i,n){
		if(n.parentId==null){
		n.c=0;
		array.push(n);
		recursion(json,n.classId,n.c)
		}
	});

}
//递归调用数据库的类型，并遍历做一些比较排序放在array里面
function recursion(json,classId,c){
	jQuery.each(json.whuClassList,function(i,n){
		if(n.parentId==null){
			return;
		}
		if(n.parentId==classId){
			n.c=c+1;
			array.push(n);
			recursion(json,n.classId,n.c);
		}
	})
}
//返回后，遍历array，动态的加载在option里面
function showOption(classId,flag,parentId){
	
	var options="";
	jQuery.each(array,function(i,n){
		//如果是添加子类
		if(flag==1){
		if(n.c==0){
			if(classId==n.classId){
				options=options+"<option value="+n.classId+" selected>"+n.className+"</option>";
			}
			else{
				options=options+"<option value="+n.classId+">"+n.className+"</option>";
		}
		}
		else {
			var kg="&nbsp;&nbsp;&nbsp;";
			for(var j=1; j<n.c;j++){
				kg=kg+"&nbsp;&nbsp;";
			}
			kg=kg+"└";
			if(classId==n.classId){
				options=options+"<option value="+n.classId+" selected>"+kg+n.className+"</option>";
			}else{
				options=options+"<option value="+n.classId+">"+kg+n.className+"</option>";
			}
		}
	}
	   //如果是编辑
			if(flag==0){
		            if(n.c==0){
		            	
				if(parentId==n.classId){
					options=options+"<option value="+n.classId+" selected>"+n.className+"</option>";
				}else{
					options=options+"<option value="+n.classId+">"+n.className+"</option>";
				}
		}
		else {
			var kg="&nbsp;&nbsp;&nbsp;";
			for(var j=1; j<n.c;j++){
				kg=kg+"&nbsp;&nbsp;";
			}
			kg=kg+"└";
			if(parentId==n.classId){
				options=options+"<option value="+n.classId+" selected>"+kg+n.className+"</option>";
			}else{
				options=options+"<option value="+n.classId+">"+kg+n.className+"</option>";
			}
		}
	}

	});
		
	jQuery(options).appendTo("#classify");
	
}
     function getValue(){
     	var classId=''; 
     	var typeId=''; 
     	var flag="";
     	var parentId="";
         var url=window.location.search; 
       if(url!=null){
         if(url.indexOf("?")!=-1) 
        { 
        var str = url.substr(1) 
        strs = str.split("&"); 
        for(i=0;i<strs.length;i++) 
       { 
         if([strs[i].split("=")[0]]=='classId') 
         classId=unescape(strs[i].split("=")[1]); 
          if([strs[i].split("=")[0]]=='typeId') 
          typeId=unescape(strs[i].split("=")[1]);   
          if([strs[i].split("=")[0]]=='flag') 
          flag=unescape(strs[i].split("=")[1]);  
            if([strs[i].split("=")[0]]=='parentId') 
          parentId=unescape(strs[i].split("=")[1]);
        } 
         } 
      
   }
     showOption(classId,flag,parentId);
     selectedFenLei(typeId);
}
function selectedFenLei(typeId){
  jQuery("#"+typeId).attr({selected:"selected"});
}