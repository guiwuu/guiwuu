var links = document.all.tags("a");
for(var i in links){
	if(links[i].href != undefined){
		links[i].href=links[i].href.replace('order/creator_history_new.htm','experience/wall.htm');
	}
}
