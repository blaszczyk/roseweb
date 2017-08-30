package bn.blaszczyk.roseweb;

import bn.blaszczyk.rose.RoseException;
import bn.blaszczyk.rosecommon.controller.ControllerBuilder;
import bn.blaszczyk.rosecommon.controller.ModelController;
import bn.blaszczyk.roseweb.view.MainScreen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@Viewport("user-scalable=no,initial-scale=1.0")
//@Theme("rosetheme")
public class RoseWebUI extends UI
{
	public static final String VERSION_ID = "0.3";
	
	private static final long serialVersionUID = -6099879665473884372L;
	
	private static final Logger LOGGER = LogManager.getLogger(RoseWebUI.class);
	
	private final ModelController controller;
	
	public RoseWebUI()
	{
		controller = ControllerBuilder.forService().withCache().build();
	}

	@Override
	protected void init(final VaadinRequest vaadinRequest)
	{
		Responsive.makeResponsive(this);
		setLocale(vaadinRequest.getLocale());
		getPage().setTitle("Rose Web");
		showMainView();
	}
	
	

	@Override
	public void close()
	{
		super.close();
		try
		{
			controller.close();
		}
		catch (final RoseException e)
		{
			LOGGER.error("error closing model controller",e);
		}
	}

	private void showMainView()
	{
		addStyleName(ValoTheme.UI_WITH_MENU);
		setContent(new MainScreen());
		getNavigator().navigateTo(getNavigator().getState());
	}

	public static RoseWebUI get()
	{
		return (RoseWebUI) UI.getCurrent();
	}
	
	public static ModelController getModelController()
	{
		return get().controller;
	}
}
