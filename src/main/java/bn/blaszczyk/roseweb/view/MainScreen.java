package bn.blaszczyk.roseweb.view;

import bn.blaszczyk.rose.model.Readable;
import bn.blaszczyk.rose.model.Writable;
import bn.blaszczyk.rosecommon.tools.TypeManager;
import bn.blaszczyk.roseweb.RoseWebUI;
import bn.blaszczyk.roseweb.view.crud.ListView;

import java.util.Random;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MainScreen extends HorizontalLayout
{
	private static final long serialVersionUID = 5289798680128338343L;

	private Menu menu;

	public MainScreen()
	{
		final RoseWebUI ui = RoseWebUI.get();
		
		setStyleName("valo-menu-responsive");
		setResponsive(true);
		setSpacing(false);
		setSizeFull();
		
		final CssLayout viewContainer = new CssLayout();
		viewContainer.setSizeFull();

		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);
		navigator.addViewChangeListener(viewChangeListener);
		
		menu = new Menu(navigator);
		for (final Class<? extends Readable> type : TypeManager.getEntityClasses())
		{
			final Class<? extends Writable> subclass = type.asSubclass(Writable.class);
			final int random = new Random().nextInt(VaadinIcons.values().length);
			final Resource icon = VaadinIcons.values()[random];
			menu.addView(new ListView<>(subclass), type.getName(), type.getSimpleName(), icon);
		}

		addComponents(menu,viewContainer);
		setExpandRatio(viewContainer, 1);
	}

	private final ViewChangeListener viewChangeListener = new ViewChangeListener()
	{
		
		private static final long serialVersionUID = -7037369901333403225L;

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			return true;
		}

		@Override
		public void afterViewChange(ViewChangeEvent event) {
			menu.setActiveView(event.getViewName());
		}

	};
}
