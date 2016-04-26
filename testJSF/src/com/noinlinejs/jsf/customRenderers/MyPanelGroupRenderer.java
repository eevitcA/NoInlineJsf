package com.noinlinejs.jsf.customRenderers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext.Parameter;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.noinlinejs.jsf.renderersUtils.MyClientWidgetRenderer;
import com.noinlinejs.jsf.renderersUtils.MyHtmlValidTags;
import com.noinlinejs.jsf.renderersUtils.MyRenderKitUtils;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.GroupRenderer;

public class MyPanelGroupRenderer extends GroupRenderer {

	private static final Attribute[] ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.PANELGROUP);
	private String layout;

	public MyPanelGroupRenderer() {
	}

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		rendererParamsNotNull(context, component);

		if (!shouldEncode(component)) {
			return;
		}
		
		ResponseWriter writer = context.getResponseWriter();

		if (divOrSpan(component) || MyClientWidgetRenderer.writeCWidget(writer, component)) {
			// to allow other tags than just div and span (ex: aside, article, etc.).
			layout = writeTag(component);
			writer.startElement(layout, component);
			writeIdAttributeIfNecessary(context, writer, component);
		}
		
		MyRenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
		// styleClass attr has to be written after. For events that aren't click className is used to add event listeners on those elements on js side. 
		// delegating only click events to the body of document and adding listeners for other events which are used less frequently sounds like a better choice.
		String styleClass = (String) component.getAttributes().get("styleClass");
		if (styleClass != null) {
			writer.writeAttribute("class", styleClass, "styleClass");
		}
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		rendererParamsNotNull(context, component);

		if (!shouldEncode(component)) {
			return;
		}
		ResponseWriter writer = context.getResponseWriter();
		if (divOrSpan(component)) {
			writer.endElement(layout);
		}
	}

	private String writeTag(UIComponent component) throws IOException {
		String layout = (String) component.getAttributes().get("layout");
		if(null == layout){
			return "span";
		}else{
			if( "block".equals(layout) ){
				return "div";
			}else{
				// Dunno if this should be done. Seems a bit safer.
				if(Arrays.binarySearch(MyHtmlValidTags.TAGS_VALID, layout ) >= 0){
					return layout;
				}else{
					//Maybe throwing an error here would be better for compatibility
					return "span";
				}
			}
		}
	}

	private boolean divOrSpan(UIComponent component) {

		return (shouldWriteIdAttribute(component) || (component.getAttributes().get("style") != null)
				|| (component.getAttributes().get("styleClass") != null));

	}
}
