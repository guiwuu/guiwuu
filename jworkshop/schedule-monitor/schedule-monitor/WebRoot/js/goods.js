// JavaScript Document

var paramGsArray = new Array();
var paramsArray = new Array();

//初始化高度
function initHeight()
{
	jQuery("#main_text").height(jQuery("#s_o").height() - 135);
	jQuery("#main_text").width(jQuery("#main_text").width()-3);
}
//tab切换样式
function tabSwitch(aim)
{
	jQuery(".mtb").hide();
	jQuery("#main_ctrl a").removeClass("on");
	jQuery("#main_ctrl a[aim='"+aim+"']").addClass("on");
	jQuery("#main_ctrl a[aim='"+aim+"']").blur();
	if(aim=="all")
	{
		jQuery(".mtb").fadeIn();
	}
	else
	{
		jQuery("#"+aim).fadeIn();
	}
}
//窗口缩放
window.onresize = function(){
	//初始化高度
	initHeight();
};
jQuery(document).ready(function(){
	//初始化高度
	initHeight();
	//上传图片
	jQuery("#img_upload").css("margin-right",(jQuery(".detail_block").width()-jQuery(".detail_block table").width()-395)+'px');
	//input获得焦点样式
//	jQuery("input[type='text'],textarea").focus(function(){
//		jQuery(this).addClass("input_hover");
//	});
//	jQuery("input[type='text'],textarea").blur(function(){
//		jQuery(this).removeClass("input_hover");
//	});
	//tab功能
	jQuery("#main_ctrl>a").each(function(){
		this.href = "javascript:tabSwitch('"+jQuery(this).attr('aim')+"')";
	});
	//商品分类select
	jQuery("#classify").change(function(){
		jQuery(this).next().remove();
		//var o = this.options[this.selectedIndex].value.split("~");
		var v = this.options[this.selectedIndex].value;
		jQuery.each(array,function(i,n){
			if(n.classId == v)
				jQuery("#typeid option[value='"+n.typeId+"']").attr("selected","selected");
		});
		jQuery("#typeid").change();
	});
	
	//通过商品类型查找到相应的品牌
	
	
	//商品类型select
	jQuery("#typeid").change(function(){
		jQuery(this).next().remove();
		var v = this.options[this.selectedIndex].value;
		var json = "";
		var u=this.url+this.options[this.selectedIndex].value;
		jQuery.ajax({
				type: "POST",
				 url: "type!findGoodsAttribute.action?typeid="+v,
			   async: true,
			dataType: "json",
			 success: function(attributejson)
			 	{
			 	    jQuery(".insert_tr").remove();
			 	    var inputStr='';
			 	    var props=attributejson.whuType.typeProps;//属性
			 	    if(props!=null)
			 	    {
			 	    props=eval("(" + props + ")");
//[{t:'i',n:'CPU',s:'y',os:[],f:'p0'},{t:'i',n:'显卡',s:'n',os:[],f:'p1'},{t:'i',n:'主板',s:'y',os:[],f:'p2'},{t:'s',n:'声卡',s:'n',os:['5200','5300','5400'],f:'p3'}]
//[{t:'i',n:'Test1',s:'y',os:[],f:'p0'},{t:'i',n:'Test2',s:'y',os:[],f:'p1'},{t:'i',n:'Test3',s:'y',os:[],f:'p2'},{t:'s',n:'Test4',s:'n',os:['1','2','3','4'],f:'p3'},{t:'i',n:'Test5',s:'y',os:[],f:'p4'},{t:'i',n:'Test6',s:'y',os:[],f:'p5'},{t:'i',n:'Test7',s:'y',os:[],f:'p6'}]
			 	     for(var i=0;i<props.length;i++)
			 	     {
			 	     	var p=props[i];
			 	     	if(p.t=='i')
			 	     	{
     		 	     		inputStr = '<input name="'+'goods.'+p.f+'" type="text" value="" />';
			 	     	}else if(p.t=='s')
			 	     	{
			 	     	   	inputStr= '<select name="'+'goods.'+p.f+'">';
			 	     	 for(var j=0;j<p.os.length;j++)
			 	     		 {
			 	     		     inputStr += '<option value="'+p.os[j]+'">'+p.os[j]+'</option>';
			 	     		 }
			 	     	    inputStr += '</select>';
			 	     	}
	
			 	     	jQuery("#ib").before('<tr class="insert_tr"><td class="right show width_left apAttr">'+p.n+':</td><td class="text_input width_long">'+inputStr+'<span class="hint">&lt;这里已经改变了</span></td></tr>');
			 	     	 inputStr='';
			 	     }
			 	     }

//参数---------------------------[{g:'性能',p:['前端频率','GPU']}]


			 	      var param=attributejson.whuType.typeParams;//属性

			 	      paramGsArray = new Array();
			 	      paramsArray = new Array();
			 	      
			 	      if(param!=null)
			 	      {
			 	          param=eval("(" + param + ")");
			 	          
			 	          for(var i=0;i<param.length;i++)
			 	          {
			 	          	    var strg = '参数组: <b>'+param[i].g+'</b>';
			 	          	    paramGsArray.push(param[i].g);
			 	          	    jQuery("#pro").before('<tr class="insert_tr"><td colspan="3" style="padding-left:20px;">'+strg+'</td></tr>');
			 	          	    var p=param[i].p;
			 	          	    var pArray = new Array();
			 	          	    for(var j=0;j<p.length;j++)
			 	          	    {
			 	          	    	pArray.push({name:p[j],id:p[j]+'_'+j});
			 	          	          inputStr=p[j]+ ':</td><td class="text_input width_long"><input type="text" value="" id="'+p[j]+'_'+j+'" />';
			 	          	          jQuery("#pro").before('<tr class="insert_tr"><td style="width:40px"></td><td class="right show width_left">'+inputStr+'</td></tr>');
			 	          	          inputStr='';
			 	          	    }
			 	          	    paramsArray.push(pArray);
			 	          }		 	          		
			 	      } 	      
			 	     	 	     
				 	findbrand();
				 }
			})
				 	
	 
	   
//		jQuery("select[name='brand']").empty();
//		jQuery("select[name='brand']").append('<option>请选择</option>');
//		for(var i=0;i<json.brand.length;i++)
//		{
//			jQuery("select[name='brand']").append('<option value="'+json.brand[i].name+'">'+json.brand[i].title+'</option>');
//		}
//		jQuery("select[name='brand']").after('<span class="hint">&lt;这里已经该变了</span>');
//		jQuery(".insert_tr").remove();
//		for(var i=0;i<json.attr.length;i++){
//			var inputStr = "";
//			if(json.attr[i].input=="")
//			{
//				inputStr = '<input name="'+json.attr[i].name+'" type="text" value="" />';
//			}
//			else
//			{
//				inputStr += '<select name="'+json.attr[i].name+'">';
//				for(var j=0;j<json.attr[i].input.length;j++)
//				{
//					inputStr += '<option value="'+json.attr[i].input[j].name+'">'+json.attr[i].input[j].title+'</option>';
//				}
//				inputStr += '</select>';
//			}
//			jQuery("#ib").before('<tr class="insert_tr"><td class="right show width_left apAttr">'+json.attr[i].title+':</td><td class="text_input width_long">'+inputStr+'<span class="hint">&lt;这里已经改变了</span></td></tr>');
//		}
//		jQuery(".hint").fadeIn("slow",function(){
//			jQuery(".hint").fadeOut(4000);
//		});
//				}
//		});
//		
//		json = {
//		
//			attr:[
//				{title:"产品类型",name:"leixing",input:[{title:"商务应用",name:"shangwu"},{title:"家庭影音",name:"jiating"},{title:"娱乐游戏",name:"yule"}]},
//				{title:"标称主频",name:"zhupin",input:""},
//				{title:"屏幕尺寸",name:"chicun",input:[{title:"17寸",name:"17"},{title:"19寸",name:"19"}]}
//			]
//		};
		
	});
});

