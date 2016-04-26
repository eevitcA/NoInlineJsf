var doc = document;
var growl = doc.getElementById('growl');

(function(funcName, baseObj) {
    "use strict";
    // The public function name defaults to window.docReady
    // but you can modify the last line of this function to pass in a different object or method name
    // if you want to put them in a different namespace and those will be used instead of 
    // window.docReady(...)
    funcName = funcName || "docReady";
    baseObj = baseObj || window;
    let readyList = [];
    let readyFired = false;
    let readyEventHandlersInstalled = false;
    
    // call this when the document is ready
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
        if ( document.readyState === "complete" ) {
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
        // if document already ready to go, schedule the ready function to run
        // IE only safe when readyState is "complete", others safe when readyState is "interactive"
        if (document.readyState === "complete" || (!document.attachEvent && document.readyState === "interactive")) {
            setTimeout(ready, 1);
        } else if (!readyEventHandlersInstalled) {
            // otherwise if we don't have event handlers installed, install them
            if (document.addEventListener) {
                // first choice is DOMContentLoaded event
                document.addEventListener("DOMContentLoaded", ready, false);
                // backup is window load event
                window.addEventListener("load", ready, false);
            } else {
                // must be IE
                document.attachEvent("onreadystatechange", readyStateChange);
                window.attachEvent("onload", ready);
            }
            readyEventHandlersInstalled = true;
        }
    }
})("docReady", window);

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
function findTargetsId(e, trs){
	var trs = trs.split(" ");
	var tlength = trs.length;
	for(var i = 0; i < tlength; i++){
		var tar = findTarget(e, trs[i]);
		trs[i] = tar.id;
	}
	return trs.join(" ");
}
function findTargets(e, trs){
	var trs = trs.split(" ");
	var tlength = trs.length;
	for(var i = 0; i < tlength; i++){
		trs[i] = findTarget(e, trs[i]);
	}
	return trs;
}

function findTarget(e, tr){
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

function findSibling(e,sibling){
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

function focusElem(id){
	doc.getElementById(id).focus();
}

function showUserDlg(data) {
    var status = data.status;
	var dlgContent = doc.getElementById('uDlgContent');
    switch (status) {
        case "begin": 	show('uDlg');
        				dlgContent.innerHTML = '<img src="/csbasement/resources/images/icons/ajaxloadingbar.gif"/>';
           				break;
        case "success": show('uDlg');
           				break;
    }
}

function growly(data){
    var status = data.status;
    if(status == "success") {
    	growl = doc.getElementById('growl');
    	growl.className += " growlVisible";
    	setTimeout(ungrow, 2000);
    }
}
function ungrow(){
	growl.className = growl.className.replace(' growlVisible','');
}

function log(x){
	console.log(x);
}