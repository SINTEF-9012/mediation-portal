package net.modelbased.mediation.gui.portlet;
 
import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig; 
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.xml.namespace.QName;


/**
 * Implementation of the event processing supported by the Mediation portlet.
 * The portlet can basically receive a event named "mappingResult(mappingId)",
 * where mappingId is a simple string describing the unique ID of the mapping to
 * display, and send the event "mappingComplete(mappingId)" when the user
 * considers the mapping as complete
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
public class MediationPortlet extends GenericPortlet {

	
	private static final String NORMAL_VIEW = "/view.jsp";
	private PortletRequestDispatcher normalView;

	private String message = null;
	
	public static final QName SHOW_MAPPING = new QName("http://www.modelbased.net", "showMapping");
	public static final QName MAPPING_COMPLETE = new QName("http://www.modelbased.net", "mappingComplete");

	public static final String MESSAGE_ATTR = "MESSAGE";

	private int counter = 0;
	
	public void doView(RenderRequest request, RenderResponse response) throws PortletException,
			IOException
	{		
		if (message !=null)
			request.setAttribute(MESSAGE_ATTR, "Message(" + ++counter + ")" + message);
		
		normalView.include(request, response);
		
		message = null;
	}

	public void init(PortletConfig config) throws PortletException
	{
		super.init(config);
		normalView = config.getPortletContext().getRequestDispatcher(NORMAL_VIEW);
	}

	public void destroy()
	{
		normalView = null;
		super.destroy();
	}
	
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException
	{		
		response.setEvent(MediationPortlet.MAPPING_COMPLETE, "Hello!");
	}

	public void processEvent(EventRequest eventRequest, EventResponse response)
			throws PortletException, IOException
	{
		Event event = eventRequest.getEvent();
		if (event.getQName().equals(MediationPortlet.SHOW_MAPPING)) {
			message = (String) event.getValue();
		}			
	}
	

}