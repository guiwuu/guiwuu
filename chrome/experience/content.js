function replaceCustomFollowLink(){
	var links = document.all.tags("a");
	for(var i in links){
		var href = links[i].href;
		if(href != undefined && href.match('order/creator_history_new.htm') != null){
			href = href.replace(/&.+$/, '').replace('order/creator_history_new.htm','experience/united.htm');
			links[i].href = href;
		}
	}
};

window.setInterval(replaceCustomFollowLink, 100);
