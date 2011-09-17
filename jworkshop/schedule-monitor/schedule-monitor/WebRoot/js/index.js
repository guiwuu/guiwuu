// JavaScript Document
(function(jQuery){
	jQuery.fn.winPop =function(cfg){
		var d = {
			url:"",
			width:'',
			height:''
		};
		jQuery.extend(d,cfg);
		jQuery(document.body).append('<div class="doc_mask"></div>');
		if(d.width!=''||d.height!='')
		{
			jQuery(document.body).append('<iframe id="win" class="win" style="width:'+d.width+'px;height:'+d.height+'px;"><div>页面加载中</div></iframe>');
			jQuery("#win").css("top",((jQuery(document.body)[0].offsetHeight-d.height)/2)+"px");
			jQuery("#win").css("left",((jQuery(document.body)[0].offsetWidth-d.width)/2)+"px");
		}
		else
		{
			jQuery(document.body).append('<iframe id="win" class="win"><div>页面加载中</div></iframe>');
			jQuery("#win").css("height",(jQuery(document.body)[0].offsetHeight*0.95)+"px");
			jQuery("#win").css("width",(jQuery(document.body)[0].offsetWidth*0.95)+"px");
			jQuery("#win").css("top",(jQuery(document.body)[0].offsetHeight*0.025)+"px");
			jQuery("#win").css("left",(jQuery(document.body)[0].offsetWidth*0.025)+"px");
		}
		jQuery.ajax({
			type:"POST",
			url:cfg.url,
			success:function(j){
				jQuery("#win").empty();
				jQuery("#win").append(j);
			}
		});
	}
})(jQuery)
window.onresize = function(){
	jQuery("#leftFrame").height(jQuery(document.body).height() - jQuery("#topFrame").height());
	jQuery("#mainFrame").height(jQuery(document.body).height() - jQuery("#topFrame").height());
}
jQuery(document).ready(function(){
//	jQuery().winPop({
//		url:'adminAddGoods.html'
//	});
})