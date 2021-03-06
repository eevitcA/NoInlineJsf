"use strict";
var doc = document;

function setClickEvents(){
		doc.body.addEventListener("click", function(e) {
			execEvent(e, "click");
		});
	}

	// jsf events other than than click .  evt listener is added when doc ready.
function getJsfMobileEvts(){
		var events = ["blur","change","dblclick","focus","keydown","keypress","keyup","mousedown","mousemove","mouseout","mouseover","mouseup","select"];
		var evLength = events.length;
		for(var i = 0; i < evLength; i++){
			var elems = doc.getElementsByClassName('data-on' + events[i]);
			if(elems.length > 0){
				addJsfEvtsToMobileEvts(elems, events[i]);
			}
		}
	}

function addJsfEvtsToMobileEvts (elems, evt){
		var eLength = elems.length;
		for(var i = 0; i < eLength; i++){
			(function(i){
				elems[i].addEventListener(evt, function(e){
					e.preventDefault();
					execEvent(e, evt);
				});
			})(i);
		}
	}
function execEvent(event, eventName){
	var w = event.target.getAttribute("data-on" + eventName);
	if(w){
		var s = w.split(" ");
		var slength = s.length;
		for(var i = 0; i < slength; i++){
			window[s[i]](event.target, event);
		}
	}
}
	
function jsfajax (elem, event){
		event.preventDefault();
		var render = elem.getAttribute('data-render');
		var execute =  elem.getAttribute('data-execute');
		var op = {};
		var onevent = elem.getAttribute('data-onevent');
		var onerror = elem.getAttribute('data-onerror');
		var delay = elem.getAttribute('data-delay');
		var jsfEvent = elem.getAttribute('data-jsf-event');

		if(elem.id == ""){
			elem.id = elem.name;
		}

		if(!jsfEvent){
			jsfEvent = "click";
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
	}
function mojAb (s, e, n, ex, re, op) {
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
	}

function findTargetsId (e, trs){
		var trs = trs.split(" ");
		var tlength = trs.length;
		for(var i = 0; i < tlength; i++){
			var tar = findTarget(e, trs[i]);
			trs[i] = tar.id;
		}
		return trs.join(" ");
	}
	
function findTargets (e, trs){
		var trs = trs.split(" ");
		var tlength = trs.length;
		for(var i = 0; i < tlength; i++){
			trs[i] = findTarget(e, trs[i]);
		}
		return trs;
	}
	
function findTarget (e, tr){
		if(tr.charAt(0) === '#'){
			var target = tr.split(',');
			switch(target[0]){
				case '#this': return e;
				case '#sibling': return findSibling(e, target[1]);
				case '#id' : return doc.getElementById(target[1]); 
			}
		}else{
			return doc.getElementById(tr);
		}
	}
	
function findSibling (e,sibling){
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
	}
	
	//widgets :
	
	//hides a target elem given by data-hide attribute
function hider(elem){
					var targets = elem.getAttribute('data-hide');
					targets = findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						hideElem(targets[i]);	
					}
				 }
	// makes target div visible , target given by data-show attr
function shower(elem){
					var targets = elem.getAttribute('data-show');
					targets = findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						showElem(targets[i]);	
					}
				  }

	// focus on target field , target given by data-focus attr
function focuser(elem){
					var targets = elem.getAttribute('data-focus');
					targets = findTargets(elem, targets);
					var tlength = targets.length;
					for(var i = 0; i < tlength; i++){
						target.focus();	
					}
				  }

	// toggles class on target elem , target given by data-tg-target attr
function classToggler(elem){
							var className = elem.getAttribute('data-toggleClass');
							var targets = elem.getAttribute('data-tg-target');
							targets = findTargets(elem, targets);
							var tlength = targets.length;
							for(var i = 0; i < tlength; i++){
								toggleClass(targets[i], className);	
							}
						}

function show(id){
		doc.getElementById(id).style.display = "block";
	}

function showElem(e){
		e.style.display = "block";
	}
function hide(id){
		doc.getElementById(id).style.display = "none";
	}

function hideElem(e){
		e.style.display = "none";
	}

function hasClass(element, cls) {
	    return (' ' + element.className + ' ').indexOf(' ' + cls + ' ');
	}

function toggleClass(e, cls){
	    var classString = e.className;
	    var nameIndex = classString.indexOf(cls);
	    if (nameIndex == -1) {
	        classString += ' ' + className;
	    }
	    else {
	        classString = classString.substr(0, nameIndex) + classString.substr(nameIndex + className.length);
	    }
	    e.className = classString;
	}
function focusElem(id){
		doc.getElementById(id).focus();
	}


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
})("docReady", window);

docReady(function(){
	console.log('hi');
	setClickEvents();
	getJsfMobileEvts();
});