function findbrand()
{
	var v = jQuery("#typeid").val();
	jQuery.ajax({
			type: "POST",
			 url: "type!findbrand.action?typeid="+v,
		   async: true,
		dataType: "json",
		 success: function(brandjson)
		{
			
			jQuery("#brandid").empty();
			jQuery("#brandid").append('<option value="'+1+'">请选择</option>')
			jQuery.each(brandjson.brandList,function(i,n)
			{
					jQuery("#brandid").append('<option value="'+n[0]+'" brandname="'+n[1]+'">'+n[1]+'</option>');
			});
			jQuery("#brandid").change(function(){
				jQuery("#brandname").val(this.options[this.selectedIndex].brandname);
			});
		}
	});
}



function openSpec()
{
	if(jQuery("#specMask").length==0)
		jQuery(document.body).append('<div id="specMask" style="position:absolute;width:100%;height:100%;background:#fff;opacity:0.3;filter:alpha(opacity:30);z-index:999">');
	else
		jQuery("#specMask").show();
	jQuery("#ospec").css("top",(jQuery(document.body).height()-jQuery("#ospec").height())/2+"px").css("left",(jQuery(document.body).width()-jQuery("#ospec").width())/2+"px").show();
	
}
var specn = new Array();
var specv = new Array();
var dkr = new Array();
function addSpec()
{
	var speci = jQuery("#ospec input");
	jQuery("#ospec").hide();
	jQuery("#specMask").hide();
	if(speci[0].value==''||speci[1].value=='')
		return;
	speci[0].value = speci[0].value.replace(/[\s\\\']/g,"");
	speci[1].value = speci[1].value.replace(/[\s\\\']/g,"");
	var vsn = speci[1].value.split(",");
	var vs = new Array();
	var isNull = true;
	jQuery.each(vsn,function(i,n){
		if(n==''||!n)
			return;
		var isaru = false;
		if(vs.length>0)
		{
			jQuery.each(vs,function(j,m){
				if(m==n)
					isaru = true;
			});
		}
		if(isaru)
			return;
		vs.push(n);
		isNull = false;
	});
	if(isNull)
		return;
	specn.push(speci[0].value);
	specv.push(vs);
	
	
	var tk = new Array();
	if(dkr.length==0)
	{
		jQuery.each(vs,function(i,n){
			tk[i] = new Array();
			tk[i].push(n);
		});
	}
	else
	{
		jQuery.each(vs,function(i,n){
			jQuery.each(dkr,function(j,m){
				if(m.length==0)
					return;
				var tttt = new Array();
				jQuery.each(m,function(k,o){
					tttt[k] = o;
				});
				tk.push(tttt);
				tk[tk.length-1].push(n);
			});
		});
	}
	dkr = tk;
	jQuery("#spec2").empty();
	if(jQuery("#addspec").length==0)
		jQuery("#spec1").after('<a id="addspec" href="javascript:openSpec()" class="add_ico silver_bt"><span></span>增加规格</a><a id="remspec" href="javascript:removeSpec3()" class="del_ico silver_bt"><span></span>取消规格</a>');
	var tempstr1 = '';
	jQuery.each(specn,function(i,n){
		if(!n)
			return;
		tempstr1+='<td abc="'+i+'">'+n+' <a href="javascript:removeSpec2('+i+')">[x]</a>'+'</td>';
	});
	jQuery("#spec2").append('<tr head="a"><td></td><td>货号</td>'+tempstr1+'<td>库存</td><td>销售价格</td></tr>');
	jQuery.each(dkr,function(i,n){
		var tempstr2 = '';
		
		jQuery.each(n,function(j,m){
			if(!m)
				return;
			tempstr2 += '<td abc="'+j+'"><input name="productsList['+i+'].pdtDesc" value="'+m+'" type="hidden"/>'+m+'</td>';
		});
		//alert(i);
		jQuery("#spec2").append('<tr asdf="'+i+'"><td><a href="javascript:removeSpec('+i+')">[x]</a></td><td class="text_input"><input name="productsList['+i+'].barcode" type="text" value="" /></td>'+tempstr2+'<td class="text_input"><input name="productsList['+i+'].store" type="text" value="" /></td><td class="text_input"><input name="productsList['+i+'].pprice"  type="text" value="" /></td></tr>');
	});
	
	speci[0].value='';
	speci[1].value='';
	jQuery("#spec1").hide();
	jQuery("#spec2").show();
	
	jQuery(":text").keyup(function(){
		this.value=this.value.replace(/\'/g,'');
	});
}
function removeSpec(index)
{
	dkr[index] = new Array();
	//jQuery("#spec2 tr:eq("+(index+1)+")").remove();
	jQuery("tr[asdf='"+index+"']").remove();
	if(jQuery("tr[asdf]").length==0)
	{
		removeSpec3();
	}
}
function removeSpec2(index)
{
	specn[index] = null;
	//specv[index] = new Array();
	var temp1 = new Array();
	var temp2 = new Array();
	for(var i in specn)
	{
		if(!specn[i])
			continue;
		temp1.push(specn[i]);
		temp2.push(specv[i]);
	}
	specn = temp1;
	specv = temp2;
	jQuery("#spec2 td[abc='"+index+"']").remove();
	if(jQuery("tr[head='a'] td").length < 5)
	{
		removeSpec3();
	}
	else
	{
		dkr = cartesian(specv);
		jQuery("#spec2").empty();
		if(jQuery("#addspec").length==0)
			jQuery("#spec1").after('<a id="addspec" href="javascript:openSpec()" class="add_ico silver_bt"><span></span>增加规格</a><a id="remspec" href="javascript:removeSpec3()" class="del_ico silver_bt"><span></span>取消规格</a>');
		var tempstr1 = '';
		jQuery.each(specn,function(i,n){
			if(!n)
				return;
			tempstr1+='<td abc="'+i+'">'+n+' <a href="javascript:removeSpec2('+i+')">[x]</a>'+'</td>';
		});
		jQuery("#spec2").append('<tr head="a"><td></td><td>货号</td>'+tempstr1+'<td>库存</td><td>价格</td></tr>');
		jQuery.each(dkr,function(i,n){
			var tempstr2 = '';
			jQuery.each(n,function(j,m){
				if(!m)
					return;
				tempstr2 += '<td abc="'+j+'"><input name="productsList['+i+'].pdtDesc" value="'+m+'" type="hidden"/>'+m+'</td>';
			});
			//alert(i);
			jQuery("#spec2").append('<tr asdf="'+i+'"><td><a href="javascript:removeSpec('+i+')">[x]</a></td><td class="text_input"><input name="productsList['+i+'].barcode" type="text" value="" /></td>'+tempstr2+'<td class="text_input"><input name="productsList['+i+'].store" type="text" value="" /></td><td class="text_input"><input name="productsList['+i+'].sprice"  type="text" value="" /></td></tr>');
		});
		
		//speci[0].value='';
		//speci[1].value='';
		jQuery("#spec1").hide();
		jQuery("#spec2").show();
		
		jQuery(":text").keyup(function(){
			this.value=this.value.replace(/\'/g,'');
		});
	}
}
function removeSpec3()
{
	jQuery("#spec2").empty();
	jQuery("#spec2").hide();
	jQuery("#spec1").show();
	jQuery("#addspec").remove();
	jQuery("#remspec").remove();
	specn = new Array();
	specv = new Array();
	dkr = new Array();
}
function specCancel()
{
	jQuery("#ospec").hide();
	jQuery("#specMask").hide();
}
var aI = 0;
function addAttachment()
{
	jQuery("#attachment").append('<div id="attachment_'+aI+'" class="detail_block theme_gray">'+
		'<table>'+
			'<tr>'+
				'<td class="right show width_left">配件组名称:</td>'+
				'<td class="width_middle text_input">'+
					'<input tpye="text" value="" />'+
				'</td>'+
				'<td class="width_long"><a href="javascript:removeAttachment('+aI+')" class="del_ico silver_bt"><span></span>删除此配件组</a></td>'+
			'</tr>'+
			'<tr>'+
				'<td class="right show width_left">最小购买量:</td>'+
				'<td class="text_input width_middle">'+
					'<input type="text" value="" />'+
				'</td>'+
				'<td class="width_long"></td>'+
			'</tr>'+
			'<tr>'+
				'<td class="right show width_left">最大购买量:</td>'+
				'<td class="text_input width_middle">'+
					'<input type="text" value="" />'+
				'</td>'+
				'<td class="width_long"></td>'+
			'</tr>'+
			'<tr>'+
				'<td class="right show width_left">配件优惠:</td>'+
				'<td colspan="2" class="input_radio width_middle">'+
					'<input type="radio" name="qqq"  value="1" id="jyi" checked="checked"/><label for="jyi">优惠谋个折扣</label>&nbsp;'+
					'<input type="radio" name="qqq"  value="0" id="jni"/><label for="jni">优惠一定金额</label>'+
				'</td>'+
			'</tr>'+
			'<tr>'+
				'<td class="right show width_left">优惠额度:</td>'+
				'<td class="text_input width_middle">'+
					'<input type="text" value="" />'+
				'</td>'+
				'<td class="width_long">(无优惠可不填；优惠9折就输入0.9，优惠100元就输入100)</td>'+
			'</tr>'+
			'<tr>'+
				'<td class="right show width_left">添加配件商品:</td>'+
				'<td colspan="2">'+
					'<a href="javascript:addAttaGoods('+aI+')" class="add_ico silver_bt"><span></span>添加</a>'+
				'</td>'+
			'</tr>'+
			'<tr>'+
				'<td></td>'+
				'<td colspan="2" class="add_atta">'+
					'<div class="bao">'+
						'<table>'+
							'<tr>'+
								'<th width="50"></th>'+
								'<th width="200">商品名称</th>'+
								'<th width="300">详细信息</th>'+
								'<th></th>'+
							'</tr>'+
						'</table>'+
					'</div>'+
				'</td>'+
			'</tr>'+
		'</table>'+
	'</div>');
	jQuery(":text").keyup(function(){
		this.value=this.value.replace(/\'/g,'');
	});
	aI++;
}
function removeAttachment(index)
{
	ccc[index] = null;
	jQuery("#attachment_"+index).remove();
}
function addAttaGoods(attaIndex)
{
	if(jQuery("#specMask").length<1)
		jQuery(document.body).append('<div id="specMask" style="position:absolute;width:100%;height:100%;background:#fff;opacity:0.3;filter:alpha(opacity:30);z-index:999">');
	else
		jQuery("#specMask").show();
	if(jQuery("#selectGoods_b").length<1)
	{
		jQuery(document.body).append('<div id="selectGoods_b" style="width:700px;height:400px;position:absolute;z-index:1000;border:3px double #d4dee7;left:'+(jQuery(document.body).width()-700)/2+'px;top:'+(jQuery(document.body).height()-400)/2+'px"><div id="selectGoods"></div><div id="selectGoods_button"><a href="javascript:pushAttaGoods('+attaIndex+')">确定</a> <a href="javascript:hideAttaGoods()">取消</a></div></div>');
		var table={title:'&nbsp;&nbsp;选择商品',height:'375px',width:'100%',pKey:'goodId',pageUrl:'../commodity/searchGoods!page.action?state=1',searchUrl:'../commodity/searchGoods!page.action?state=1'};
		var column=[
			{type:'checkbox'},
			{title:'标签',name:'tagName',width:'100px',sortable:false},
			{title:'货号',name:'barCode',width:'100px',sortable:true,searchable:true,field:'goods.barcode'},
			{title:'商品名',name:'goodsName',width:'100px',sortable:true,searchable:true,field:'goods.name'},
			{title:'分类',name:'className',width:'100px',sortable:true,field:'wclass.class_name'},
			{title:'类型',name:'typeName',width:'100px',sortable:true,field:'wtype.type_name'},
			{title:'市场价',name:'price',width:'100px',sortable:true,field:'goods.price'},
			{title:'成本价',name:'cost',width:'100px',sortable:true,field:'goods.cost'},
			{title:'品牌',name:'brandName',width:'100px',sortable:true,field:'goods.brand'},
			{title:'商品简介',name:'brief',width:'100px',sortable:true,field:'goods.brief'},
			//{type:'other',title:'上架',name:'infoUpdateState',width:'100px',sortable:true,field:'goods.info_update_state'},
			{title:'重量',name:'weight',width:'100px',sortable:true,field:'goods.weight'},
			{title:'商品单位',name:'unit',width:'100px',sortable:true,field:'goods.unit'},
			{title:'评分',name:'score',width:'100px',sortable:true,field:'goods.score'}
		];
		jQuery('#selectGoods').ajaxGrid({tab:table,col:column,exfn:{}});
	}
	else
	{
		jQuery("#selectGoods_button").empty();
		jQuery("#selectGoods_button").append('<a href="javascript:pushAttaGoods('+attaIndex+')">确定</a> <a href="javascript:hideAttaGoods()">取消</a>');
		jQuery("#selectGoods_b").show();
	}
}
function hideAttaGoods()
{
	jQuery("#specMask").hide();
	jQuery("#selectGoods_b").hide();
}
var attachments;
var ccc = new Array();
var ds = 'd';
var names = new Array();
var details = new Array();
function pushAttaGoods(attaIndex)
{
	//alert(attaIndex);
	if(typeof(ccc[attaIndex])=="object")
		attachments = ccc[attaIndex];
	else
	{
		attachments = new Array();
	}
	var ids = jQuery("#selectGoods").checkChange("selectGoods");
	if(!ids)
		return;
	names[attaIndex] = new Array();
	details[attaIndex] = new Array();
	var data = jQuery("#selectGoods").getJsonData().data;
	jQuery.each(data,function(i,n){
		jQuery.each(ids,function(j,m){
			if(n.goodId == m)
			{
				names[attaIndex].push(n.goodsName);
				if(n.brief==null)
					details[attaIndex].push(' —— ')
				else
					details[attaIndex].push(n.brief);
			}
		});
	});
	jQuery.each(ids,function(i,n){
		var ff = false;
		jQuery.each(attachments,function(j,m){
			if(n==m)
				ff = true;
		});
		if(ff)
			return;
		jQuery("#attachment_"+attaIndex+" .bao table").append('<tr id="'+n+'" class="'+ds+'">'+
			'<td class="x">'+
				'<a href="javascript:removeAttaGoods(\''+n+'\','+attaIndex+')">[x]</a>'+
			'</td>'+
			'<td>'+
				names[attaIndex][i]+
			'</td>'+
			'<td>'+
				details[attaIndex][i]+
			'</td>'+
			'<td></td>'+
		'</tr>');
		if(ds=='d')
			ds = 's';
		else
			ds = 'd';
		attachments.push(n);
	});
	ccc[attaIndex] = attachments;
	attachments = null;
	hideAttaGoods();
}
function removeAttaGoods(id,attaIndex)
{
	var nn = new Array();
	var newname = new Array();
	var newdetail = new Array();
	jQuery.each(ccc[attaIndex],function(i,n){
		if(n!=id)
		{
			nn.push(n);
			newname.push(names[attaIndex][i]);
			newdetail.push(details[attaIndex][i]);
		}
	});
	ccc[attaIndex] = nn;
	names[attaIndex] = newname;
	details[attaIndex] = newdetail;
	jQuery("#attachment_"+attaIndex+" .bao table tr[id]").remove();
	ds = 'd';
	
	
	jQuery.each(ccc[attaIndex],function(i,n){
		jQuery("#attachment_"+attaIndex+" .bao table").append('<tr id="'+n+'" class="'+ds+'">'+
			'<td class="x">'+
				'<a href="javascript:removeAttaGoods(\''+n+'\','+attaIndex+')">[x]</a>'+
			'</td>'+
			'<td>'+
				names[attaIndex][i]+
			'</td>'+
			'<td>'+
				details[attaIndex][i]+
			'</td>'+
			'<td></td>'+
		'</tr>');
		if(ds=='d')
			ds = 's';
		else
			ds = 'd';
	});
}







function getSpecNames()
{
	var str = '[';
	jQuery.each(specn,function(i,n){
		str += '\'' + n + '\',';
	});
	if(str.charAt(str.length-1)!='[')
		str = str.substring(0,str.length-1) + ']';
	else
		str += ']';
	//alert(obj2json(specn))
	return str;
	//demo ['颜色','尺寸','重量']
}
function getSpecs()
{
//	var arr = new Array();
//	for(i in specn)
//	{
//		var obj = new Object();
//		var temp = new Array();
//		for(j in specv[i])
//		{
//			temp.push(specv[i][j]);
//		}
//		obj[specn[i].toString()] = temp;
//		arr.push(obj);
//	}
	var str = '[';
	jQuery.each(specn,function(i,n){
		str += '{' + n + ':[';
		jQuery.each(specv[i],function(j,m){
			if(!m)
				return;
			str +='\'' + m + '\',';
		});
		if(str.charAt(str.length-1)!='[')
			str = str.substring(0,str.length-1) + ']';
		else
			str += ']';
		str += '},';
	});
	if(str.charAt(str.length-1)!='[')
		str = str.substring(0,str.length-1) + ']';
	else
		str += ']';
	//	alert(str);
	//alert(obj2json(arr));
	return str;

	//demo [{'颜色':['红','黄']},{'尺寸':['1','2']},{'重量':['12','3','232']}]
}
function getSpecsDetail()
{
	//alert(1);
	var str = '[';
	var f = 0;
	jQuery.each(dkr,function(j,m){
		if(m.length<1)
		{
			f++;
			return;
		}
		var tv = jQuery("tr[asdf]:eq("+(j-f)+") input");
		if(tv[0].value==''||tv[1].value==''||tv[2].value=='')
			return;
		str += '{ss:[';
		jQuery.each(specn,function(i,n){
			str += '\'' + n + '\',';
		});
		if(str.charAt(str.length-1)!='[')
			str = str.substring(0,str.length-1) + ']';
		else
			str += ']';
		str += ',sv:[';
		jQuery.each(m,function(i,n){
			str += '\'' + n + '\',';
		});
		if(str.charAt(str.length-1)!='[')
			str = str.substring(0,str.length-1) + ']';
		else
			str += ']';
		str += ',n:\''+tv[0].value+'\',r:\''+tv[1].value+'\',p:\''+tv[2].value+'\'},';
	});
	if(str.charAt(str.length-1)!='[')
		str = str.substring(0,str.length-1) + ']';
	else
		str += ']';
	return str;
	//demo [{ss:['a','b','c'],sv:['1','2','3'],n:'123213',r:'32',p:'322'},{}]
}
function getAttachments()
{
	var str = '[';
	for(var i=0;i<=aI;i++)
	{
		if(!ccc[i])
			continue;
		str += '{n:\'' + jQuery("#attachment_"+i+" input:eq(0)").val().replace(/\'/g,"") + '\',min:\'' + jQuery("#attachment_"+i+" input:eq(1)").val().replace(/\'/g,"") + '\',max:\'' + jQuery("#attachment_"+i+" input:eq(2)").val().replace(/\'/g,"") +'\',y:\'' + jQuery("#attachment_"+i+" :radio:checked").val() + '\',e:\''+jQuery("#attachment_"+i+" input:eq(5)").val().replace(/\'/g,"") + '\',gs:[';
		jQuery.each(ccc[i],function(j,m){
			if(!m)
				return;
			str += '\'' + m + '\',';
		});
		if(str.charAt(str.length-1)!='[')
			str = str.substring(0,str.length-1) + ']';
		else
			str += ']';
		str += '},';
	}
	if(str.charAt(str.length-1)!='[')
		str = str.substring(0,str.length-1) + ']';
	else
		str += ']';
	return str;
}

function getParamStr()
{
	var str = '[';
	for(i in paramGsArray)
	{
		str += '{g:\'' + paramGsArray[i] + '\',p:[';
		for(j in paramsArray[i])
		{
			str += '{n:\'' + paramsArray[i][j].name + '\',v:\'' + jQuery("#"+paramsArray[i][j].id).val() + '\'}';
			if(j<paramsArray[i].length-1)
				str += ',';
		}
		str += ']}'
		if(i<paramGsArray.length-1)
			str += ',';
	}
	str += ']';
	return str;
}