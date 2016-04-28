package com.noinlinejs.jsf.customRenderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.noinlinejs.jsf.renderersUtils.MyRenderKitUtils;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.CheckboxRenderer;

public class MyCheckBoxRenderer extends CheckboxRenderer{
	private static final Attribute[] ATTRIBUTES =
			           AttributeManager.getAttributes(AttributeManager.Key.SELECTBOOLEANCHECKBOX);
	
	@Override
	protected void getEndTextToRender(FacesContext context, UIComponent component, String currentValue)
			throws IOException {
	        ResponseWriter writer = context.getResponseWriter();
	        assert(writer != null);
	        String styleClass;

	        writer.startElement("input", component);
	        writeIdAttributeIfNecessary(context, writer, component);
	        writer.writeAttribute("type", "checkbox", "type");
	        writer.writeAttribute("name", component.getClientId(context),
	                              "clientId");

	        if (Boolean.valueOf(currentValue)) { 
	            writer.writeAttribute("checked", Boolean.TRUE, "value");
	        }
	        
	        MyRenderKitUtils.renderPassThruAttributes(context,
	                                                writer,
	                                                component,
	                                                ATTRIBUTES,
	                                                getNonOnClickSelectBehaviors(component));
	        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);

	        MyRenderKitUtils.renderSelectOnclick(context, component, false);
	        
	        if (null != (styleClass = (String)
		              component.getAttributes().get("styleClass"))) {
		            writer.writeAttribute("class", styleClass, "styleClass");
		        }

	        writer.endElement("input");
	}
}
