var tempjj = new Array;
var imgUpLoader = new Object;
var idArray = new Array;
var isDelete = true;
function bMChange(elm)
{
	var t = jQuery(elm);
	if(t.height()>232)
	{
		t.removeAttr("width");
		t.attr("height","232");
	}
	else
	{
		t[0].style.marginTop = (232 - t.height())/2 +"px";
	}
}
function sMChange(elm)
{
	var t = jQuery(elm);
	if(t.height()==0)
		return;
	if(t.height()>80)
	{
		t.removeAttr("width");
		t.attr("height","80");
	}
	else
	{
		t[0].style.marginTop = (80 - t.height())/2 +"px";
	}
}
function sRightClick(id,elm)
{
	var ii = jQuery("#"+id+" .imgUpAllImgs img").index(elm);
	var z = '';
	if(imgUpLoader[opt.id].zoom)
		z = '<a href="javascript:imgUpLoader[\''+opt.id+'\'].imgZoomOut('+ii+')">缩小</a><hr/>';
	else
		z = '<a href="javascript:imgUpLoader[\''+opt.id+'\'].imgZoomIn('+ii+')">放大</a><hr/>';
	jQuery(".rightMenu").remove();
	var rm = jQuery('<div class="rightMenu">'+
		'<a href="#" onclick="window.open(\''+elm.src+'\')">打开</a>'+
		z+
		'<a href="javascript:imgUpLoader[\''+opt.id+'\'].checkAll()">全选</a>'+
		'<a href="javascript:imgUpLoader[\''+opt.id+'\'].openImg()">添加一张</a>'+
		'<a href="javascript:imgUpLoader[\''+opt.id+'\'].deleteImg(\''+ii+'\')">删除这张</a><hr/>'+
		'<a href="javascript:imgUpLoader[\''+opt.id+'\'].hideManage()">返回</a>'+
	'</div>');
	rm[0].style.pixelTop=event.clientY - jQuery("#"+id+" .imgUpAllImgs").offset().top;
	rm[0].style.pixelLeft=event.clientX - jQuery("#"+id+" .imgUpAllImgs").offset().left;
	jQuery("#"+id+" .imgUpImgManager").append(rm);
}
function bRightClick(id,elm)
{
	var ii = jQuery("#"+id+" .imgUpImgBlock img").index(elm);
	jQuery(".rightMenu").remove();
	var rm = jQuery('<div class="rightMenu">'+
		'<a href="#" onclick="window.open(\''+elm.src+'\')">打开</a><hr/>'+
		'<a href="javascript:imgUpLoader[\''+opt.id+'\'].openImg()">添加一张</a>'+
		'<a href="javascript:imgUpLoader[\''+opt.id+'\'].deleteImg(\''+ii+'\')">删除这张</a>'+
	'</div>');
	rm[0].style.pixelTop=event.clientY - jQuery("#"+id+" .imgUpImgBlock").offset().top;
	rm[0].style.pixelLeft=event.clientX - jQuery("#"+id+" .imgUpImgBlock").offset().left;
	jQuery("#"+id+" .imgUpImgBlock").append(rm);
}
function newImgUpLoader(opt)
{
	if(jQuery("#"+opt.id).length<1||typeof(opt)!="object")
		return;
	imgUpLoader[opt.id] = new function()
	{
		var id = opt.id;
		var me = this;
		var imgs = new Array;
		var current = 0;
		var cfg = {
			multiple:true,
			getUrl:'',
			addUrl:'',
			deleteUrl:'',
			id:''
		};
		jQuery.extend(cfg,opt);
		this.zoom = false;
		this.checkall = false;
		
		this.getUrls = function()
		{
			var urls = '';
			jQuery.each(imgs,function(i,n){
				if(n.url!='')
					urls += n.url;
				if(i<imgs.length-1)
					urls +=  ',';
			})
			return urls;
		}
		this.show = function(json)
		{
			if(typeof(json)=='string')
			{
				var sss = json.split(",");
				jQuery.each(sss,function(i,n){
					var obj = {
						url: n,
						title: '第'+(i+1)+'张',
						id: ''+i
					};
					tempjj.push(obj);
				});
			}
			else if(typeof(json)=='object')
			{
				tempjj = json;
			}
			jQuery("#"+id+" .imgUpImgs").empty();
			jQuery("#"+id+" .imgUpAllImgs").empty();
			imgs = new Array;
			if(tempjj.length==0||(tempjj.length==1&&tempjj[0].url=='')||json=="我无语啊居然没配getUrl")
			{
				//pushImg('','','');
			}
			else
			{
				jQuery.each(tempjj,function(i,n){
					pushImg(n.url,n.title,n.id);
				});
				if(current>tempjj.length-1)
					current = tempjj.length-1;
				changeDetail(tempjj[current].title);
			}
			
			showImg();
		}
		this.insertOne = function(iu,it,iid,st)
		{
			if(imgs.length==1&&imgs[0].url=='')
				imgs = new Array;
			if(st==1)
			{
				if(iu!='')
				{
					pushImg(iu,it,iid);
					showImgAndDetail();
					this.showHint("图片添加成功");
					setTimeout(this.hideHint,3000);
					jQuery("#"+id+" .imgUpManagerButtons a:eq(0)").removeAttr('disabled');
					jQuery("#"+id).css("cursor","default");
				}
			}
			else
			{
				this.showHint("图片上传失败,请重试");
				setTimeout(this.hideHint,3000);
				jQuery("#"+id+" .imgUpManagerButtons a:eq(0)").removeAttr('disabled');
				jQuery("#"+id).css("cursor","default");
			}
			this.imgZoomOut(jQuery("#"+id+" .imgUpAllImgs>span").length-1);
			jQuery("#"+id+" .imgUpSubmit .imgUpSubmitText").empty();
			jQuery("#"+id+" .imgUpSubmit .imgUpSubmitText").append('<form  action="'+cfg.addUrl+'" target="imgUpFrame'+id+'" method="POST" name="imgUpForm'+id+'" id="imgUpForm'+id+'" enctype="multipart/form-data"><input type="file" id="imgUpFile'+id+'" name="upLoadFile" value=""/><input  type="hidden" name="fileNames" id="fileField" value="filename"/><input type="submit" disabled="disabled" id="imgUpSubmit'+id+'" value="提交"/></form>');
			jQuery("#imgUpForm"+id)[0].onsubmit = function(){
				me.upSubmit();
			};
			jQuery('#imgUpFile'+id).change(function(){
				if(this.value == '')
				{
					me.hidePreview();
					return;
				}
				var h = this.value.substring(this.value.lastIndexOf(".")+1,this.value.length);
				if(h!="jpg"&&h!="gif"&&h!="png")
				{
					me.showHint("请选择jpg,gif或png图片");
					setTimeout(me.hideHint,2000);
					me.hidePreview();
					jQuery('#imgUpSubmit'+id).attr("disabled","disabled");
				}
				else
				{
					jQuery('#imgUpSubmit'+id).removeAttr("disabled");
					me.hidePreview(function(){me.showPreview(jQuery('#imgUpFile'+id).val());});
				}
			});
		}
		this.deleteOne = function(ls)
		{
			if(ls.length<1)
				return;
			var na = new Array;
			jQuery.each(imgs,function(i,n){
				var aru = false;
				jQuery.each(ls,function(j,m){
					if(n.url==m)
						aru = true;
				});
				if(!aru)
					na.push(n);
			});
			//tempjj = na;
			this.show(na);
			this.imgZoomOut(0);
		}
		this.changeImg = function(i)
		{
			if(current+i<0||current+i>jQuery(".imgUpImgs img").length-1)
			{
				this.showHint("没有了");
				setTimeout(this.hideHint,1000);
				return;
			}
			else
			{
				jQuery("#"+id+" .imgUpImgs img").fadeOut("fast");
				jQuery("#"+id+" .imgUpImgs img:eq("+(current+i)+")").fadeIn("fast");
				current = current + i;
				changeDetail(imgs[current].title);
			}
		};
		this.showManage = function()
		{
			jQuery("#"+id+" .imgUpImgsMask").hide();
			jQuery("#"+id+" .imgUpImgBlockMask2").show();
			jQuery("#"+id+" .imgUpImgManager").show();
			jQuery.each(jQuery("#"+id+" .imgUpImgSmall img"),function(i,n){
				sMChange(n);
			});
		};
		this.hideManage = function()
		{
			jQuery("#"+id+" .imgUpImgBlockMask2").hide();
			jQuery("#"+id+" .imgUpImgManager").hide();
			jQuery("#"+id+" .imgUpImgsMask").show();
			this.hideSubmit();
			this.hidePreview();
		};
		this.checkAll = function()
		{
			var allspan = jQuery("#"+id+" .imgUpAllImgs>span");
			if(me.checkall)
			{
				jQuery.each(allspan,function(i,n){
					imgs[i].checked = false;
					var t = jQuery(n);
					if(!this.zoom)
					{
						t.addClass("imgUpImgSmall");
						t.removeClass("imgUpImgSmallSelected");
					}
					else
					{
						t.addClass("imgUpImgBig");
						t.removeClass("imgUpImgBigSelected");
					}
				});
				me.checkall = false;
				jQuery("#"+id+" .imgUpManagerButtons a:eq(1)").attr("disabled","disabled");
			}
			else
			{
				jQuery.each(allspan,function(i,n){
					imgs[i].checked = true;
					var t = jQuery(n);
					if(!this.zoom)
					{
						t.removeClass("imgUpImgSmall");
						t.addClass("imgUpImgSmallSelected");
					}
					else
					{
						t.removeClass("imgUpImgBig");
						t.addClass("imgUpImgBigSelected");
					}
				});
				me.checkall = true;
				jQuery("#"+id+" .imgUpManagerButtons a:eq(1)").removeAttr("disabled");
			}
		}
		this.checkImg = function(elm)
		{
			var t = jQuery(elm);
			var index = jQuery("#"+id+" .imgUpAllImgs>span").index(t);
			if(imgs[index].checked)
			{
				imgs[index].checked = false;
				if(!this.zoom)
				{
					t.removeClass("imgUpImgSmallSelected");
					t.addClass("imgUpImgSmallHover");
				}
				else
				{
					t.removeClass("imgUpImgBigSelected");
					t.addClass("imgUpImgBigHover");
				}
			}
			else
			{
				imgs[index].checked = true;
				if(!this.zoom)
				{
					t.removeClass("imgUpImgSmallHover");
					t.addClass("imgUpImgSmallSelected");
				}
				else
				{
					t.removeClass("imgUpImgBigHover");
					t.addClass("imgUpImgBigSelected");
				}
			}
			var f = false;
			jQuery.each(imgs,function(i,n){
				if(n.checked)
					f = true;
			});
			if(f)
				jQuery("#"+id+" .imgUpManagerButtons a:eq(1)").removeAttr("disabled");
			else
				jQuery("#"+id+" .imgUpManagerButtons a:eq(1)").attr("disabled","disabled");
		};
		this.hoverLImg = function(elm)
		{
			var t = jQuery(elm);
			var index = jQuery("#"+id+" .imgUpAllImgs>span").index(t);
			elm.childNodes[1].style.display = "none";
			if(imgs[index].checked)
				return;
			if(!this.zoom)
			{
				jQuery(elm).removeClass("imgUpImgSmall");
				jQuery(elm).addClass("imgUpImgSmallHover");
			}
			else
			{
				jQuery(elm).removeClass("imgUpImgBig");
				jQuery(elm).addClass("imgUpImgBigHover");
			}
		};
		this.outLImg = function(elm)
		{
			var t = jQuery(elm);
			var index = jQuery("#"+id+" .imgUpAllImgs>span").index(t);
			elm.childNodes[1].style.display = "";
			if(imgs[index].checked)
				return;
			if(!this.zoom)
			{
				jQuery(elm).removeClass("imgUpImgSmallHover");
				jQuery(elm).addClass("imgUpImgSmall");
			}
			else
			{
				jQuery(elm).removeClass("imgUpImgBigHover");
				jQuery(elm).addClass("imgUpImgBig");
			}
		};
		this.hoverBImg = function(elm)
		{
			jQuery("#"+id+" .imgUpImgsMask").hide();
			jQuery("#"+id+" .imgUpImgInfoMask").slideDown("fast");
			jQuery("#"+id+" .imgUpImgInfo").slideDown("fast");
		};
		this.outBImg = function(elm)
		{
			jQuery("#"+id+" .imgUpImgInfoMask").slideUp("fast");
			jQuery("#"+id+" .imgUpImgInfo").slideUp("fast");
			jQuery("#"+id+" .imgUpImgsMask").show();
		};
		this.upSubmit = function()
		{
			this.hidePreview();
			jQuery("#imgUpForm"+id).css("position","absolute");
			jQuery("#imgUpForm"+id).css("left","-10000px");
			jQuery("#"+id+" .imgUpSubmit .imgUpSubmitText").append("...请稍后...");
			jQuery("#"+id+" .imgUpManagerButtons a:eq(0)").attr('disabled','disabled');
			jQuery("#"+id).css("cursor","wait");
		}
		this.openImg = function()
		{
			this.showManage()
			var file = jQuery("#imgUpFile"+id);
			var fileForm = jQuery("#imgUpForm"+id);
			if(jQuery("#"+id+" .imgUpSubmit").css('display')=='none')
			{
				this.showSubmit();
			}
			else
			{
				this.hideSubmit();
				this.hidePreview();
			}
			jQuery('#imgUpFile'+id).change(function(){
				if(this.value == '')
				{
					me.hidePreview();
					return;
				}
				var h = this.value.substring(this.value.lastIndexOf(".")+1,this.value.length).toLowerCase();
				if(h!="jpg"&&h!="gif"&&h!="png")
				{
					me.showHint("请选择jpg,gif或png图片");
					setTimeout(me.hideHint,2000);
					me.hidePreview();
					jQuery('#imgUpSubmit'+id).attr("disabled","disabled");
				}
				else
				{
					jQuery('#imgUpSubmit'+id).removeAttr("disabled");
					me.hidePreview(function(){me.showPreview(jQuery('#imgUpFile'+id).val());});
				}
			});
		};
		this.deleteImg = function(ii)
		{
			var l = new Array;
			var ls = new Array;
			var s = '';
			var su;
			if(!ii)
			{
				jQuery.each(imgs,function(i,n){
					if(n.checked)
					{
						l.push(n.id);
						s += "path="+n.url+"&";
						ls.push(n.url);
					}
				});
			}
			else
				su = imgs[ii].url;
			if(typeof(su)=="string")
			{
				s = "path="+su;
				ls.push(su);
			}
			this.showHint("...删除中...");
			jQuery("#imgUpForm"+id).css("cursor","wait");
			//alert(s);
			jQuery.ajax({
				type: "POST",
				url: cfg.deleteUrl,
				data: s,
				success: function(json){
							jQuery("#imgUpForm"+id).css("cursor","default");
							me.hideHint();
							me.showHint("删除成功");
							setTimeout(me.hideHint,2000);
							me.deleteOne(ls);
						},
				error: function(){
							jQuery("#imgUpForm"+id).css("cursor","default");
							me.showHint("连接失败了");
							setTimeout(me.hideHint,2000);
						}
			});
		};
		this.imgUpLoadSuccess = function(u,t,i)
		{
			pushImg(u,t,i);
		};
		this.imgZoomIn = function(ii)
		{
			var allspan = jQuery("#"+id+" .imgUpAllImgs>span");
			jQuery.each(allspan,function(i,n){
				imgs[i].checked = false;
				var t = jQuery(n);
				t.removeClass("imgUpImgSmallSelected");
				t.addClass("imgUpImgSmall");
			});
			me.checkall = false;
			jQuery("#"+id+" .imgUpManagerButtons a:eq(1)").attr("disabled","disabled");
			
			jQuery("#"+id+" .imgUpAllImgs .imgUpImgSmall").removeClass("imgUpImgSmall").addClass("imgUpImgBig");
			jQuery("#"+id+" .imgUpAllImgs .imgUpImgSmallMask").removeClass("imgUpImgSmallMask").addClass("imgUpImgBigMask");
			var si = jQuery("#"+id+" .imgUpAllImgs span img");
			jQuery.each(si,function(i,n){
				jQuery(n).removeAttr("height");
				jQuery(n).removeAttr("width");
				if(n.width>n.height)
					n.width = "250";
				else
					n.height = "250";
				if(jQuery(n).height()<250)
					n.style.marginTop = (250 - jQuery(n).height())/2 +"px";
			});
			this.zoom = true;
			jQuery("#"+id+" .imgUpAllImgs")[0].scrollTop = jQuery("#"+id+" .imgUpAllImgs>span:eq("+ii+")").offset().top - jQuery("#"+id+" .imgUpAllImgs").offset().top + jQuery("#"+id+" .imgUpAllImgs")[0].scrollTop - 2;
			
		}
		this.imgZoomOut = function(ii)
		{
			var allspan = jQuery("#"+id+" .imgUpAllImgs>span");
			jQuery.each(allspan,function(i,n){
				imgs[i].checked = false;
				var t = jQuery(n);
				t.removeClass("imgUpImgBigSelected");
				t.addClass("imgUpImgBig");
			});
			me.checkall = false;
			jQuery("#"+id+" .imgUpManagerButtons a:eq(1)").attr("disabled","disabled");
			
			jQuery("#"+id+" .imgUpAllImgs .imgUpImgBig").removeClass("imgUpImgBig").addClass("imgUpImgSmall");
			jQuery("#"+id+" .imgUpAllImgs .imgUpImgBigMask").removeClass("imgUpImgBigMask").addClass("imgUpImgSmallMask");
			var si = jQuery("#"+id+" .imgUpAllImgs span img");
			jQuery.each(si,function(i,n){
				jQuery(n).removeAttr("height");
				jQuery(n).removeAttr("width");
				if(n.width>n.height)
					n.width = "80";
				else
					n.height = "80";
				if(jQuery(n).height()<80)
					n.style.marginTop = (80 - jQuery(n).height())/2 +"px";
			});
			this.zoom = false;
			if(jQuery("#"+id+" .imgUpAllImgs>span:eq("+ii+")").length>0)
				jQuery("#"+id+" .imgUpAllImgs")[0].scrollTop = jQuery("#"+id+" .imgUpAllImgs>span:eq("+ii+")").offset().top - jQuery("#"+id+" .imgUpAllImgs").offset().top + jQuery("#"+id+" .imgUpAllImgs")[0].scrollTop - 80;
		}

		var imgUpLoaderInit = function()
		{
			var mult = '<a href="javascript:imgUpLoader[\''+id+'\'].showManage()" onfocus="blur()" title="添加/删除图片"><span></span>管理图片</a>';
			if(cfg.multiple)
				mult = '<a href="javascript:imgUpLoader[\''+id+'\'].showManage()" onfocus="blur()" title="添加/删除图片"><span></span>管理图片</a>';
			jQuery("#"+id).addClass("imgUpDiv");
			jQuery("#"+id).append('<iframe src="" id="imgUpFrame'+id+'" name="imgUpFrame'+id+'" class="hideFrame"></iframe');
			jQuery("#"+id).append('<div class="imgUpHint">'+
				'<div class="imgUpHintMask"></div>'+
				'<span class="imgUpHintText">...正在加载...</span>'+
			'</div>'+
			'<div class="imgUpSubmit">'+
				'<div class="imgUpSubmitMask"></div>'+
				'<span class="imgUpSubmitText">'+
				'</span>'+
			'</div>'+
			'<div class="imgUpSubmitPreview">'+
				'<div class="imgUpSubmitPreviewMask"></div>'+
				'<div class="imgUpSubmitPreviewText" >'+
					'<img />'+
				'</div>'+
			'</div>'+
			'<div class="imgUpImgBlock">'+
				'<div class="imgUpImgInfoMask"></div>'+
				'<div class="imgUpImgInfo">'+
					'<a href="javascript:imgUpLoader[\''+id+'\'].changeImg(-1)" onfocus="blur()" class="imgUpBack" title="上一张">&lt;&lt;</a>'+
					'<a href="javascript:imgUpLoader[\''+id+'\'].changeImg(1)" onfocus="blur()" class="imgUpNext" title="下一张">&gt;&gt;</a>'+
					'<span class="imgUpImgDetail"></span>'+
				'</div>'+
				'<div class="imgUpImgsMask"></div>'+
				'<div class="imgUpNoImg">(暂无图片)</div>'+
				'<div class="imgUpImgs">'+
				'</div>'+
			'</div>'+
			'<div class="imgUpButtonBlock">'+
				mult+
			'</div>'+
			'<div class="imgUpImgBlockMask"></div>'+
			'<div class="imgUpImgBlockMask2"></div>'+
			'<div class="imgUpImgManager">'+
				'<div class="imgUpAllImgs">'+
				'</div>'+
				'<div class="imgUpManagerButtons">'+
					'<a href="javascript:imgUpLoader[\''+opt.id+'\'].openImg()" onfocus="blur()">添加图片</a>'+
					'<a href="javascript:imgUpLoader[\''+opt.id+'\'].deleteImg()" onfocus="blur()" disabled="disabled">删除图片</a>'+
					'<a href="javascript:imgUpLoader[\''+opt.id+'\'].hideManage()" onfocus="blur()">返回</a>'+
				'</div>'+
			'</div>');
			jQuery("#"+id+" .imgUpImgBlock").hover(
				function()
				{
					var aim = this.parentNode.id;
					imgUpLoader[aim].hoverBImg();
				},
				function()
				{
					var aim = this.parentNode.id;
					imgUpLoader[aim].outBImg();
				}
			)
			jQuery("#"+id+" .imgUpButtonBlock a").hover(
				function()
				{
					jQuery(this).css("background-position","0 -39px");
					jQuery(this).css("color","#333");
				},
				function(){
					jQuery(this).css("background-position","0 0");	
					jQuery(this).css("color","#545454");
				}
			);
			jQuery("#"+id+" .imgUpManagerButtons a").hover(
				function()
				{
					jQuery(this).css("background-position","0 -29px");
					jQuery(this).css("border","1px solid #aaa");
				},
				function(){
					jQuery(this).css("background-position","0 0");	
					jQuery(this).css("border","1px solid #777");
				}
			);
			
		};
		var getImgUrlAjax = function()
		{
			if(!cfg.getUrl||cfg.getUrl=='')
			{
				showNoImg();
				//me.show("我无语啊居然没配getUrl");
				return;
			}
			jQuery("#"+id).css("cursor","wait");
			jQuery.ajax({
				type: "POST",
				url: cfg.getUrl,
				success: function(json){
							jQuery("#"+id).css("cursor","default");
							me.show(json);
						},
				error: function(){
							jQuery("#"+id).css("cursor","default");
							me.showHint("连接失败了");
							//setTimeout(me.hideHint,2000);
							jQuery("#"+id+" .imgUpImgBlock").unbind();
						}
			});
		};
		var pushImg = function(imgUrl,imgTitle,imgId)
		{
			if(imgUrl==''||!imgUrl)
				return;
			var obj = {
				    url: imgUrl,
				  title: imgTitle,
				     id: imgId,
				checked: false
			};
			imgs.push(obj);
			var bm = jQuery('<img src="'+obj.url+'" onclick="window.open(this.src)" oncontextmenu="bRightClick(\''+id+'\',this);return false;" onload="bMChange(this)" width="232"/>');
			jQuery("#"+id+" .imgUpImgs").append(bm);
			if(!this.zoom)
				var lm = jQuery('<span class="imgUpImgSmall" onclick="imgUpLoader[\''+id+'\'].checkImg(this)" onmouseover="imgUpLoader[\''+id+'\'].hoverLImg(this)" onmouseout="imgUpLoader[\''+id+'\'].outLImg(this)"><img src="'+obj.url+'" ondblclick="this.click();window.open(this.src)" oncontextmenu="sRightClick(\''+id+'\',this);return false;" onload="sMChange(this)" width="80"/><span class="imgUpImgSmallMask"></span></span>');
			else
				var lm = jQuery('<span class="imgUpImgBig" onclick="imgUpLoader[\''+id+'\'].checkImg(this)" onmouseover="imgUpLoader[\''+id+'\'].hoverLImg(this)" onmouseout="imgUpLoader[\''+id+'\'].outLImg(this)"><img src="'+obj.url+'" ondblclick="this.click();window.open(this.src)" oncontextmenu="sRightClick(\''+id+'\',this);return false;" onload="sMChange(this)" width="250"/><span class="imgUpImgBigMask"></span></span>');
			jQuery("#"+id+" .imgUpAllImgs").append(lm);
		};
		var changeDetail = function(s){
			jQuery("#"+id+" .imgUpImgDetail").empty();
			jQuery("#"+id+" .imgUpImgDetail").append(s);
		};
		var showImg = function()
		{
			me.hideHint();
			hideMask1();
			if(imgs.length==0)
				showNoImg();
			else
				showImgAndDetail();
		};
		this.showHint = function(s){
			jQuery("#"+id+" .imgUpHint .imgUpHintText").empty();
			jQuery("#"+id+" .imgUpHint").show();
			jQuery("#"+id+" .imgUpHint .imgUpHintText").append(s);
		};
		this.hideHint = function(){
			jQuery("#"+id+" .imgUpHint .imgUpHintText").empty();
			jQuery("#"+id+" .imgUpHint").hide();
		};
		this.showPreview = function(s){
			jQuery("#"+id+" .imgUpSubmitPreview .imgUpSubmitPreviewText").empty();
			jQuery("#"+id+" .imgUpSubmitPreview .imgUpSubmitPreviewText").append('<img src="'+s+'" />');
			jQuery("#"+id+" .imgUpSubmitPreview").slideDown('fast');
		};
		this.hidePreview = function(fn){
			if(!fn)
				fn = function(){return};
			jQuery("#"+id+" .imgUpSubmitPreview").slideUp('fast',fn);
		};
		this.showSubmit = function(){
			jQuery("#"+id+" .imgUpSubmit .imgUpSubmitText").append('<form  action="'+cfg.addUrl+'" target="imgUpFrame'+id+'" method="POST" name="imgUpForm'+id+'" id="imgUpForm'+id+'" enctype="multipart/form-data"><input type="file" id="imgUpFile'+id+'" name="upLoadFile" value=""/><input  type="hidden" name="fileNames" id="fileField" value="filename"/><input type="submit" disabled="disabled" id="imgUpSubmit'+id+'" value="提交"/></form>');
			jQuery("#"+id+" .imgUpSubmit").slideDown("fast");
			jQuery("#imgUpForm"+id)[0].onsubmit = function(){
				me.upSubmit();
			};
		};
		this.hideSubmit = function(){
			jQuery("#"+id+" .imgUpSubmit").slideUp("fast",function(){jQuery("#"+id+" .imgUpSubmit .imgUpSubmitText").empty();});
		};
		var hideMask1 = function(){
			jQuery("#"+id+" .imgUpImgBlockMask").hide();
		};
		var showNoImg = function(){
			hideMask1();
			me.hideHint();
			jQuery("#"+id+" .imgUpImgBlock").unbind();
			jQuery("#"+id+" .imgUpImgs").empty();
			jQuery("#"+id+" .imgUpImgs").hide();
			jQuery("#"+id+" .imgUpAllImgs").empty();
			jQuery("#"+id+" .imgUpNoImg").show();
		};
		var showImgAndDetail = function(){
			jQuery("#"+id+" .imgUpNoImg").hide();
			jQuery("#"+id+" .imgUpImgs").show();
			jQuery("#"+id+" .imgUpImgs img:eq(0)").fadeIn("slow");
			jQuery("#"+id+" .imgUpImgBlock").hover(
				function()
				{
					var aim = this.parentNode.id;
					imgUpLoader[aim].hoverBImg();
				},
				function()
				{
					var aim = this.parentNode.id;
					imgUpLoader[aim].outBImg();
				}
			)
		};
		
		imgUpLoaderInit();
		getImgUrlAjax();
	};
}
var aaaaaaa = true;
(function(jQuery){
jQuery.fn.imgUpLoader = function(opt){
	if(this.length <1)
		return;
	opt.id =jQuery(this[0]).attr('id');
	newImgUpLoader(opt);
	idArray.push(opt.id);
}
})(jQuery);
jQuery(document).ready(function(){
	jQuery(document.body).click(function(){
		jQuery(".rightMenu").hide();
		aaaaaaa = false;
	});
	jQuery(document.body).mousemove(function(){
		aaaaaaa = true;
	});
	document.body.onunload = function(){
		if(isDelete){
			jQuery.each(idArray,function(i,n){
				if(imgUpLoader[n].getUrls().length>0)
				{
					if(!imgUpLoader[n].checkall)
						imgUpLoader[n].checkAll();
					imgUpLoader[n].deleteImg();
				}
			});
		}
	};
	document.body.onbeforeunload = function(){
		if(aaaaaaa)
		{
			var bbb = false;
			jQuery.each(idArray,function(i,n){
				if(imgUpLoader[n].getUrls().length>0)
					bbb = true;
			});
			if(bbb)
				return "您上传了图片但没有保存商品,\n如果不保存商品,本次上传的图片将被删除.\n确定要这样做吗?";
		}
	}
});

function isSubmit()
{
   isDelete=false;
}


