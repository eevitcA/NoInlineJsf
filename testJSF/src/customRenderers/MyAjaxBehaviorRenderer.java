package customRenderers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.AjaxBehaviorRenderer;

public class MyAjaxBehaviorRenderer extends AjaxBehaviorRenderer {

	public void buildCommands(ResponseWriter writer, UIComponent component, ClientBehavior behavior,
			ClientBehaviorContext behaviorContext) {

		if (!(behavior instanceof AjaxBehavior)) {
			// TODO: use MessageUtils for this error message?
			throw new IllegalArgumentException(
					"Instance of javax.faces.component.behavior.AjaxBehavior required: " + behavior);
		}

		if (((AjaxBehavior) behavior).isDisabled()) {
			return;
		}
		buildAjaxCommand(behaviorContext, (AjaxBehavior) behavior, namespaceParameters, writer, component);
	}

	private static void buildAjaxCommand(ClientBehaviorContext behaviorContext, AjaxBehavior ajaxBehavior, boolean namespaceParameters, ResponseWriter writer, UIComponent component){
	    // First things first - if AjaxBehavior is disabled, we are done.
        if (ajaxBehavior.isDisabled()) {
            return;
        }        
        
        String eventName = behaviorContext.getEventName();

        StringBuilder ajaxCommand = new StringBuilder(256);
        Collection<String> execute = ajaxBehavior.getExecute();
        Collection<String> render = ajaxBehavior.getRender();
        String onevent = ajaxBehavior.getOnevent();
        String onerror = ajaxBehavior.getOnerror();
        String sourceId = behaviorContext.getSourceId();
        String delay = ajaxBehavior.getDelay();
        Boolean resetValues = null;
        if (ajaxBehavior.isResetValuesSet()) {
            resetValues = ajaxBehavior.isResetValues();
        }
        Collection<ClientBehaviorContext.Parameter> params = behaviorContext.getParameters();

        // Needed workaround for SelectManyCheckbox - if execute doesn't have sourceId,
        // we need to add it - otherwise, we use the default, which is sourceId:child, which
        // won't work.
        ClientBehaviorContext.Parameter foundparam = null;
        for (ClientBehaviorContext.Parameter param : params) {
            if (param.getName().equals("incExec") && (Boolean)param.getValue()) {
                foundparam = param;
            }
        }
        if (foundparam != null && !execute.contains(sourceId)) {
                execute = new LinkedList<String>(execute);
                execute.add(component.getClientId());
        }
        if (foundparam != null) {
            try {
                // And since this is a hack, we now try to remove the param
                params.remove(foundparam);
            } catch (UnsupportedOperationException uoe) {
                if (logger.isLoggable(Level.FINEST)) {
                    logger.log(Level.FINEST, "Unsupported operation", uoe);
                }
            }
        }
        
        writer.writeAttribute("data-widget", "jsfajax", null);

        if (sourceId == null) {
            ajaxCommand.append("this");
        } else {
            ajaxCommand.append("'");
            ajaxCommand.append(sourceId);
            ajaxCommand.append("'");
        }

        ajaxCommand.append(",event,'");
        ajaxCommand.append(eventName);
        ajaxCommand.append("',");

        appendIds(component, ajaxCommand, execute);
        ajaxCommand.append(",");
        appendIds(component, ajaxCommand, render);
        
        String namingContainerId = null;
        if (namespaceParameters) {
            FacesContext context = behaviorContext.getFacesContext();
            UIViewRoot viewRoot = context.getViewRoot();
            if (viewRoot instanceof NamingContainer) {
                namingContainerId = viewRoot.getContainerClientId(context);
            }
        }

        if ((namingContainerId != null) || (onevent != null) || (onerror != null) || (delay != null) || 
                (resetValues != null) || !params.isEmpty())  {

            ajaxCommand.append(",{");

            if (namingContainerId != null) {
                // the literal string must exactly match the corresponding value 
                // in jsf.js.
                RenderKitUtils.appendProperty(ajaxCommand, "com.sun.faces.namingContainerId", namingContainerId, true);
            }

            if (onevent != null) {
                RenderKitUtils.appendProperty(ajaxCommand, "onevent", onevent, false);
            }

            if (onerror != null) {
                RenderKitUtils.appendProperty(ajaxCommand, "onerror", onerror, false);
            }
            
            if (delay != null) {
                RenderKitUtils.appendProperty(ajaxCommand, "delay", delay, true);
            }
            
            if (resetValues != null) {
                RenderKitUtils.appendProperty(ajaxCommand, "resetValues", resetValues, false);
            }

            if (!params.isEmpty()) {
                for (ClientBehaviorContext.Parameter param : params) {
                    RenderKitUtils.appendProperty(ajaxCommand, 
                                                  param.getName(),
                                                  param.getValue());
                }
            }
             
            ajaxCommand.append("}");
        }

        ajaxCommand.append(")");
	}
}
