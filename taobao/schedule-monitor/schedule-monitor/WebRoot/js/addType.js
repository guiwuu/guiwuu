// JavaScript Document
var rl = 0
function initHeight()
{
	jQuery("#main_text").height(jQuery("#s_o").height() - 135);
	jQuery("#main_text").width(jQuery("#main_text").width()-3);
}
function addAttr(si)
{
	var index = parseInt(si);
	if(index - rl == jQuery("#attrTable tr").length - 2)
	{
		jQuery("#attrTable").append('<tr id="attrTr_'+(index+1)+'">'+
			'<td class="show"><a href="javascript:removeTr('+(index+1)+')">╳</a></td>'+
			'<td class="text_input">'+
				'<input type="text" abc="attrName" id="attrName_'+(index+1)+'" attrIndex="'+(index+1)+'" onfocus="addAttr(this.attrIndex)" onblur="removeAttr(this.attrIndex)" value=""/>'+
			'</td>'+
			'<td>'+
				'<select name="attr_type_'+(index+1)+'" id="attr_type_'+(index+1)+'">'+
					'<optgroup label="输入项">'+
						'<option value="0">输入项 - 可搜索</option>'+
						'<option value="1">输入项 - 不可搜索</option>'+
					'</optgroup>'+
					'<optgroup label="选择项">'+
						'<option value="2">选择项 - 可搜索</option>'+
						'<option value="3">选择项 - 不可搜索</option>'+
					'</optgroup>'+
				'</select>'+
			'</td>'+
			'<td class="text_input">'+
				'<input type="text"  id="selectValue_'+(index+1)+'" value=""/>'+
			'</td>'+
		'</tr>');
	}
}
function removeAttr(si)
{
	var index = parseInt(si);
	if(index == jQuery("input[abc='attrName']").length - 2 + rl&&jQuery("#attrName_"+index).val()==''&&jQuery("#selectValue_"+index).val()=='')
	{
		jQuery("#attrTr_"+(index+1)).remove();
	}
}
function removeTr(si)
{
	var index = parseInt(si);
	if(index - rl == jQuery("#attrTable tr").length - 2)
		return;
	jQuery("#attrTr_"+(index)).remove();
	rl++;
}
var pgrl = 0;
function addParamG(si)
{
	var index = parseInt(si);
	if(index - pgrl == jQuery("#paramGDiv > div.tab_left").length - 1)
	{
		jQuery("#paramGDiv").append('<div class="tab_left l_border" id="paramG_'+(index+1)+'">'+
			'<a href="javascript:removeParamGTr('+(index+1)+')">╳</a> '+
			'参数组名:'+
			'<span class="text_input w180">'+
				'<input type="text" abc="paramGName" paramGIndex="'+(index+1)+'" id="paramGName_'+(index+1)+'" onfocus="addParamG(this.paramGIndex)" onblur="removeParamG(this.paramGIndex)" value="" />'+
			'</span>'+
			'<div class="tab_left"> '+
				'<a>&nbsp;&nbsp;&nbsp;&nbsp;</a> '+
				'参数名: '+
				'<span class="text_input w180"> '+
					'<input type="text" abc="paramName" paramIndex="0" pId="paramG_'+(index+1)+'" id="paramName_paramG_'+(index+1)+'_0" onfocus="addParam(this.paramIndex,this.pId)" onblur="removeParam(this.paramIndex,this.pId)" value="" /> '+
				'</span> '+
			'</div> '+
		'</div> ');
	}
}
function removeParamG(si)
{
	var index = parseInt(si);
	if(index == jQuery("input[abc='paramGName']").length - 2 + pgrl&&jQuery("#paramGName_"+index).val()=='')
	{
		jQuery("#paramG_"+(index+1)).remove();
	}
}
function removeParamGTr(si)
{
	var index = parseInt(si);
	if(index - pgrl == jQuery("#paramGDiv > div.tab_left").length - 1)
		return;
	jQuery("#paramG_"+(index)).remove();
	pgrl++;
}
var prl = new Object;
function addParam(si,pId)
{
	var index = parseInt(si);
//	if(index!=0)
//		if(jQuery("#"+pId+" input[abc='paramName'][value='']").length!=0)
//		{
//			if(jQuery("#"+pId+" input[abc='paramName'][value='']")[0].paramGIndex<index){
//				jQuery("#"+pId+" input[abc='paramName'][value='']")[0].focus();
//				return;
//			}
//		}
	if(!prl[pId])
		prl[pId] = 0;
	if(index - prl[pId] == jQuery("#"+pId+" input[abc='paramName']").length - 1)
	{
		jQuery("#"+pId).append('<div class="tab_left" id="param_'+(index+1)+'"> '+
			'<a href="javascript:removeParamTr('+(index+1)+',\''+pId+'\')">╳</a> '+
			'参数名: '+
			'<span class="text_input w180"> '+
				'<input type="text" abc="paramName" paramIndex="'+(index+1)+'" pId="'+pId+'" id="paramName_'+pId+'_'+(index+1)+'" onfocus="addParam(this.paramIndex,this.pId)" onblur="removeParam(this.paramIndex,this.pId)" value="" /> '+
			'</span> '+
		'</div> ');
	}
}
function removeParam(si,pId)
{
	var index = parseInt(si);
	if(!prl[pId])
		prl[pId] = 0;
	if(index == jQuery("#"+pId+" input[abc='paramName']").length - 2 + prl[pId]&&jQuery("#"+pId+" #paramName_"+pId+"_"+index).val()=='')
	{
		jQuery("#"+pId+" #param_"+(index+1)).remove();
	}
}
function removeParamTr(si,pId)
{
	var index = parseInt(si);
	if(!prl[pId])
		prl[pId] = 0;
	if(index - prl[pId] == jQuery("#"+pId+" input[abc='paramName']").length - 1)
		return;
	jQuery("#"+pId+" #param_"+(index)).remove();
	prl[pId] += 1;
}
var brandSearchState = 1;
function brandSearch(s)
{
	var b = jQuery(".brandChooseSpan");
	brandSearchState = 0;
	jQuery("#searchF").hide();
	if(s!='')
	{
		if(s!='')
		{
			b.hide();
			jQuery.each(b,function(i,n){
				var ss = n.brandname.toLowerCase();
				if(ss.indexOf(s.toLowerCase())!=-1)
				{
					jQuery(n).show();
					brandSearchState = 1;
				}
			});
		}
		else
		{
			b.show();
			brandSearchState = 1;
		}
		if(brandSearchState == 0)
		{
			jQuery("#searchF").show();
		}
	}
	if(s=='')
	{
		b.show();
	}
}
var brands = new Array;
function removeBrand(id,bid)
{
	jQuery("#"+id).remove();
	jQuery.each(brands,function(i,n){
		if(n==bid)
		{
			brands[i] = -1;
		}
	});
}
jQuery(document).ready(function(){
	initHeight();
	jQuery(".brandChooseSpan").hover(
		function(){
			this.style.border = '1px solid #777';
		},
		function(){
			this.style.border = '1px solid #ccc';
		}
	);
	jQuery(".brandChooseSpan").click(function(){
		var f = 0;
		var id = this.id;
		jQuery.each(brands,function(i,n){
			if(n==id)
			{
				f = 1;
				brands[i] = -1;
			}
		});
		if(f==0)
		{
			brands.push(this.id);
			jQuery("#checkedBrand").append(" <a href=\"javascript:removeBrand('brand_"+this.id+"',"+this.id+")\" id=\"brand_"+this.id+"\" title=\"点击取消此品牌\">"+this.brandname+"</a> ");
		}
		else
		{
			jQuery("#brand_"+this.id).remove();
		}
	});
	
})