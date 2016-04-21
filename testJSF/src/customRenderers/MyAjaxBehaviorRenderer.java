package customRenderers;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.AjaxBehaviorRenderer;

public class MyAjaxBehaviorRenderer extends AjaxBehaviorRenderer {
	
	// When the execute attribute is not specified, this boolean dictate if the default behavior is to execute the whole form or not. 
	// Default is like default jsf (false).
	private static final boolean ASSUME_FORM_EXEC_ON_EMPTY_EXEC = false;

	public void buildCommands(ResponseWriter writer, UIComponent component, ClientBehavior behavior,
			ClientBehaviorContext behaviorContext) throws IOException {

		if (!(behavior instanceof AjaxBehavior)) {
			throw new IllegalArgumentException(
					"Instance of javax.faces.component.behavior.AjaxBehavior required: " + behavior);
		}

		if (((AjaxBehavior) behavior).isDisabled()) {
			return;
		}
		buildAjaxCommand(behaviorContext, (AjaxBehavior) behavior, namespaceParameters, writer, component);
	}

	private static void buildAjaxCommand(ClientBehaviorContext behaviorContext, AjaxBehavior ajaxBehavior, boolean namespaceParameters, ResponseWriter writer, UIComponent component) throws IOException{
	    // First things first - if AjaxBehavior is disabled, we are done.
        if (ajaxBehavior.isDisabled()) {
            return;
        }        
        
        String eventName = behaviorContext.getEventName();

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
        
        // if event is not click, event listener is added on the client by finding the appropriate classNames.
        if( ! "click".equals(eventName)){
        	String styleClass = (String) component.getAttributes().get("styleClass");
        	styleClass += " jsf-e-" + eventName;
        	component.getAttributes().put("styleClass", styleClass);
        }else{
        	writer.writeAttribute("data-widget", "jsfajax", null);
        }

        appendIds(component, writer, execute, "execute");
        appendIds(component, writer, render, "render");
        
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

            if (namingContainerId != null) {
            	//TODO only interesting for portlet based apps (not servlet based apps).
            	// http://stackoverflow.com/questions/36772044/when-does-mojarra-adds-a-naming-container-to-the-list-of-optional-parameters?noredirect=1#comment61121377_36772044
            	writer.writeAttribute("data-namingContainerId", namingContainerId, null);
            }

            if (onevent != null) {
            	writer.writeAttribute("data-onevent", onevent, null);
            }

            if (onerror != null) {
            	writer.writeAttribute("data-onerror", onerror, null);
            }
            
            if (delay != null) {
            	writer.writeAttribute("data-delay", delay, null);
            }
            
            if (resetValues != null) {
            	writer.writeAttribute("data-resetValues", resetValues, null);
            }
            //TODO Not sure what that does.
            
            if (!params.isEmpty()) {
            	String paramsStr = "";
                for (ClientBehaviorContext.Parameter param : params) {
                	paramsStr += param.getName() + "/" + param.getValue() + ",";
                }
                writer.writeAttribute("data-jsf-params", paramsStr, null);
            }

        }
	}
    // Appends an ids argument to the ajax command
    private static void appendIds(UIComponent component,
    							  ResponseWriter writer,
                                  Collection<String> ids,
                                  String attName) throws IOException {

    	//no need to append '0' as if the attr is not found in js it will automatically assume '0'.
        if ((null == ids) || ids.isEmpty()) {
            return;
        }
        
        String strIds = "";
        boolean first = true;

        for (String id : ids) {
            if (id.trim().length() == 0) {
                continue;
            }
            if (!first) {
            	strIds += ' ';
            } else {
                first = false;
            }
            
            // the # char is used in the js as an helper, for example for finding siblings in a tree like structure.
            if(id.charAt(0) == '#'){
            	strIds += id;
            	continue;
            }

            if (id.equals("@all") || id.equals("@none") ||
                id.equals("@form") || id.equals("@this")) {
            	strIds += id;
            } else {
            	strIds += getResolvedId(component, id);
            }
        }
        writer.writeAttribute("data-" + attName, strIds, null);

    }
    
    // Returns the resolved (client id) for a particular id.
    private static String getResolvedId(UIComponent component, String id) {

        UIComponent resolvedComponent = component.findComponent(id);
        if (resolvedComponent == null) {
            if (id.charAt(0) == UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance())) {
                return id.substring(1);
            }
            return id;
        }

        return resolvedComponent.getClientId();
    }
}
