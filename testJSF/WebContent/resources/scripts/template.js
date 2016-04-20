"use strict";
docReady(function(){
	(function(widgets){
		document.body.addEventListener("click", function(e) {
			console.log(e.target);
			var w = e.target.getAttribute("data-widget");
			if(w){
				e.preventDefault();
				var s = w.split(",");
				var slength = s.length;
				for(var i = 0; i <slength; i++){
					widgets[s[i]](e.target);
				}
		
			}
		});
	})(new Widgets);	
});


// widgets object 
function Widgets(){
	// sends ajax req.
	this.jsfajax = function jsfajax(elem){
						if(elem.id == ""){
							elem.id = elem.name;
						}
						var jsfEvent = elem.getAttribute('data-jsf-event');
						var render = elem.getAttribute('data-render');
						var execute =  elem.getAttribute('data-execute');
						var onevent = elem.getAttribute('data-onevent');
						if(render === null){
							render = 0;
						}else{
							var tar = findTarget(elem, render);
							render = tar.id;
						}
						if(execute === null){
							execute = '@form';
						}
						if(onevent === null){
							mojAb(elem, "click", jsfEvent, execute, render);
						}else{
							mojAb(elem, "click", jsfEvent, execute, render, {'onevent': Miscellaneous[onevent]});
						}
						
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
					var target = elem.getAttribute('data-hide');
					target = findTarget(elem, target);
					hideElem(target);
				 };
	// makes target div visible , target given by data-show attr
	this.shower = function shower(elem){
					var target = elem.getAttribute('data-show');
					target = findTarget(elem, target);
					showElem(target);
				  }
	// focus on target field , target given by data-focus attr
	this.focuser = function focuser(elem){
					var target = elem.getAttribute('data-focus');
					target = findTarget(elem, target);
					target.focus();
				  };
	// toggles class on target elem , target given by data-tg-target attr
	this.classToggler = function classToggler(elem){
							var className = elem.getAttribute('data-toggleClass');
							var target = elem.getAttribute('data-tg-target');
							target = findTarget(elem, target);
							toggleClass(target, className);
						};
	this.editorTools = function EditorTools( elem ) { Editor(elem); }; // uppercase bcz I kinda see it as a static class even though it's not, in fct bcuz defined in other file
	
	this.miscellaneous = function Misc( elem ) { 
							var misc = elem.getAttribute("data-misc");
							Miscellaneous[misc](elem); 
						};
}

function Miscellaneous(){}