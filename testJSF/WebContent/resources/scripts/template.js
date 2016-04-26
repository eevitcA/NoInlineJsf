"use strict";
var noinlinejs = {};
docReady(function(){
	(function(widgets){
		noinlinejs.setClickEvents(widgets);
		noinlinejs.getJsfMobileEvts(widgets);
	})(new Widgets);
});

noinlinejs.setClickEvents = function setClickEvents(widgets){
	document.body.addEventListener("click", function(e) {
		var w;
		if(e.target.hasAttribute("data-jsfajax")){
			e.preventDefault();
			widgets["jsfajax"](e.target, e);
		}
		 w = e.target.getAttribute("data-c-widget");
		if(w){
			var s = w.split(",");
			var slength = s.length;
			for(var i = 0; i < slength; i++){
				widgets[s[i]](e.target, e);
			}
		}
	});
}
noinlinejs.getJsfMobileEvts = function getJsfMobileEvts(widgets){
	var events = ["blur","change","dblclick","focus","keydown","keypress","keyup","mousedown","mousemove","mouseout","mouseover","mouseup","select"];
	var evLength = events.length;
	for(var i = 0; i < evLength; i++){
		var elems = doc.getElementsByClassName('jsf-e-' + events[i]);
		if(elems.length > 0){
			addJsfEvtsToMobileEvts(elems, events[i], widgets);
		}
	}
}

noinlinejs.addJsfEvtsToMobileEvts = function addJsfEvtsToMobileEvts(elems, evt, widgets){
	log(evt);
	var eLength = elems.length;
	for(var i = 0; i < eLength; i++){
		(function(i){
			elems[i].addEventListener(evt, function(e){
				widgets["jsfajax"](elems[i], e, evt);
			});
		})(i);
	}
}


// widgets object 
function Widgets(){
	// sends ajax req.
	this.jsfajax = function jsfajax(elem, event, jsfEvent){
						var render = elem.getAttribute('data-render');
						var execute =  elem.getAttribute('data-execute');
						var op = {};
						var onevent = elem.getAttribute('data-onevent');
						var onerror = elem.getAttribute('data-onerror');
						var delay = elem.getAttribute('data-delay');

						if(elem.id == ""){
							elem.id = elem.name;
						}
						
						if(!jsfEvent){
							jsfEvent = elem.getAttribute('data-jsf-event');
							if(!jsfEvent){
								jsfEvent = "click";
							}
						}
						if(render === null){
							render = 0;
						}else{
							render = findTargetsId(elem, render);
						}
						if(execute === null){
							execute = '@form';
						}
						if(onevent !== null){
							op['onevent'] = window[onevent];
						}
						if(onerror !== null){
							op['onerror'] = window[onerror];
						}
						if(delay !== null){
							op['delay'] = delay;
						}
						mojAb(elem, event, jsfEvent, execute, render, op);
					};
	function mojAb(s, e, n, ex, re, op) {
					    if (!op) {
					        op = {};
					    }

					    if (n) {
					        op["javax.faces.behavior.event"] = n;
					    }

					    if (ex) {
					        op["execute"] = ex;
					    }

					    if (re) {
					        op["render"] = re;
					    }
					    jsf.ajax.request(s, e, op);
					};
	//hides a target elem given by data-hide attribute
	this.hider = function hider(elem){
					var targets = elem.getAttribute('data-hide');
					targets = findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						hideElem(targets[i]);	
					}
				 };
	// makes target div visible , target given by data-show attr
	this.shower = function shower(elem){
					var targets = elem.getAttribute('data-show');
					targets = findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						showElem(targets[i]);	
					}
				  }
	// focus on target field , target given by data-focus attr
	this.focuser = function focuser(elem){
					var targets = elem.getAttribute('data-focus');
					targets = findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						target.focus();	
					}
				  };
	// toggles class on target elem , target given by data-tg-target attr
	this.classToggler = function classToggler(elem){
							var className = elem.getAttribute('data-toggleClass');
							var targets = elem.getAttribute('data-tg-target');
							targets = findTargets(elem, targets);
							var tlength = targets.length;
							for(var i = 0; i < tlength; i++){
								toggleClass(targets[i], className);	
							}
						};
	
	this.miscellaneous = function Misc( elem ) { 
							var misc = elem.getAttribute("data-misc");
							Miscellaneous[misc](elem); 
						};
}

function Miscellaneous(){}