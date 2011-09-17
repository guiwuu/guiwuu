
var currslid = 0;
var slidint;
function setfoc(id){
	stopit();
	jQuery(".foclnk").hide();
	jQuery("#focpic"+id).show();
	currslid = id;
	for(i=0;i<6;i++){
		document.getElementById("tmb"+i).className = "thubpic";
	};
	document.getElementById("tmb"+id).className ="thubpiccur";
}

function playnext(){
	if(currslid==5){
		currslid = 0;
	}
	else{
		currslid++;
	};
	setfoc(currslid);
	playit();
}
function playit(){
	slidint = setTimeout(playnext,4500);
}
function stopit(){
	clearTimeout(slidint);
}
jQuery(document).ready(function(){
	playit();
});