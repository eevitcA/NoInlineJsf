package com.noinlinejs.jsf.customRenderers;

import java.io.IOException;
import java.util.logging.Level;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.SelectItem;

import com.noinlinejs.jsf.renderersUtils.MyRenderKitUtils;
import com.sun.faces.io.FastStringWriter;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.SelectItemsIterator;
import com.sun.faces.renderkit.html_basic.MenuRenderer;

public class MyMenuRenderer extends MenuRenderer{
	
    private static final Attribute[] ATTRIBUTES =
            AttributeManager.getAttributes(AttributeManager.Key.SELECTMANYMENU);
    
    // Render the "select" portion..
    //
    protected void renderSelect(FacesContext context,
                                UIComponent component) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);

        if (logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER, "Rendering 'select'");
        }
        writer.startElement("select", component);
        writeIdAttributeIfNecessary(context, writer, component);
        writer.writeAttribute("name", component.getClientId(context),
                              "clientId");
        if (!getMultipleText(component).equals("")) {
            writer.writeAttribute("multiple", true, "multiple");
        }

        // Determine how many option(s) we need to render, and update
        // the component's "size" attribute accordingly;  The "size"
        // attribute will be rendered as one of the "pass thru" attributes
        SelectItemsIterator<SelectItem> items = RenderKitUtils.getSelectItems(context, component);

        // render the options to a buffer now so that we can determine
        // the size
        FastStringWriter bufferedWriter = new FastStringWriter(128);
        context.setResponseWriter(writer.cloneWithWriter(bufferedWriter));
        int count = renderOptions(context, component, items);
        context.setResponseWriter(writer);
        // If "size" is *not* set explicitly, we have to default it correctly
        Integer size = (Integer) component.getAttributes().get("size");
        if (size == null || size == Integer.MIN_VALUE) {
            size = count;
        }
        writeDefaultSize(writer, size);

        MyRenderKitUtils.renderPassThruAttributes(context,
                                                writer,
                                                component,
                                                ATTRIBUTES,
                                                getNonOnChangeBehaviors(component));
        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer,
                                                         component);

        MyRenderKitUtils.renderOnchange(context, component, false);
        
        // render styleClass attribute if present.
        String styleClass;
        if (null !=
            (styleClass =
                  (String) component.getAttributes().get("styleClass"))) {
            writer.writeAttribute("class", styleClass, "styleClass");
        }

        // Now, write the buffered option content
        writer.write(bufferedWriter.toString());
        
        writer.endElement("select");

    }
}
