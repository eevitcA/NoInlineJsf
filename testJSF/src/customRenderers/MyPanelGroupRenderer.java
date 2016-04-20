package customRenderers;

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

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.GroupRenderer;

public class MyPanelGroupRenderer extends GroupRenderer {

	private static final Attribute[] ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.PANELGROUP);

	public MyPanelGroupRenderer() {
	}

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		rendererParamsNotNull(context, component);

		if (!shouldEncode(component)) {
			return;
		}
		String styleClass = (String) component.getAttributes().get("styleClass");
		ResponseWriter writer = context.getResponseWriter();
//		component.getAttributes().keySet().forEach(k -> System.out.println(k));
//		HtmlPanelGroup panel = (HtmlPanelGroup) component;
//		System.out.println(panel.getOnclick());
//		for (String key : panel.getClientBehaviors().keySet()) {
//			System.out.println(key + ": " + panel.getClientBehaviors().get(key));
//			if (key.equals("click")) {
//				writer.writeAttribute("data-widget", "jsfajax", null);
//				writer.writeAttribute("data-jsf-event", "click", null);
//			}
//		}
		if (divOrSpan(component)) {
			// to allow other tags than just div and span (ex: aside, article, etc.).
			writeTag(component, writer);
			writeIdAttributeIfNecessary(context, writer, component);
			if (styleClass != null) {
				writer.writeAttribute("class", styleClass, "styleClass");

			}
			// JAVASERVERFACES-3270: do not manually render "style" as it is
			// handled
			// in renderPassThruAttributes().
		}

		MyRenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		rendererParamsNotNull(context, component);

		if (!shouldEncode(component)) {
			return;
		}
		ResponseWriter writer = context.getResponseWriter();
		if (divOrSpan(component)) {
			if ("block".equals(component.getAttributes().get("layout"))) {
				writer.endElement("div");
			} else {
				writer.endElement("span");
			}
		}
	}

	private void writeTag(UIComponent component, ResponseWriter writer) throws IOException {
		String layout = (String) component.getAttributes().get("layout");
		if(null == layout){
			writer.startElement("span", component);
		}else{
			if( "block".equals(layout) ){
				writer.startElement("div", component);
			}else{
				// Dunno if this should be done. Seems a bit safer.
				if(Arrays.binarySearch(MyHtmlValidTags.TAGS_VALID, layout ) >= 0){
					writer.startElement(layout, component);
				}else{
					writer.startElement("span", component);
				}
			}
		}
	}

	private boolean divOrSpan(UIComponent component) {

		return (shouldWriteIdAttribute(component) || (component.getAttributes().get("style") != null)
				|| (component.getAttributes().get("styleClass") != null));

	}
}
