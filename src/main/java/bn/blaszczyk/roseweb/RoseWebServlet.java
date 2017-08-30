package bn.blaszczyk.roseweb;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import bn.blaszczyk.rose.RoseException;
import bn.blaszczyk.rosecommon.tools.Preferences;
import bn.blaszczyk.rosecommon.tools.TypeManager;


@WebServlet(urlPatterns = "/*", name = "roseweb", asyncSupported = true)
@VaadinServletConfiguration(ui = RoseWebUI.class, productionMode = false)
public class RoseWebServlet extends VaadinServlet
{

	private static final long serialVersionUID = -2756738053941356169L;

	@Override
	public void init(final ServletConfig servletConfig) throws ServletException
	{
		super.init(servletConfig);
		final String roseFile = servletConfig.getInitParameter("rose-file");
		try
		{
			TypeManager.parseRoseFile(RoseWebServlet.class.getResourceAsStream(roseFile));
		}
		catch (RoseException e)
		{
			throw new ServletException("error initializing rose environment", e);
		}
		Preferences.setMainClass(RoseWebServlet.class);
	}
	
	
	
	
}