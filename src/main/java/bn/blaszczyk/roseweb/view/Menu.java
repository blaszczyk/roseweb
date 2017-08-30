package bn.blaszczyk.roseweb.view;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

public class Menu extends CssLayout {

	private static final long serialVersionUID = 1266295788682351710L;
	
	private static final String VALO_MENUITEMS = "valo-menuitems";
	private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
	private static final String VALO_MENU_VISIBLE = "valo-menu-visible-deunemudda";
	private Navigator navigator;
	private Map<String, Button> viewButtons = new HashMap<String, Button>();

	private CssLayout menuItemsLayout;
	private CssLayout menuPart;

	public Menu(Navigator navigator)
	{
		this.navigator = navigator;
		setPrimaryStyleName(ValoTheme.MENU_ROOT);
		menuPart = new CssLayout();
		menuPart.addStyleName(ValoTheme.MENU_PART);

		final HorizontalLayout top = new HorizontalLayout();
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName(ValoTheme.MENU_TITLE);
		Label title = new Label("RoseWeb");
		title.addStyleName(ValoTheme.LABEL_H3);
		title.setSizeUndefined();
		top.addComponent(title);
		menuPart.addComponent(top);

		final MenuBar logoutMenu = new MenuBar();
		logoutMenu.addItem("Logout", VaadinIcons.SIGN_OUT, this::logout);


		logoutMenu.addStyleName("user-menu");
		menuPart.addComponent(logoutMenu);

		final Button showMenu = new Button("Menu", e -> toggleMenu());
		showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
		showMenu.addStyleName(VALO_MENU_TOGGLE);
		showMenu.setIcon(VaadinIcons.MENU);
		menuPart.addComponent(showMenu);

		menuItemsLayout = new CssLayout();
//		menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
		menuPart.addComponent(menuItemsLayout);

		addComponent(menuPart);
	}

	public void addView(final View view, final String name, final String caption, final Resource icon)
	{
		navigator.addView(name, view);
		createViewButton(name, caption, icon);
	}

	public void addView(final Class<? extends View> viewClass, final String name, final String caption, final Resource icon)
	{
		navigator.addView(name, viewClass);
		createViewButton(name, caption, icon);
	}
	
	public void toggleMenu(final ClickEvent event)
	{
		toggleMenu();
	}
	
	private void toggleMenu()
	{
		setMenuVisible(!menuPart.getStyleName().contains(VALO_MENU_VISIBLE));
	}
	
	private void setMenuVisible(final boolean visible)
	{
		if (visible)
		{
			menuPart.addStyleName(VALO_MENU_VISIBLE);
		}
		else
		{
			menuPart.removeStyleName(VALO_MENU_VISIBLE);
		}
	}
	
	private void logout(final MenuItem item)
	{
		VaadinSession.getCurrent().getSession().invalidate();
		Page.getCurrent().reload();
	}

	private void createViewButton(final String name, final String caption, final Resource icon)
	{
		final Button button = new Button(caption, event -> navigator.navigateTo(name));
		button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
		button.setIcon(icon);
		menuItemsLayout.addComponent(button);
		viewButtons.put(name, button);
	}

	public void setActiveView(final String viewName)
	{
		for (Button button : viewButtons.values())
		{
			button.removeStyleName("selected");
		}
		final Button selected = viewButtons.get(viewName);
		if (selected != null)
		{
			selected.addStyleName("selected");
		}
		setMenuVisible(false);
	}
}
