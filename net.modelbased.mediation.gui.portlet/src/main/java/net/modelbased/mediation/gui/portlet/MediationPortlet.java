package net.modelbased.mediation.gui.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.xml.namespace.QName;

/**
 * Implementation of the event processing supported by the Mediation portlet.
 * The portlet can basically receive a event named "showMapping(mappingId)",
 * where mappingId is a simple string describing the unique ID of the mapping to
 * display, and send the event "mappingComplete(mappingId)" when the user
 * considers the mapping as complete
 * 
 * @author Franck Chauvel - SINTEF ICT
 * 
 * @since 0.0.1
 */
public class MediationPortlet extends GenericPortlet {
	
	
	private static final String VIEW_MODE_PAGE = "/view.jsp";
	
	private PortletRequestDispatcher viewMode;

	public static final QName SHOW_MAPPING = new QName(
			"http://www.modelbased.net", "showMapping");
	
	public static final QName MAPPING_COMPLETE = new QName(
			"http://www.modelbased.net", "mappingComplete");



	/*
	 * (non-Javadoc)
	 * @see javax.portlet.GenericPortlet#init(javax.portlet.PortletConfig)
	 */
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		viewMode = config.getPortletContext().getRequestDispatcher(
				VIEW_MODE_PAGE);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.portlet.GenericPortlet#doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {		
		viewMode.include(request, response);
	}


	/*
	 * (non-Javadoc)
	 * @see javax.portlet.GenericPortlet#destroy()
	 */
	public void destroy() {
		viewMode = null;
		super.destroy();
	}

	/**
	 * Event handler to handle the external "showMapping" event (sent by the
	 * composition portlet).
	 * 
	 */
	@ProcessEvent(qname = "{http://www.modelbased.net}showMapping")
	public void showMapping(EventRequest request, EventResponse response) {
		Event event = request.getEvent();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>" + event.getValue());
	}

	/**
	 * Trigger the mapping complete action
	 */
	@ProcessAction(name = "mappingComplete")
	public void mappingComplete(ActionRequest request, ActionResponse response) {
		response.setEvent(MediationPortlet.MAPPING_COMPLETE, "Hello!");
	}

}