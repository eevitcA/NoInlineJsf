"use strict";
var doc = document;

var noinlinejs = {
	setClickEvents : function (){
		doc.body.addEventListener("click", function(e) {
			var w;
			//sets jsf ajax click events
			if(e.target.hasAttribute("data-jsfajax")){
				e.preventDefault();
				this.jsfajax(e.target, e);
			}
			//sets client-widget click events
			 w = e.target.getAttribute("data-c-widget");
			if(w){
				var s = w.split(" ");
				var slength = s.length;
				for(var i = 0; i < slength; i++){
					this[s[i]](e.target, e);
				}
			}
		}.bind(this));
	},
	// jsf events other than than click .  evt listener is added when doc ready.
	getJsfMobileEvts : function (){
		var events = ["blur","change","dblclick","focus","keydown","keypress","keyup","mousedown","mousemove","mouseout","mouseover","mouseup","select"];
		var evLength = events.length;
		for(var i = 0; i < evLength; i++){
			var elems = doc.getElementsByClassName('jsf-e-' + events[i]);
			if(elems.length > 0){
				this.addJsfEvtsToMobileEvts(elems, events[i]);
			}
		}
	},
	addJsfEvtsToMobileEvts : function (elems, evt){
		var eLength = elems.length;
		for(var i = 0; i < eLength; i++){
			(function(i){
				elems[i].addEventListener(evt, function(e){
					e.preventDefault();
					noinlinejs.jsfajax(elems[i], e, evt);
				});
			})(i);
		}
	},
	
	jsfajax : function (elem, event, jsfEvent){
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
		this.mojAb(elem, event, jsfEvent, execute, render, op);
	},
	mojAb : function(s, e, n, ex, re, op) {
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
	},
	findTargetsId : function(e, trs){
		var trs = trs.split(" ");
		var tlength = trs.length;
		for(var i = 0; i < tlength; i++){
			var tar = this.findTarget(e, trs[i]);
			trs[i] = tar.id;
		}
		return trs.join(" ");
	},
	
	findTargets : function (e, trs){
		var trs = trs.split(" ");
		var tlength = trs.length;
		for(var i = 0; i < tlength; i++){
			trs[i] = this.findTarget(e, trs[i]);
		}
		return trs;
	},
	
	findTarget : function (e, tr){
		if(tr.charAt(0) === '#'){
			var target = tr.split(',');
			switch(target[0]){
				case '#this': return e;
				case '#sibling': return this.findSibling(e, target[1]);
				case '#id' : return doc.getElementById(target[1]); 
			}
		}else{
			return doc.getElementById(tr);
		}
	},
	
	findSibling : function(e,sibling){
		var arr = sibling.split('-');
		var t = e;
		while(arr.length > 0){
			switch(arr[0]){
				case 'parent': t = t.parentNode;
					break;
				case 'previous': t = t.previousElementSibling;
					break;
				case 'next': t = t.nextElementSibling;
					break;
				}
			arr.splice(0, 1);
		}
		return t;
	},
	
	//widgets :
	
	//hides a target elem given by data-hide attribute
	hider : function hider(elem){
					var targets = elem.getAttribute('data-hide');
					targets = this.findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						this.hideElem(targets[i]);	
					}
				 },
	// makes target div visible , target given by data-show attr
	shower : function shower(elem){
					var targets = elem.getAttribute('data-show');
					targets = this.findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						this.showElem(targets[i]);	
					}
				  },
	// focus on target field , target given by data-focus attr
	focuser : function focuser(elem){
					var targets = elem.getAttribute('data-focus');
					targets = this.findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						this.target.focus();	
					}
				  },
	// toggles class on target elem , target given by data-tg-target attr
	classToggler : function classToggler(elem){
							var className = elem.getAttribute('data-toggleClass');
							var targets = elem.getAttribute('data-tg-target');
							targets = this.findTargets(elem, targets);
							var tlength = targets.length;
							for(var i = 0; i < tlength; i++){
								this.toggleClass(targets[i], className);	
							}
						},
	show : function(id){
		doc.getElementById(id).style.display = "block";
	},
	showElem : function(e){
		e.style.display = "block";
	},
	hide : function hide(id){
		doc.getElementById(id).style.display = "none";
	},
	hideElem : function(e){
		e.style.display = "none";
	},
	hasClass : function(element, cls) {
	    return (' ' + element.className + ' ').indexOf(' ' + cls + ' ');
	},
	toggleClass : function(e, cls){
	    var classString = e.className;
	    var nameIndex = classString.indexOf(cls);
	    if (nameIndex == -1) {
	        classString += ' ' + className;
	    }
	    else {
	        classString = classString.substr(0, nameIndex) + classString.substr(nameIndex + className.length);
	    }
	    e.className = classString;
	},
	focusElem : function(id){
		doc.getElementById(id).focus();
	}
};

// check if doc is ready (https://github.com/jfriend00/docReady/blob/master/docready.js)
(function(funcName, baseObj) {
    // The public function name defaults to window.docReady
    // but you can modify the last line of this function to pass in a different object or method name
    // if you want to put them in a different namespace and those will be used instead of 
    // window.docReady(...)
    funcName = funcName || "docReady";
    baseObj = baseObj || window;
    var readyList = [];
    var readyFired = false;
    var readyEventHandlersInstalled = false;
    
    // call this when the doc is ready
    // this function protects itself against being called more than once
    function ready() {
        if (!readyFired) {
            // this must be set to true before we start calling callbacks
            readyFired = true;
            for (var i = 0; i < readyList.length; i++) {
                // if a callback here happens to add new ready handlers,
                // the docReady() function will see that it already fired
                // and will schedule the callback to run right after
                // this event loop finishes so all handlers will still execute
                // in order and no new ones will be added to the readyList
                // while we are processing the list
                readyList[i].fn.call(window, readyList[i].ctx);
            }
            // allow any closures held by these functions to free
            readyList = [];
        }
    }
    
    function readyStateChange() {
        if ( doc.readyState === "complete" ) {
            ready();
        }
    }
    
    // This is the one public interface
    // docReady(fn, context);
    // the context argument is optional - if present, it will be passed
    // as an argument to the callback
    baseObj[funcName] = function(callback, context) {
        // if ready has already fired, then just schedule the callback
        // to fire asynchronously, but right away
        if (readyFired) {
            setTimeout(function() {callback(context);}, 1);
            return;
        } else {
            // add the function and context to the list
            readyList.push({fn: callback, ctx: context});
        }
        // if doc already ready to go, schedule the ready function to run
        // IE only safe when readyState is "complete", others safe when readyState is "interactive"
        if (doc.readyState === "complete" || (!doc.attachEvent && doc.readyState === "interactive")) {
            setTimeout(ready, 1);
        } else if (!readyEventHandlersInstalled) {
            // otherwise if we don't have event handlers installed, install them
            if (doc.addEventListener) {
                // first choice is DOMContentLoaded event
                doc.addEventListener("DOMContentLoaded", ready, false);
                // backup is window load event
                window.addEventListener("load", ready, false);
            } else {
                // must be IE
                doc.attachEvent("onreadystatechange", readyStateChange);
                window.attachEvent("onload", ready);
            }
            readyEventHandlersInstalled = true;
        }
    }
})("docReady", noinlinejs);

noinlinejs.docReady(function(){
	(function(){
		noinlinejs.setClickEvents();
		noinlinejs.getJsfMobileEvts();
	})();
});



