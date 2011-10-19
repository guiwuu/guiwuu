function replaceWeiboLink(){
	var links = document.all.tags("a");
	for(var i in links){
		var href = links[i].href;
		if(href != undefined && href.match('weibo.com/[^.]*$') != null){
			href = href.replace('weibo.com','weibo.cn');
			links[i].href = href;
		}
	}
};

window.setInterval(replaceWeiboLink, 100);
