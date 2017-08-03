package bn.blaszczyk.roseweb;

import bn.blaszczyk.rosecommon.controller.ControllerBuilder;
import bn.blaszczyk.rosecommon.controller.ModelController;
import bn.blaszczyk.roseweb.view.MainScreen;

import com.vaadin.annotations.Viewport;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

@Viewport("user-scalable=no,initial-scale=1.0")
public class RoseWebUI extends UI
{
	private static final long serialVersionUID = -6099879665473884372L;
	
	private final ModelController controller;
	
	public RoseWebUI()
	{
		controller = ControllerBuilder.forService().withCache().build();
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Responsive.makeResponsive(this);
		setLocale(vaadinRequest.getLocale());
		getPage().setTitle("Rose Web");
		showMainView();
	}

	protected void showMainView()
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
