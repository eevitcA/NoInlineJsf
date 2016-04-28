# NoInlineJsf

This is a drop in plugin for the JSF framework that allows JSF to run without rendering inline scripts. It also include some helper tags to speed up javascript development.

*v0.0.2 This is a project under development, not all components are done and it wasn't tested much so use it wisely.*

##Index 

 - Installation
 - How to use it
 - How it works
 
##Installation

 - Include noinline.min.js (or noinline.js) in your template. The file is situated in 
   `testJSF/WebContent/resources/scripts/`
 - Include the jar in your WEB-INF/lib folder. The jar is situated in `NoInlineJsf/testJSF/WebContent/WEB-INF/lib/` on this git.
 - to your faces-config.xml add the uncommented lines from the file `NoInlineJsf/testJSF/WebContent/WEB-INF/faces-config.xml`

##How to use it


You can just leave your JSF application as is and on the javascript side you have to make sure that your functions name don't collide with the ones in noinlinejs.min.js as those are defined globally. You can find the non-compressed file for easier readability at :  NoInlineJsf/NoInlineJsF/WebContent/resources/scripts/noinlinejs.js

There is however additional features that can help speed up the development process. Those features are explained here.

###data-on*eventname*
There is an additional attribute that is allowed that helps you directly specify basic client side behaviors in the tags. That attribute is `data-on*eventname*`. It helps defining javascript actions (such as hide element, show element, and toggle a class for an element).

To use it include it in your tag with values separated by a space.
For example : 

```
<h:panelGroup data-onmouseover="shower hider" data-show="c0" data-hide="c1">
	Click here
</h:panelGroup>
<h:panelGroup id="c0" style="display:none;">
	c0
</h:panelGroup>
<h:panelGroup id="c1">
	c1
</h:panelGroup>
```
			
On click c0 is shown and c1 disappears.

When using `data-*eventname*` you can use your own function (just write the name - with no param-) or use the predefined ones. If the later you have to specify the correct target attribute as well. 
 - shower with data-show (target elem)
 - hider with data-hide (target elem)
 - classToggler with data-tg-target (target elem) and data-toggleClass (classname)
 - focuser with data-focus (target elem)

###specify an element
For data-c-widget as well as for the **render** attribute you can use 2 method :
 - The classic JSF method where JSF container automatically finds the correct element
 - The # key character introduced here that can help you find an element.

Example : The # key
```
<h:panelGroup layout="aside" data-onclick="shower" data-show="#sibling,next" >
	Click here
</h:panelGroup>
<h:panelGroup id="c0" style="display:none;">
	c0
</h:panelGroup>
```
Here the next sibling element will be hiden. This is particulary useful for the render attribute when the rendered component id is hard to find, in a tree structure, datatable and so on.

just use the key #sibling, before the target then next, previous or parent.
Example: render="c1 c2 #sibling,parent-next-next-next"

###layout in panelGroup
You can now specify other layouts for the panel group than just block. If the layout is invalid it will result in a span (or no tag).
The allowed attributes are : "abbr", "address", "area", "article", "aside", "audio", "b",
			"blockquote", "caption", "cite", "code", "del", "details", "dialog", "div", "em", "fieldset", "figure",
			"header", "hr", "i", "ins", "legend", "li", "main","menu","menuitem","nav","output","pre","section","span","sup","sub","time","u","ul";
			
			

 
