package com.noinlinejs.jsf.customRenderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.noinlinejs.jsf.renderersUtils.MyClientWidgetRenderer;
import com.noinlinejs.jsf.renderersUtils.MyRenderKitUtils;
import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.TextareaRenderer;

public class MyTextareaRenderer extends TextareaRenderer {
    private static final Attribute[] ATTRIBUTES =
            AttributeManager.getAttributes(AttributeManager.Key.INPUTTEXTAREA);

      @Override
      protected void getEndTextToRender(FacesContext context,
                                        UIComponent component,
                                        String currentValue) throws IOException {

          ResponseWriter writer = context.getResponseWriter();
          assert(writer != null);


          writer.startElement("textarea", component);
          writeIdAttributeIfNecessary(context, writer, component);
          writer.writeAttribute("name", component.getClientId(context),
                                "clientId");

          // style is rendered as a passthru attribute
          MyRenderKitUtils.renderPassThruAttributes(context,
                                                  writer,
                                                  component,
                                                  ATTRIBUTES);
          RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);

          MyRenderKitUtils.renderOnchange(context, component, false);
          
          String styleClass =
                  (String) component.getAttributes().get("styleClass");

          if (null != styleClass) {
              writer.writeAttribute("class", styleClass, "styleClass");
          }
          
          MyClientWidgetRenderer.writeCWidget(writer, component);

          if (component.getAttributes().containsKey("com.sun.faces.addNewLineAtStart") &&
                  "true".equalsIgnoreCase((String) component.getAttributes().get("com.sun.faces.addNewLineAtStart"))) {
              writer.writeText("\n", null);
          }

          // render default text specified
          if (currentValue != null) {
              writer.writeText(currentValue, component, "value");
          }

          writer.endElement("textarea");

      }

  } // end of class TextareaRenderer