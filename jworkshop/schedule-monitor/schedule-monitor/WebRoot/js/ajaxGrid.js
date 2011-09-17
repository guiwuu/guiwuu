/*
 *  ajax分页表格
 *  v1.5.1 Beta
 *  2009-4-6 dali
 *
*/
//页面文档类型:
//		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


/****************************************************************************************************************************************/
//                                                           grid的属性 tab的配置
/****************************************************************************************************************************************/
//	tab :
//	{
//			   title: '',             //表名 string
//			   width: '700%',         //表宽 string
//			  height: '500%',         //表高 string
//				rows: 15,             //每页数据行 int
//		   pageStyle: 1,              //分页按钮 int [1/2] [上下页/数字]
//			 pageNum: 5,              //pageStyle 为2时 此属性表示显示在下面的页码总数
//		      cookie: false,          //是否保存cookie bool                                                                   表格外观
//		----------------------------------------------------------------------------------------------------------------------------------
//			colEdite: false,          //可否进行列调整 bool
//		previewStyle: 'none',         //预览样式 string [none/default/advance] [不可预览/默认预览/高级预览]                        表格操作
//		----------------------------------------------------------------------------------------------------------------------------------
//			  imgKey: '',             //预览图片键值
//			 linkKey: '',             //预览连接键值
//		   detailKey: '',             //预览详细信息键值                                                                                  默认预览键
//		----------------------------------------------------------------------------------------------------------------------------------
//				pKey: '',             //主键 string  (必填)                                                                   数据主键
//		----------------------------------------------------------------------------------------------------------------------------------
//			 pageUrl: '',             //分页请求地址 string  (必填)
//			 editUrl: '',             //编辑跳转地址 string
//			  addUrl: '',             //添加跳转地址 string
//		   deleteUrl: '',             //删除请求地址 string
//		  previewUrl: '',             //预览请求地址 string
//		   searchUrl: '',             //排序请求地址 string                                                                    配置action
//		 adSearchUrl: '',             //高级搜索请求地址 string                                             (配置了相应的action,才会显示相应的按钮)
//		----------------------------------------------------------------------------------------------------------------------------------
//	}
//


/****************************************************************************************************************************************/
//                                                           列属性 col的配置
/****************************************************************************************************************************************/
//	col:
//	{
//			 type: 'text',   // 列类型string [text/checkbox/edit/other] [文本列/多选框列/编辑操作列/自定义列] (若列类型非text文本列则title,sortable,name属性无效)
//			title: '',       // 列标题string  (必填)
//			width: 100,      // 列宽int
//			align: 'center', // 列对齐方式string [left/center/right] [左/中/右对齐]
//		 sortable: false,    // 可否排序bool
//	   searchable: false,    // 可否搜索
//			 name: ''        // 数据字段string  (必填)
//	}


/****************************************************************************************************************************************/
//                                                        grid的扩展函数 exfn的配置
/****************************************************************************************************************************************/
//	//grid数据更新成功(分页,搜索,排序)
//	// 参数
//	//	 aim  父div的id
//	//	json  更新的json
//	tableUpdateSuccess = function(aim,json){}
//	
//	//grid数据更新失败(分页,搜索,排序)
//	// 参数
//	//	  aim  父div的id
//	//	state  状态
//	tableUpdateError = function(aim,state){}
//	
//	//预览成功
//	//	 aim  父div的id
//	//	 cId  预览div的id
//	previewSuccess = function(pId,cId){}
//	
//	//预览失败
//	//	 aim  父div的id
//	previewError = function(pId){}
//	
//	//删除之前
//	// 参数
//	//	pks  删除目标的id array
//	beforeDelete = function(pks){}
//	
//	//删除成功
//	deleteSuccess = function(){}
//	
//	//删除失败
//	deleteError = function(){}
//
//	//高级预览
//	// 参数
//	//	  pKey 该行主键
//	//	   pId 父div的id
//	//	   cId 添加层的id
//	// 返回
//	//	string 预览的HTML内容
//	adPreview = function(pKey,pId,cId){}
//	
//	//高级搜索
//	// 参数
//	//	pId 父div的id
//	//	cId 添加层的id
//	// 返回 string 高级搜索HTML内容
//	adSearch = function(pId,cId){}
//	
//	/*
//	* 高级搜索ajax
//	*/
//	adSearchAjax = function()
//	{
//		重写此函数必须在成功返回之后,更新当前表格状态并刷新显示内容,如下
//		jQuery.ajax({
//			 success: function(json)
//				{
//					jQuery().changeState(data);
//					jQuery().updateView(json.page);
//				},
//		});
//	}
//	
//	/*
//	* 自定义单元格内容
//	* 参数 
//	*	info: = {
//			cellText: 此列name所对应的json中的值
//			    pKey: 该行主键对应的值
//			   rowId: 该行的html标签id
//			rowIndex: 该行的行索引
//			colIndex: 该列的列索引
//		}
//	*/
//	newCell = function(info)
//	{
//		重写此函数必须return一个html
//		var html = "";
//		return html;
//	}
/*****************************************************************************************************************************************/

window.onresize = function(){
	var gt = jQuery(".grid_table");
	jQuery(".grid_table").css({position:"relative"});
	for(var i=0;i<gt.length;i++)
	{
		gt[i].style.height = (gt[i].parentNode.offsetHeight - jQuery(".grid_page")[i].offsetHeight - jQuery(".grid_title")[i].offsetHeight - jQuery(".grid_edit")[i].offsetHeight)+'px';
	}
}

jQuery(document).ready(function(){
	jQuery(document.body).click(function(){
		jQuery(".rc").empty(); 
		jQuery(".rc").hide();
	});
	var gt = jQuery(".grid_table");
	var th = jQuery(".table_head");
	jQuery(".grid_table").css({position:"relative"});
	for(var i=0;i<gt.length;i++)
	{
		gt[i].style.height = (gt[i].parentNode.offsetHeight - jQuery(".grid_page")[i].offsetHeight - jQuery(".grid_title")[i].offsetHeight - jQuery(".grid_edit")[i].offsetHeight - 18)+'px';
		gt[i].style.width = gt[i].parentNode.offsetWidth + 'px';
	}
});


