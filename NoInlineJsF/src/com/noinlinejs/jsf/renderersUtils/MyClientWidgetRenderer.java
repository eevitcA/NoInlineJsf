package com.noinlinejs.jsf.renderersUtils;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.util.CollectionsUtils;

//This is just an helper class to speed up js development by allowing hide, show, focus and toggle class with attributes
//example : <h:panelGroup data-c-widget="shower" data-show="cid #sibling,next"/> will show cid with resolved id as well as the next sibling element in the DOM
// The reason for sibling is that it's sometimes hard to identify an id in a tree structure like comments.
public class MyClientWidgetRenderer {
	private static final String[] EVENTS = { "blur", "change", "click", "dblclick", "focus", "keydown", "keypress",
			"keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup", "select" };

	private static final Map<String, CAttribute[]> WIDGET_ATTRS = CollectionsUtils.<String, CAttribute[]> map()
			.add("shower", new CAttribute[] { new CAttribute("data-show", true) })
			.add("hider", new CAttribute[] { new CAttribute("data-hide", true) })
			.add("focuser", new CAttribute[] { new CAttribute("data-focus", true) })
			.add("classToggler", new CAttribute[] { new CAttribute("data-tg-target", false),
					new CAttribute("data-toggleClass", false) });
	private static final String ERROR_VALID_ATTRS = ". Valid attributes are: shower, hider, focuser, classToggler.";

	public static String writeCWidget(ResponseWriter writer, UIComponent component, String styleClass) throws IOException {
		// No ajax
		if(null == styleClass){
			styleClass = "";
		}
		for (int i = 0; i < EVENTS.length; i++) {
			String onevent = (String) component.getAttributes().get("data-on" + EVENTS[i]);

			if (null == onevent) {
				continue;
			}
			// example: data-onclick="shower classToggler"
			String[] widArr = onevent.split(" ");
			for (String attr : widArr) {
				if (!"jsfajax".equals(attr)) {
					writeTargetAttr(attr, component, writer);
				}
			}
			writer.writeAttribute("data-on" + EVENTS[i], onevent, null);
			if ( ! "jsfajax".equals(onevent)) {
				styleClass += " data-on" + EVENTS[i];
			}
			
		}
		if(styleClass.equals(""))
			return null;
		else
			return styleClass;
	}

	private static void writeTargetAttr(String attr, UIComponent component, ResponseWriter writer) throws IOException{
		CAttribute attrVals[] = WIDGET_ATTRS.get(attr);
		if (null == attrVals) {
			throw new RuntimeException(
					attr + " is not a valid attribute for data-c-widget on component with id: "
							+ component.getClientId() + ERROR_VALID_ATTRS);
		}
		for (CAttribute attrC : attrVals) {
			String val = (String) component.getAttributes().get(attrC.attr);
			if (null == val) {
				throw new RuntimeException(
						attrC + " is missing for the component with id: " + component.getClientId());
			}
			if (attrC.shouldResolveId) {
				val = resolveIds(component, val);
			}
			writer.writeAttribute(attrC.attr, val, null);
		}
	}
	
	private static String resolveIds(UIComponent component, String ids) {
		String[] idarr = ids.split(" ");
		StringBuilder sb = null;

		for (int i = 0; i < idarr.length; i++) {
			idarr[i] = getResolvedId(component, idarr[i]);
			if (sb == null) {
				sb = new StringBuilder();
			} else {
				sb.append(" ");
			}
			sb.append(idarr[i]);
		}

		return sb.toString();
	}

	// Returns the resolved (client id) for a particular id.
	private static String getResolvedId(UIComponent component, String id) {
		// the # char is used in the js as an helper, for example for finding
		// siblings in a tree like structure.
		if (id.charAt(0) == '#') {
			return id;
		}
		UIComponent resolvedComponent = component.findComponent(id);
		if (resolvedComponent == null) {
			if (id.charAt(0) == UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance())) {
				return id.substring(1);
			}
			return id;
		}

		return resolvedComponent.getClientId();
	}

	private static class CAttribute {
		String attr;
		boolean shouldResolveId;

		public CAttribute(String attr, boolean shouldResolveId) {
			this.attr = attr;
			this.shouldResolveId = shouldResolveId;
		}
	}
}
