var picChange = new Object();
picChange.cIndex = new Object();
picChange.srcs = new Object();
picChange.urls = new Object();
picChange.aChange = new Object();

function newPicChange(opt,url,id,time,details)
{
	if(typeof time != 'number')
		time = 3000;
	var pics = new Array()
	if(typeof(opt)!='object'||opt.length<1)
		return;
	jQuery("#"+id).empty().addClass("pic_change");
	var spanstr = '';
	var detailstr = '';
	jQuery.each(opt,function(i,n){
		if(typeof(n)!='string')
			n += '';
		if(typeof(url[i])!='string')
			url[i] += '';
		var pic = new Image();
		pic.src = n;
		pics.push(pic);
		if(i==0)
			spanstr += '<span class="on" aim="'+n+'" jump="'+url[0]+'" index="0">1</span>';
		else
			spanstr += '<span aim="'+n+'" jump="'+url[i]+'" index="'+i+'">'+(i+1)+'</span>';
		if(details)
		{
			if(!details[i])
				return;
			if(i==0)
				detailstr += '<div class="db on" index="0">'+details[0]+'</div>';
			else
				detailstr += '<div class="db" index="'+i+'">'+details[i]+'</div>';
		}
	});
	picChange.cIndex[id] = 0;
	picChange.srcs[id] = opt;
	picChange.urls[id] = url;
	jQuery("#"+id).append('<img src="'+opt[0]+'" />'+
		'<div class="nums">'+
			spanstr+
		'</div>'+
		'<div class="detail">'+
			detailstr+
		'</div>');
	jQuery("#"+id).mouseover(function(){
		clearInterval(picChange.aChange[id]);
	});
	if(url.length>0&&time>0)
		jQuery("#"+id).mouseout(function(){
			picChange.aChange[id] = setInterval(function(){autoChange(id)},time);
		});
	jQuery("#"+id+" .nums span").mouseover(function(){
		if(jQuery("#"+id+" img").attr('src') == this.jump&&jQuery(this).attr('class') == 'on')
			return;
		imgChange(this.index,id);
	});
	jQuery("#"+id+" .nums span").click(function(){
		window.open(this.jump);
	});
	if(url.length>0&&time>0)
		picChange.aChange[id] = setInterval(function(){autoChange(id)},time);
}

function imgChange(index,id)
{
	index = parseInt(index);
	if(index>picChange.srcs[id].length-1)
		index = 0;
	jQuery("#"+id+" img").fadeOut('fast',function(){jQuery("#"+id+" img").attr('src',picChange.srcs[id][index]).unbind('click').click(function(){window.open(picChange.urls[id][index])}).fadeIn('fast')});
	jQuery("#"+id+" .nums span").removeClass();
	jQuery("#"+id+" .nums span:eq("+index+")").addClass("on");
	jQuery("#"+id+" .detail div.db").removeClass('on');
	jQuery("#"+id+" .detail div.db:eq("+index+")").addClass("on");
	picChange.cIndex[id] = index;
}

function autoChange(id)
{
	imgChange(picChange.cIndex[id]+1,id);
}

(function(jQuery){
jQuery.fn.picChange = function(opt,url,time,details){
	if(this.length < 1)
		return;
	newPicChange(opt,url,jQuery(this[0]).attr('id'),time,details);
}
})(jQuery);