(function(jQuery){
	
	//ajaxGrid对象
	var ag;
	
	/**************** 定义单元格类 *****************
	* 参数:                                       *
	* 	value  单元格内容  string                  *
	*	pk     该行的主键对应值 string              *
	*	col    与该单元格对应的表头对象  object      *
	*	tab    表格对象   object                  *
	*	aim    单元格所在行的id  string            *
	*********************************************/
	var Cell = function(value,pk,col,tab,aim,index,cIndex,aim2)
	{
		this.data = 
		{
			 type: 'text',  // 单元格类型
			value: '',      // 单元格内容
			width: 100,     // 单元格宽度
			align: 'center'// 单元格对齐方式
		};
		
		//更新数据
		jQuery.extend(this.data,{
			    type: col[cIndex].data.type,
			   value: value,
			   width: col[cIndex].data.width,
			   align: col[cIndex].data.align
		});
		if(this.data.value==null)
			this.data.value = '<span class="cgray">(暂无数据)</span>';
		//绘制单元格
		var style = 'width:'+col[cIndex].data.width+';text-align:'+col[cIndex].data.align+';';
		var pointer = '';
		var eo = 'odd';
		if(tab.data.previewStyle!='none'&&tab.data.previewStyle!='')
			pointer += "cursor:pointer;";
		if(col[cIndex].data.hide&&this.data.type!='checkbox')
			style += 'display:none;';
		if(index%2==0)
			eo = 'even';
		if(this.data.type == 'text')
			jQuery("#"+aim).append('<span class="table_cell '+eo+'" col="'+cIndex+'" style="'+style+pointer+'" onclick="jQuery(\'#'+aim+'\').preView(\''+pk+'\');">'+this.data.value+'</span>');
		else if(this.data.type == 'checkbox')
			jQuery("#"+aim).append('<span class="table_cell '+eo+'" style="'+style+'"><input type="checkbox" style="cursor:pointer" id="'+pk+'" name="check_all'+aim.substring(0,aim.length-(index+'').length)+'" onclick="jQuery().checkChange(\''+aim2+'\');"/>&nbsp;</span>');
		else if(this.data.type == 'edit'&&tab.data.editUrl!=''){
			var intext = '';
			intext += '<a class="edit" href="javascript:jQuery().rowEdit(\''+pk+'\');">编辑</a>';
			jQuery("#"+aim).append('<span class="table_cell '+eo+'" col="'+cIndex+'" style="'+style+'">'+intext+'</span>');
		}
		else if(this.data.type == 'other')
		{
			var info = {
				cellText: value,
				    pKey: pk,
				   rowId: aim,
				rowIndex: index,
				colIndex: cIndex
			};
			var intext = jQuery().newCell(info);
			jQuery("#"+aim).append('<span class="table_cell '+eo+'" col="'+cIndex+'" style="'+style+'">'+intext+'</span>');
		}
}
	
	
	/****************** 定义行类 *********************
	* 参数:                                         *
	*	row    行的数据  json                        *
	*	col    grid的表头数据集合 Array<json>         *
	*	tab    grid的表格对象 object                 *
	*	index  行号  int                            *
	*	aim    grid数据块(类似于tbody)的id  string    *
	************************************************/
	var Row = function(row,col,tab,index,aim)
	{
		//存储一行单元格
		this.data = new Array;
		
		//绘制表格行
		var style = "white-space:nowrap;"
		
		var rst = jQuery('<div class="table_row" style="'+style+'" id="tr'+aim+index+'"></div>');
		rst.hover(
			function()
			{
				jQuery(this).addClass("cell_hover");
				jQuery(this.childNodes).addClass("cell_hover");
			},
			function()
			{
				jQuery(this).removeClass("cell_hover");
				jQuery(this.childNodes).removeClass("cell_hover");
			}
		)
		
		jQuery("#"+aim).append(rst);
		
		//创建单元格
		for(var i=0;i<Column.nameArray.length;i++)
		{
			var name = Column.nameArray[i];
			this.data[i] = new Cell(row[name],row[tab.data.pKey],col,tab,"tr"+aim+index,index,i,aim.substring(2,aim.length));
		}
	}
	
	//静态变量显示正在处于预览状态的行
	Row.preViewIndex = '';
	
	
	
	/***************** 定义表头类 ********************
	* 参数:                                         *
	*	col   单个表头数据  json                      *
	*	index 当前表头所在索引 int                     *
	*	aim   表头行的id  string                     *
	************************************************/
	var Column = function(col,index,aim)
	{
		//初始化数据
		this.data = 
		{
			     type: 'text',   // 列类型string [text/checkbox/edit/other] [文本列/多选框列/编辑操作列/自定义列] (若列类型非text文本列则title,sortable,name属性无效)
			    title: '',       // 列标题string
			    width: '100px',  // 列宽int
			    align: 'center', // 列对齐方式string [left/center/right] [左/中/右对齐]]
		   searchable: false,    // 可否搜索
			 sortable: false,    // 可否排序bool
			     hide: false,    // 是否隐藏bool
			     name: '',       // 数据字段string
			    field: ''
		};
		
		//更新数据
		jQuery.extend(this.data,col);
		
		//向静态字段数组中添加字段
		Column.nameArray.push(this.data.name);
		
		//如果是checkbox类型,更改样式
		if(this.data.type == 'checkbox')
		{
			this.data.align = 'center';
			this.data.width = '30px';
		}
		
		//判断必要数据完整
		if(this.data.name!=''||this.type!='text')
		{
			//绘制表头
			var style = 'width:'+this.data.width+';text-align:'+this.data.align+';';
			if(this.data.sortable&&this.data.type=='text')
				style += 'cursor:pointer;';
			if(this.data.hide&&this.data.type!='checkbox')
				style += 'display:none;';
			if(this.data.type == 'text')
			{
				jQuery("#"+aim).append('<span class="head_cell" col="'+index+'" style="'+style+'" id="'+aim+'~c'+index+'" onclick="jQuery(\'#'+aim+'_'+this.data.name+'\').colSort(\''+this.data.name+'\',\''+this.data.field+'\','+index+');">'+this.data.title+'</span>');
			}
			else if(this.data.type == 'checkbox')
				jQuery("#"+aim).append('<span class="head_cell_check" style="'+style+'">'+
				'<input type="checkbox" id="all'+aim+'" style="cursor:pointer" name="" onclick="jQuery(\'#all'+aim+'\').checkAll();jQuery().checkChange(\''+aim.substring(2,aim.length)+'\');" />'+
				'</span>');
			else if(this.data.type == 'edit')
				jQuery("#"+aim).append('<span class="head_cell" col="'+index+'" style="'+style+'">编辑</span>');
			else if(this.data.type == 'other')
			{
				jQuery("#"+aim).append('<span class="head_cell" col="'+index+'" style="'+style+'">'+this.data.title+'</span>');
			}
		}
		else
		{
			alert("Debug:表头数据不完整,name不能为空");
		}
	}
	
	/*
	* 表头静态变量,字段数组
	* 在此数组之内的字段才会被显示出来
	*/
	Column.nameArray = new Array;
	
	
	
	/******************* 定义表格类 ********************
	* 参数:                                           *
	*	tab  表格属性  json                            *
	*	col  表头属性  json                            *
	*	aim  页面调用者的id  string                    *
	**************************************************/
	var Table = function(tab,col,aim)
	{
		//初始化数据
		this.data = 
		{
			       title: '',             //表名 string
			       width: '100%',         //表宽 int
			      height: '100%',         //表高 int
			        rows: 15,             //每页数据行 int
			      cookie: false,           //是否保存cookie bool 
			    colEdite: false,          //可否进行列 bool
			      amount: 0,              //数据量 int
			       state:
					   {
				         state: 'page',
				  searchString: '',
				       sortKey: '',
				     sortValue: '',
				     sortField: '',
				          rows: 15,
				          page: 1
					   },
			previewStyle: 'none',      //预览样式 string [none/default/advance] [不可预览/默认预览/advance)]
		       pageStyle: 1,              //分页按钮 int [1/2] [上下页/数字]
			     pageNum: 5,              //pageStyle 为2时 此属性表示显示在下面的页码总数
			      imgKey: '',             //预览图片键值
		 	     linkKey: '',             //预览连接键值
			   detailKey: '',             //预览详细信息键值
			        pKey: '',             //主键 string
			     pageUrl: '',             //分页请求地址 string
			     editUrl: '',             //编辑跳转地址 string
			      addUrl: '',             //添加跳转地址 string
			   deleteUrl: '',             //删除请求地址 string
			   searchUrl: '',             //搜索请求地址 string
			 adSearchUrl: '',             //高级搜索请求地址 string
			  previewUrl: '',             //预览请求地址 string
			  recycleUrl: ''              //回收站url
		};
		
		//更新数据
		jQuery.extend(this.data,tab);
		
		if(this.data.rows != 15)
			this.data.state.rows = this.data.rows;
		
		//判断必要数据完整
		if(this.data.pKey!=''&&this.data.pageUrl!='')
		{
			//绘制表格容器
			var editStr = '';
			if(this.data.addUrl!=''||this.data.deleteUrl!=''){
				editStr += '<span class="edit_b">';
				if(this.data.addUrl!='')
					editStr += '<a class="edit_add" href="#" onclick="jQuery().rowAdd();"><span></span>添加</a>';
				if(this.data.deleteUrl!='')
					editStr += '<a class="edit_del" href="#" id="del'+aim+'" needChecked="true" onclick="jQuery().rowDelete(\''+aim+'\');"><span></span>删除</a>';
				if(this.data.recycleUrl!='')
					editStr += '<a class="edit_rec" href="#" id="rec'+aim+'" onclick="location=\''+this.data.recycleUrl+'\'"><span></span>回收站</a>';
				editStr += '</span>';
			}
			if((this.data.addUrl!=''||this.data.deleteUrl!='')&&(this.data.searchUrl!=''||this.data.adSearchUrl!='')){
				editStr += '<span class="edit_split"></span>';
			}
			if(this.data.searchUrl!=''||this.data.adSearchUrl!=''){
				editStr += '<span class="edit_b">';
				if(this.data.searchUrl!='')
					editStr += '<a class="edit_sear" href="#" onclick="jQuery(\'#adser'+aim+'\').slideUp();jQuery(\'#ser'+aim+'\').slideToggle()"><span></span>搜索</a>';
				if(this.data.adSearchUrl!='')
					editStr += '<a class="edit_adsear" href="#" onclick="jQuery(\'#ser'+aim+'\').slideUp();jQuery(\'#adser'+aim+'\').slideToggle();"><span></span>高级搜索</a>';
				editStr += '<a class="edit_reset" href="#" onclick="jQuery().gridReset();"><span></span>重置</a></span>';
			}

			jQuery("#"+aim).addClass("ajax_grid");
			jQuery("#"+aim).css({width:this.data.width,height:this.data.height,position:'relative'});
			jQuery("#"+aim).append(
				'<div class="grid_title"><span class="ghint" id="hint'+aim+'"></span><span class="title_name">'+this.data.title+'</span></div>'+
				'<div class="grid_edit">'+editStr+'</div>'
			);
			
			//搜索条
			//搜索的页面字符
			var se = '';
			var adse = '';
			if(this.data.searchUrl!='')
			{
				//select中option的页面字符
				var so = '';
				//遍历表头设置,将有字段的列加入到搜索条件select中
				for(var i=0;i<col.length;i++)
				{
					if((col[i].type!='edit'&&col[i].type!='checkbox')&&col[i].name!=undefined&&col[i].searchable)
						so += '<option value="'+col[i].field+'">'+col[i].title+'</option>';
				}
				se = '<a class="search_close" onclick="jQuery(\'#ser'+aim+'\').slideUp()">&nbsp;</a>搜索:<select id="sersel'+aim+'">'+so+'</select><input type="text" id="stext'+aim+'" /><input type="button" value="" onclick="jQuery().rowSearch(jQuery(\'#sersel'+aim+'\').val(), jQuery(\'#stext'+aim+'\').val())" />';
			}
			if(this.data.adsearchUrl != '')
				adse = jQuery().adSearch('gt'+aim,'adser'+aim);
			//绘制表格主体和分页按钮
			jQuery("#"+aim).append(
				'<div class="grid_table" id="gt'+aim+'">'+
					'<div id="ser'+aim+'" class="grid_search" style="display:none;">'+se+'</div>'+
					'<div id="adser'+aim+'" class="grid_adSearch" style="display:none;">'+adse+'</div>'+
					'<div class="table_head" style="white-space:nowrap;" id="th'+aim+'"></div>'+
					'<div id="tb'+aim+'" class="table_body"></div>'+
				'</div>'+
				'<div class="grid_page" id="gp'+aim+'" style="position:absolute"></div>'
			);
		}
		else
		{
			alert("Debug:表格数据不完整,pKey和pageUrl不能为空");
		}
	}
	
	/******************* 定义grid类 *********************
	* 参数:                                             *
	*	initData 页面传来的初始化参数集合 json             *
	*   aim      页面调用者的id  string                  *
	****************************************************/
	var Grid = function(initData,aim)
	{
		this.aim = aim;
		this.pages;
		this.tab;//表格对象
		this.cols;//表头对象
		this.rows;//行对象
		this.jsonData;//当前grid数据
		this.jsonPreview;//预览数据
		this.initExtendFunction//扩展功能
		
		
		//function显示数据
		this.display = function()
		{
			if(this.rows!=null){//如果已经有数据
				jQuery("#allth"+aim).attr("checked",false);
				jQuery("#tb"+aim).empty();//删除原先的页面元素
				jQuery("#gp"+aim).empty();
				this.rows.length = 0;//删除原数组元素
			}else
				this.rows = new Array;//创建行数组
			for(var i=0;i<this.jsonData.data.length;i++)//向行中添加数据
				this.rows[i] = new Row(this.jsonData.data[i],this.cols,this.tab,i,'tb'+aim);
			jQuery().checkChange(aim);
			
			//分页栏按钮
			this.tab.data.amount = parseInt(this.jsonData.totalCount);
			this.pages = parseInt(this.tab.data.amount/this.tab.data.state.rows);//总共页数
			if(this.tab.data.amount%this.tab.data.state.rows != 0)
				this.pages++;
			var str = '';//分页按钮html字符串
			var cp = parseInt(this.jsonData.currentPageNo);//当前页
			var temp = parseInt((cp-1)/this.tab.data.pageNum);//当前页块
			var ps = '';//数字分页连接
			if(this.jsonData.currentPageNo == "0")
				return;
			if(this.tab.data.pageStyle == 1||this.tab.data.pageStyle == '1')
			{
				if(cp==1)
				{
					str += '<a>首页</a>';
					str += '<a>上一页</a>';
				}
				else
				{
					str += '<a href="#" onclick="jQuery(\'#'+aim+'\').pageChange(1)" >首页</a>';
					str += '<a href="#" onclick="jQuery(\'#'+aim+'\').pageChange('+(parseInt(this.tab.data.state.page)-1)+')" >上一页</a>';
				}
				if(cp==this.pages)
				{
					str += '<a>下一页</a>';
					str += '<a>末页</a>';
				}
				else
				{
					str += '<a href="#" onclick="jQuery(\'#'+aim+'\').pageChange('+(parseInt(this.tab.data.state.page)+1)+')" >下一页</a>';
					str += '<a href="#" onclick="jQuery(\'#'+aim+'\').pageChange('+this.pages+')" >末页</a>';
				}
			}
			else if(this.tab.data.pageStyle == 2||this.tab.data.pageStyle == '2')
			{
				for(var i=1;i<this.tab.data.pageNum+1;i++)
				{
					if((temp*this.tab.data.pageNum+i)<1||(temp*this.tab.data.pageNum+i)>this.pages)
						continue;
					ps += '<a class="page123a" title="第'+(temp*this.tab.data.pageNum+i)+'页" href="javascript:jQuery(\'#'+aim+'\').pageChange('+(temp*this.tab.data.pageNum+i)+')">'+(temp*this.tab.data.pageNum+i)+'</a>';
				}
				if(temp==0)
				{
					if(cp==1)
						str += '<a class="page123fd" title="首页">&nbsp;</a>';
					else
						str += '<a class="page123f" title="首页" href="javascript:jQuery(\'#'+aim+'\').pageChange(1)">&nbsp;</a>';
					str += '<a class="page123pd" title="前'+this.tab.data.pageNum+'页">&nbsp;</a>';
				}
				else
				{
					str += '<a class="page123f" title="首页" href="javascript:jQuery(\'#'+aim+'\').pageChange(1)">&nbsp;</a>';
					str += '<a class="page123p" title="前'+this.tab.data.pageNum+'页" href="javascript:jQuery(\'#'+aim+'\').pageChange('+(temp*this.tab.data.pageNum-this.tab.data.pageNum+1)+')">&nbsp;</a>';
				}
				str += ps;
				if(temp==parseInt((this.pages-1)/this.tab.data.pageNum))
				{
					str += '<a class="page123nd" title="后'+this.tab.data.pageNum+'页">&nbsp;</a>';
					if(cp==this.pages)
						str += '<a class="page123ld" title="末页">&nbsp;</a>';
					else
						str += '<a class="page123l" title="末页" href="javascript:jQuery(\'#'+aim+'\').pageChange('+this.pages+')">&nbsp;</a>';
				}
				else
				{
					str += '<a class="page123n" title="后'+this.tab.data.pageNum+'页" href="javascript:jQuery(\'#'+aim+'\').pageChange('+(temp*this.tab.data.pageNum+this.tab.data.pageNum+1)+')">&nbsp;</a>';
					str += '<a class="page123l" title="末页" href="javascript:jQuery(\'#'+aim+'\').pageChange('+this.pages+')">&nbsp;</a>';
				}
			}
			jQuery("#gp"+aim).append('<span class="page123">'+str+'</span>'+
				'<span class="pageInfo">共'+this.pages+'页'+this.tab.data.amount+'条记录,正处于第'+
				'<input type="text" id="j'+aim+'" value="'+this.tab.data.state.page+'" '+
					'onkeyup="value=value.replace(/[^\\d]/g,\'\') " '+
					'onbeforepaste="clipboardData.setData(\'text\',clipboardData.getData(\'text\').replace(/[^\\d]/g,\'\'))" '+
					'onkeydown="if(event.keyCode==13) if(jQuery(\'#'+aim+'\').pageChange(jQuery(\'#j'+aim+'\').val()) == false) this.value=\''+this.tab.data.state.page+'\';" '+
					'onfocus="this.select()"/>页</span>');
		};
		
		//发送分页,搜索,排序申请
		// 参数
		//	u url string
		//	d data json
		//	s success function
		
		this.connect = function(u,d)
		{
			jQuery().deleteJson();
			var dd = {
			searchString: '',
				page: 1
			};
			jQuery.extend(dd,this.tab.data.state);
			jQuery.extend(dd,d);
            //判断URL地址是否有?如果有截取?后所有参数信息
			var str = jQuery().splitUrl(u).p;
			var nu = new String;
			nu = '';
			nu = jQuery().splitUrl(u).u;
			str += '&' + dd.searchString;
			str += '&orderByDesc=' + dd.sortValue;
			str += '&columnName=' + dd.sortKey;
			str += '&orderByField=' + dd.sortField;
			str += '&curPageNum=' + dd.page;
			str += "&pageSize="+dd.rows;
			jQuery().cWait();
			//alert(str);
			jQuery.ajax({
				    type: "POST",
				     url: nu,
				   async: true,
				   data:str,
				dataType: "json",
				 success: function(json)
							{
								ag.jsonData=json.page;
								jQuery.extend(ag.tab.data.state,dd);
								
								//totalPageCount  总共有多少页   totalCount总共有多少数据   currentPageNo 当前页
								jQuery('.head_cell').removeClass('sort_a');
								jQuery('.head_cell').removeClass('sort_d');
								//排序样式
								if(dd.sortValue=='asc')
								{
									jQuery('#th'+aim+'~c'+dd.sortIndex).removeClass('sort_d');
									jQuery('#th'+aim+'~c'+dd.sortIndex).addClass('sort_a');
								}
								else if(dd.sortValue=='desc')
								{
									jQuery('#th'+aim+'~c'+dd.sortIndex).removeClass('sort_a');
									jQuery('#th'+aim+'~c'+dd.sortIndex).addClass('sort_d');
								}
								else
								{
									jQuery('#th'+aim+'~c'+dd.sortIndex).removeClass('sort_a');
									jQuery('#th'+aim+'~c'+dd.sortIndex).removeClass('sort_d');
								}
								
								//添加分页状态
								ag.tab.data.state.page = parseInt(ag.jsonData.currentPageNo);
								ag.tab.data.amount = parseInt(ag.jsonData.totalCount);
								
								//刷新表格
								ag.display();
								jQuery().saveJson();
								jQuery().cSuccess();
								jQuery().tableUpdateSuccess(aim,json);
							},
				   error: function()
				   			{
				   				jQuery().cError();
								jQuery().tableUpdateError(aim,dd);
							},
				 complete: function()
				 			{
				 				jQuery().cComplete();
				 			}
			});
			
		};
		
		//发送删除申请
		this.deleteRow = function(pks)
		{
			var u = this.tab.data.deleteUrl;
			var deleteData = new String;
			deleteData = '';
			var deleteData = jQuery().splitUrl(u).p;
			u = jQuery().splitUrl(u).u;
			deleteData += '&';
			for(var i=0;i<pks.length;i++)
			{
				deleteData += 'pids=' + pks[i];
				if(i<pks.length-1)
					deleteData += '&';
			}
			//alert(u+'?'+deleteData);
			jQuery.ajax({
				 url: u+'?'+deleteData,
			   cache: false,
			   dataType: "json",
		  beforeSend: function(pks)
		  				{
							jQuery().beforeDelete(pks);
						}, 
			 success: function(data)
						{
							jQuery().deleteSuccess(data);
							if(ag.tab.data.state.state=='page')
								ag.connect(ag.tab.data.pageUrl,{page:ag.tab.data.state.page});
							else if(ag.tab.data.state.state=='search')
								ag.connect(ag.tab.data.searchUrl,{page:ag.tab.data.state.page});
							else if(ag.tab.data.state.state=='adSearch')
								ag.connect(ag.tab.data.adSearchUrl,{page:ag.tab.data.state.page});
						},
			   error: function()
						{
							jQuery().deleteError();
						}
			});
		}
		
		//发送预览申请
		this.previewRow = function(id)
		{
			var u = this.tab.data.previewUrl;
			var str = "?pKey=" + id;
			//alert(u+str);
			jQuery.ajax({
				     url: u+str,
				dataType: "json",
				 success: function(json)
							{
								ag.jsonPreview=json;
								return true;
							},
				   error: function()
				   			{
								return false;
							}
			});
		}
		this.initExtendFunction = function(fns)
		{
			if(!fns)
				return;
			if(typeof(fns.tableUpdateSuccess)=='function')
				jQuery.fn.tableUpdateSuccess = fns.tableUpdateSuccess;
			if(typeof(fns.tableUpdateError)=='function')
				jQuery.fn.tableUpdateError = fns.tableUpdateError;
			if(typeof(fns.previewSuccess)=='function')
				jQuery.fn.previewSuccess = fns.previewSuccess;
			if(typeof(fns.previewError)=='function')
				jQuery.fn.previewError = fns.previewError;
			if(typeof(fns.beforeDelete)=='function')
				jQuery.fn.beforeDelete = fns.beforeDelete;
			if(typeof(fns.deleteSuccess)=='function')
				jQuery.fn.deleteSuccess = fns.deleteSuccess;
			if(typeof(fns.deleteError)=='function')
				jQuery.fn.deleteError = fns.deleteError;
			if(typeof(fns.adPreview)=='function')
				jQuery.fn.adPreview = fns.adPreview;
			if(typeof(fns.adSearch)=='function')
				jQuery.fn.adSearch = fns.adSearch;
			if(typeof(fns.adSearchAjax)=='function')
				jQuery.fn.adSearchAjax = fns.adSearchAjax;
			if(typeof(fns.newCell)=='function')
				jQuery.fn.newCell = fns.newCell;
		}
		this.initExtendFunction(initData.exfn);
		/***************************************************************************/
		                                                                           //
		//构建表格                                                                            //       \(≧▽≦)/
		this.tab = new Table(initData.tab,initData.col,aim);                       //       \(≧▽≦)/
		                                                                           //       \(≧▽≦)/
		//构建表头                                                                            //     构建grid主过程
		this.cols = new Array;                                                     //       \(≧▽≦)/
		for(var i=0;i<initData.col.length;i++)                                     //       \(≧▽≦)/
			this.cols[i] = new Column(initData.col[i],i,'th'+aim);                 //       \(≧▽≦)/
		                                                                           //
		//如果没有搜索url就默认与分页同url                                              //
		//if(this.tab.data.searchUrl=='')                                          //
			//this.tab.data.searchUrl = this.tab.data.pageUrl;                     //
		                                                                           //
		/***************************************************************************/
		
		jQuery("#th"+aim).append('<div id="r'+aim+'" class="rc" style="position:absolute;display:none"></div>');
		jQuery("#th"+aim)[0].onmousedown = jQuery().rightClick;
		jQuery("#th"+aim)[0].oncontextmenu = function(){
			return false; 
		}
		
	}
	
	
	/*******************************************************************************************************************************************
	                                                              我自己用的接口(grid操作)
	********************************************************************************************************************************************/
	/*
	* 删除验证
	*/
	jQuery.fn.checkChange = function(aim)
	{
		var c = jQuery('input[name=check_alltrtb'+aim+'][checked]');
		if(c.length!=0)
		{
			jQuery('#'+aim+' *[needChecked="true"]').removeAttr('disabled');
			jQuery('#'+aim+' *[needChecked="true"]').removeClass('disabled');
			var ids = new Array;
			jQuery.each(c,function(i,n){
				ids.push(n.id);
			});
			return ids;
		}
		else
		{
			jQuery('#'+aim+' *[needChecked="true"]').attr('disabled','true');
			jQuery('#'+aim+' *[needChecked="true"]').addClass('disabled');
		}
	}
	
	/*
	* 换页
	*/
	jQuery.fn.pageChange = function(p)
	{
		if(p<1||p>ag.pages)
			return false;
		if(ag.tab.data.state.state=='page')
			ag.connect(ag.tab.data.pageUrl,{page:p});
		else if(ag.tab.data.state.state=='search')
			ag.connect(ag.tab.data.searchUrl,{page:p});
		else if(ag.tab.data.state.state=='adSearch')
			jQuery().adSearchAjax(ag.tab.data.adSearchUrl,{page:p});
		return;
	}
	
	/*
	* 重置
	*/
	jQuery.fn.gridReset = function()
	{
		//if(ag.tab.data.state.state=='search'||ag.tab.data.state.sortKey!='')
		ag.connect(ag.tab.data.pageUrl,{state:'page',searchString:'',sortKey:'',sortValue:'',sortField:'',page:1});
	}
	
	/*
	* 预览
	*/
	jQuery.fn.preView = function(pKey)
	{
		var thisId = this[0].id;
		var rowNum = thisId.charAt(thisId.length-1);
		//如果设置为不可预览则返回
		if(ag.tab.data.previewStyle=='none'||ag.tab.data.previewStyle=='')
			return;
		//如果尚有预览处于打开状态并点击了另一行
		if(Row.preViewIndex!=''&&Row.preViewIndex!=thisId)
			jQuery("#"+Row.preViewIndex+" div").css("display","none");
		//如果点击已经打开的本行则关闭预览
		if(Row.preViewIndex==thisId&&jQuery("#"+Row.preViewIndex+" div").css("display")!="none")
		{
			jQuery("#"+Row.preViewIndex+" div").css("display","none");
		}
		else//打开一个预览
		{
			//如果之前未打开过该行预览
			if(jQuery("#"+thisId+" div").length==0)
			{
				//如果设置为默认预览样式
				if(ag.tab.data.previewStyle=='default')
				{
					if(ag.previewRow(pKey))
					{
						jQuery("#"+thisId).append('<div id="pre'+thisId+'" style="display:none;"><a href="'+ag.jsonPreview[ag.tab.data.linkKey]+'"><img src="'+ag.jsonPreview[ag.tab.data.imgKey]+'" height="100" width="100" />'+ag.jsonPreview[ag.tab.data.detailKey]+'</a></div>');
						jQuery("#"+thisId+" div").css("display","");
						jQuery().previewSuccess(thisId,'pre'+thisId);
					}
					else
					{
						jQuery().previewError(thisId);
					}
				}
				else
				{
					var inn = jQuery().adPreview(pKey,thisId,'pre'+thisId);
					if(!inn||inn=='')
					{
						jQuery().previewError(thisId);
						return;
					}
					jQuery("#"+thisId).append('<div id="pre'+thisId+'" style="display:none;"></div>');
					jQuery("#pre"+thisId).append(inn);
					jQuery("#"+thisId+" div").css("display","");
					jQuery().previewSuccess(thisId,'pre'+thisId);
				}
			}
			else//如果之前打开过该行预览
			{
				jQuery("#"+thisId+" div").css("display","");
			}
			Row.preViewIndex = thisId;
		}
	}
	
	/*
	* 排序
	*/
	jQuery.fn.colSort = function(name,f,index)
	{
		if(!ag.cols[index].data.sortable)
			return;
		var ss = ag.tab.data.state.sortValue;
		var cs;
		if(name == ag.tab.data.state.sortKey)
		{
			if(ss=='')
				cs = "asc";
			else if(ss=='asc')
				cs = "desc";
			else if(ss=='desc')
				cs = "asc";
		}
		else
		{
			cs = "asc";
		}
		var data = {
			sortIndex:index,
			  sortKey:name,
			sortValue:cs,
			sortField:f,
			     page:1
		}
		if(ag.tab.data.state.state=='page')
			ag.connect(ag.tab.data.pageUrl,data);
		else if(ag.tab.data.state.state=='search')
			ag.connect(ag.tab.data.searchUrl,data);
		else if(ag.tab.data.state.state=='adSearch')
			ag.connect(ag.tab.data.adSearchUrl,data);
	}
	
	/*
	* 添加
	*/
	jQuery.fn.rowAdd = function()
	{
		window.location = ag.tab.data.addUrl;
	}
	
	/*
	* 编辑
	*/
	jQuery.fn.rowEdit = function(pKey)
	{
		window.location = ag.tab.data.editUrl + '?' + ag.tab.data.pKey + '=' + pKey;
	}
	
	/*
	* 删除
	*/
	jQuery.fn.rowDelete = function(aim)
	{
		var d = jQuery("#tb"+aim+" input[checked]");
		var pks = new Array;
		for(var i=0;i<d.length;i++)
			pks.push(d[i].id);
		ag.deleteRow(pks);
	}
	
	/*
	* 搜索
	*/
	jQuery.fn.rowSearch = function(n,v)
	{
		
		var value =v;
		var url = ag.tab.data.searchUrl;
		var data = 
		{
			      state: 'search',
		   searchString: 'searchField='+n+'&searchValue='+value,
			    sortKey: '',
			  sortValue: '',
			  sortField: '',
			       page: 1
		}
		ag.connect(url,data);
	}
	
	/*
	* 刷新
	*/
	jQuery.fn.gridRefresh = function()
	{
		if(ag.tab.data.state.state=='page')
			ag.connect(ag.tab.data.pageUrl,{page:ag.tab.data.state.page});
		else if(ag.tab.data.state.state=='search')
			ag.connect(ag.tab.data.searchUrl,{page:ag.tab.data.state.page});
		else if(ag.tab.data.state.state=='adSearch')
			ag.connect(ag.tab.data.adSearchUrl,{page:ag.tab.data.state.page});
	}
	
	jQuery.fn.getAdSearchUrl = function()
	{
		return ag.tab.data.adSearchUrl;
	}
	
	/*
	* 将url从?分开
	*/
	jQuery.fn.splitUrl = function(s)
	{
		var d;
		if(s.indexOf('?')==-1)
		{
			d = {
				u: s,
				p: ''
			}
		}
		else
		{
			d = {
				u: s.substring(0,s.indexOf('?')),
				p: s.substring(s.indexOf('?')+1,s.length)
			}
		}
		return d;
	}
	
	/*
	* 获取grid对象
	*/
	jQuery.fn.getGrid = function(){
		return ag;
	}
	
	/*
	* 获得json
	*/
	jQuery.fn.getJsonData = function()
	{
		var j = new Object;
		j = ag.jsonData;
		return j;
	}
	
	/*
	* 更新json更新视图
	*/
	jQuery.fn.updateView = function(json)
	{
		ag.jsonData = new Object;
		ag.jsonData = json;
		ag.display();
	}
	
	/*
	* 将当前grid的内容与状态写入cookie
	*/
	jQuery.fn.saveJson = function()
	{
		if(ag.tab.data.cookie){
			jQuery.cookie("gridJson",ag.jsonData);
			jQuery.cookie("gridState",ag.tab.data);
		}
	}
	
	/*
	* 读取cookie显示grid
	*/
	jQuery.fn.loadJson = function()
	{
		if(ag.tab.data.cookie){
			ag.jsonData = jQuery.cookie("gridJson");
			jQuery.extend(ag.tab.data,jQuery.cookie("gridState"));
			ag.display();
		}
	}
	
	/*
	* 清除grid的cookie
	*/
	jQuery.fn.deleteJson = function()
	{
		if(ag.tab.data.cookie){
			jQuery.cookie("gridJson",null);
			jQuery.cookie("gridState",null);
		}
	}
	
	/*
	* 连接等待
	*/
	jQuery.fn.cWait = function()
	{
		jQuery(document.body).append('<div class="grid_mask"></div>');
		jQuery(document.body).append('<span class="grid_mask_wait"><span></span>连接中请稍后...</span>');
		jQuery(".grid_mask_wait").css("margin-top",(jQuery(".grid_mask_wait").height()/(-2))+'px');
		jQuery(".grid_mask_wait").css("margin-left",(jQuery(".grid_mask_wait").width()/(-2))+'px');
	}
	
	/*
	* 连接失败
	*/
	jQuery.fn.cError = function()
	{
		jQuery(".grid_mask_wait").remove();
	}
	
	/*
	* 连接成功
	*/
	jQuery.fn.cSuccess = function()
	{
		jQuery(".grid_mask_wait").remove();
	}
	
	/*
	* 连接完成
	*/
	jQuery.fn.cComplete = function()
	{
		jQuery(".grid_mask").remove();
	}
	
	/*
	* grid消息提示
	*/
	jQuery.fn.message = function(s,fnb,fn)
	{
		jQuery(".ghint").empty();
		jQuery(".ghint").append(s);
		fnb();
		jQuery(".ghint").animate(
			{
				right: '30px',
				opacity: 1
			},
			{
				duration: 500,
				complete: function(){
					jQuery().messageKeep(2000,fn);
				}
			}
		);
	}
	jQuery.fn.messageKeep = function(t,fn)
	{
		jQuery(".ghint").animate(
			{
				right: '30px',
				opacity: 1
			},
			{
				duration: t,
				complete: function(){
					jQuery().messageOut(fn);
				}
			}
		);
	}
	jQuery.fn.messageOut = function(fn)
	{
		jQuery(".ghint").animate(
			{
				right: '0px',
				opacity: 0
			},
			{
				duration: 1000,
				complete: function(){fn()}
			}
		);
	}
	
	/*
	* 设置当前搜索排序状态
	* 参数
	*	data state数据
	*/
	jQuery.fn.changeState = function(data)
	{
		ag.tab.data.state = data;
	}
	
	/*
	* 表头右键
	*/
	jQuery.fn.rightClick = function(){if(event.button==2){
		jQuery(".rc").empty(); 
		this.lastChild.style.display = "block";
		this.lastChild.style.left = event.x + this.parentNode.scrollLeft;
		this.lastChild.style.top = event.y + this.parentNode.scrollTop;
		for(var i=0;i<ag.cols.length;i++){
			if(ag.cols[i].data.type == 'checkbox')
				continue;
			var checked = '';
			var str = '';
			if(!ag.cols[i].data.hide)
				checked = 'checked="true"';
			if(ag.cols[i].data.title == ''&&ag.cols[i].data.type!='edit')
				str = '第'+i+1+'列';
			else if(ag.cols[i].data.type=='edit')
				str = '编辑';
			else
				str = ag.cols[i].data.title;
			jQuery(this.lastChild).append('<div><input type="checkbox" '+checked+' id="colCheck'+i+'" onclick="jQuery().colShowHide('+i+',this)" /> '+
				'<label onclick="'+
					'if(jQuery(\'#colCheck'+i+'\').attr(\'checked\')) '+
						'jQuery(\'#colCheck'+i+'\').removeAttr(\'checked\'); '+
					'else '+
						'jQuery(\'#colCheck'+i+'\').attr(\'checked\',\'true\');'+
					'jQuery().colShowHide('+i+',jQuery(\'#colCheck'+i+'\')[0])" '+
				'>'+str+'</label></div>'
			);
		}
	}}
	
	/*
	* 显示或隐藏列
	*/
	jQuery.fn.colShowHide = function(index,elm)
	{
		var aim = elm.parentNode.parentNode.parentNode.parentNode.id;
		if(elm.checked)
		{
			jQuery("#"+aim+" *[col='"+index+"']").show();
			ag.cols[index].data.hide = false;
		}
		else
		{
			jQuery("#"+aim+" *[col='"+index+"']").hide();
			ag.cols[index].data.hide = true;
		}
	}
	
	/*
	* 列宽变更
	*/
	jQuery.fn.colWidthChange = function()
	{
		
	}
	
	/*
	* 视图变更
	*/
	jQuery.fn.gridViewChange = function()
	{
		
	}
	
	/*
	* 全选
	*/
	jQuery.fn.checkAll = function()
	{
		var thisId = this[0].id;
		thisId = thisId.slice(5);
		if(jQuery("#allth"+thisId).attr("checked")!=false)
			jQuery("input[name='check_alltrtb"+thisId+"']").attr("checked", true);
		else
			jQuery("input[name='check_alltrtb"+thisId+"']").attr("checked", false);
	}
	/***********************************************************************************************************************************************
                                                                          待实现函数
	************************************************************************************************************************************************/
	//grid数据更新成功(分页,搜索,排序)
	// 参数
	//	 aim  父div的id
	//	json  更新的json
	jQuery.fn.tableUpdateSuccess = function(aim,json)
	{
		return;
	}
	
	//grid数据更新失败(分页,搜索,排序)
	// 参数
	//	  aim  父div的id
	//	state  状态
	jQuery.fn.tableUpdateError = function(aim,state)
	{
		return;
	}
	
	//预览成功
	//	 aim  父div的id
	//	 cId  预览div的id
	jQuery.fn.previewSuccess = function(pId,cId)
	{
		return;
	}
	
	//预览失败
	//	 aim  父div的id
	jQuery.fn.previewError = function(pId)
	{
		return;
	}
	
	//删除之前
	// 参数
	//	pks  删除目标的id array
	jQuery.fn.beforeDelete = function(pks)
	{
		return;
	}
	
	//删除成功
	jQuery.fn.deleteSuccess = function(data)
	{
		return;
	}
	
	//删除失败
	jQuery.fn.deleteError = function()
	{
		return;
	}

	//高级预览
	// 参数
	//	  pKey 该行主键
	//	   pId 父div的id
	//	   cId 添加层的id
	// 返回
	//	string 预览的HTML内容
	jQuery.fn.adPreview = function(pKey,pId,cId)
	{
		return false;
	}
	
	//高级搜索
	// 参数
	//	pId 父div的id
	//	cId 添加层的id
	// 返回 string 高级搜索HTML内容
	jQuery.fn.adSearch = function(pId,cId)
	{
		return false;
	}
	
	/*
	* 高级搜索ajax
	*/
	jQuery.fn.adSearchAjax = function()
	{
//		jQuery.ajax({
//			 success: function(json)
//				{
//					jQuery().changeState(data);
//					jQuery().updateView(json.page);
//				},
//		});
		return false;
	}
	
	/*
	* 自定义单元格内容
	*/
	jQuery.fn.newCell = function(info)
	{
		alert("请重写函数jQuery.fn.newCell,返回单元格中的HTML");
	}
	
	/***********************************************************************************************************************************************
	                                                                           公共
	************************************************************************************************************************************************/

	
	/*
	* 创建编辑区功能块
	* 参数
	*	i 目标功能块索引 int 原先处于该索引以及该索引之后的功能块将向后移
	*/
	jQuery.fn.gridAddCtrlTab = function(i)
	{
		var t = '';
		if(i!=0)
			t += '<span class="edit_split"></span>';
		if(jQuery('#'+this[0].id+' .grid_edit .edit_b:eq('+i+')').length==0)
			jQuery('#'+this[0].id+' .grid_edit').append(t+'<span class="edit_b"></span>');
		else
			jQuery('#'+this[0].id+' .grid_edit .edit_b:eq('+i+')').before('<span class="edit_b"></span>'+t);
	}
	
	/*
	* 创建编辑区功能块
	* 参数
	*	ti 目标功能块索引 int
	*	bi 目标按钮索引 int
	*/
	jQuery.fn.gridAddCtrlButton = function(cfg)
	{
		var d = {
			tIndex:0,
			bIndex:0,
			title:'',
			className:'',
			needChecked: false,
			fn: function(){alert('请配置fn')},
			event:'onclick'
		}
		jQuery.extend(d,cfg);
		var nc = '';
		if(d.needChecked)
			nc = 'needChecked="true" disabled';
		var id = '';
		if(d.title!='')
			id = d.title;
		else{
			alert('请设置新建按钮的title,不要重复');
			return;
		}
		if(d.bIndex==0)
		{
			jQuery('#'+this[0].id+' .grid_edit .edit_b:eq('+d.tIndex+')').prepend('<a href="#" '+nc+' class="'+d.className+'" id="'+id+'"><span></span>'+d.title+'</a>');
		}
		else
		{
			if(jQuery('#'+this[0].id+' .grid_edit .edit_b:eq('+d.tIndex+') a:eq('+d.bIndex+')').length==0)
				jQuery('#'+this[0].id+' .grid_edit .edit_b:eq('+d.tIndex+')').append('<a href="#" '+nc+' class="'+d.className+'" id="'+id+'"><span></span>'+d.title+'</a>');
			else
				jQuery('#'+this[0].id+' .grid_edit .edit_b:eq('+d.tIndex+') a:eq('+d.bIndex+')').before('<a href="#" '+nc+' class="'+d.className+'" id="'+id+'"><span></span>'+d.title+'</a>');
		}
		if(typeof(d.fn) == 'function')
		{
			jQuery("#"+id).click(d.fn);
		}
	}
	
	/*
	* 创建表格
	* 参数
	*	initData 初始化参数 json 格式见注释
	*/
	jQuery.fn.ajaxGrid = function(initData)
	{
		if(ag!=null)
			alert("Beta:现在只支持每页一个表格");
		jQuery().cWait();
		if(typeof(initData.exfn.before)=='function')
			initData.exfn.before();
		ag = new Grid(initData,this[0].id);
		if(!ag.tab.data.cookie)
		{
			jQuery().deleteJson();
			ag.connect(ag.tab.data.pageUrl,{page:ag.tab.data.state.page});
		}
		else
		{
			if(jQuery.cookie("gridJson"))
			{
				jQuery().loadJson();
			}
			else
			{
				ag.connect(ag.tab.data.pageUrl,{page:ag.tab.data.state.page});
			}
		}
		jQuery().cSuccess();
		jQuery().cComplete();
		if(typeof(initData.exfn.after)=='function')
			initData.exfn.after();
		jQuery().checkChange(this[0].id);
	}
	
})(jQuery)