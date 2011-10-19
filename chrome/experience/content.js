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

function addMineExperienceLink(){
	var mineExperienceLink = location.href.replace(/\.com\/.*/,'.com') + "/lottery/experience/mine.htm";
	var mineExperienceAnchor = "<a target='_blank' href='" + mineExperienceLink + "'>\u6211\u7684\u6218\u7EE9</a>";

	var quickLink = document.all.item("quick-link");
	if (quickLink != undefined) {
		var rel = quickLink.rel;
		rel = rel.replace('</dl>', '<dd>') + mineExperienceAnchor + '</dd></dl>';
		quickLink.rel = rel;
	}

	var myLotteryMenu = document.all.item("mylottery-menu");
	console.debug(myLotteryMenu);
	if (myLotteryMenu != undefined) {
		myLotteryMenu.innerHTML = myLotteryMenu.innerHTML + "<li>" + mineExperienceAnchor + "</li>";
	}
}

addMineExperienceLink();
window.setInterval(replaceCustomFollowLink, 100);
