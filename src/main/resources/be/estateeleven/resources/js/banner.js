function responsiveBanner(id, aspectW, aspectH) { if (document.getElementById(id)) { var banner = document.getElementById(id); var ratio = aspectW / aspectH; var nh = banner.offsetWidth / ratio; banner.style.minHeight = nh + 'px'; var container = document.getElementById(id); var ontopcontainers = container.querySelectorAll(".caption"); if (ontopcontainers.length > 0) { for (var i = 0; i < ontopcontainers.length; i++) { var ontopcontainer = ontopcontainers[i]; ontopcontainer.style.height = nh + 'px'; } } } }

function loadbannersize() {
	responsiveBanner('homebanner', 1600, 730);
	responsiveBanner('subbanner', 1600, 360);	
	responsiveBanner('aanbodBanner', 1600, 360);	
	responsiveBanner('aanbodDetailBanner', 1200, 360);	
}

var addEvent = function(elem, type, eventHandle) { if (elem == null || typeof(elem) == 'undefined') return; if ( elem.addEventListener ) { elem.addEventListener( type, eventHandle, false ); } else if ( elem.attachEvent ) { elem.attachEvent( "on" + type, eventHandle ); } else { elem["on"+type]=eventHandle; } };

loadbannersize();
addEvent(window, "resize", loadbannersize);