package bn.blaszczyk.roseweb.view.crud;

import bn.blaszczyk.rose.model.Writable;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ListView<T extends Writable> extends CssLayout implements View
{
	private static final long serialVersionUID = 5423863915998405006L;
	
	private EntityGrid<T> grid;
//	private EntityForm<T> form;
	private TextField filter;

	private ListLogic<T> viewLogic;
	private Button newProduct;

	private EntityDataProvider<T> dataProvider;

	private final Class<T> type;

	public ListView(final Class<T> type) {
		this.type = type;
		this.dataProvider = new EntityDataProvider<T>(type);
		viewLogic = new ListLogic<>(this, type);
		setSizeFull();
		addStyleName("crud-view");
		HorizontalLayout topLayout = createTopBar();

		grid = new EntityGrid<T>(type);
		grid.setDataProvider(dataProvider);
		grid.asSingleSelect().addValueChangeListener(event -> viewLogic.rowSelected(event.getValue()));

//		form = new EntityForm<T>(viewLogic, type);
		// form.setCategories(DataService.get().getAllCategories());

		VerticalLayout barAndGridLayout = new VerticalLayout();
		barAndGridLayout.addComponent(topLayout);
		barAndGridLayout.addComponent(grid);
		barAndGridLayout.setSizeFull();
		barAndGridLayout.setExpandRatio(grid, 1);
		barAndGridLayout.setStyleName("crud-main-layout");

		addComponent(barAndGridLayout);
		// addComponent(form);

		viewLogic.init();
	}

	public HorizontalLayout createTopBar() {
		filter = new TextField();
		filter.setStyleName("filter-textfield");
		filter.setPlaceholder("Filter name, availability or category");
		// ResetButtonForTextField.extend(filter);
		// // Apply the filter to grid's data provider. TextField value is never null
		filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

		newProduct = new Button("New " + type.getSimpleName());
		newProduct.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newProduct.setIcon(VaadinIcons.PLUS_CIRCLE);
		newProduct.addClickListener(click -> viewLogic.newProduct());

		HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setWidth("100%");
		topLayout.addComponent(filter);
		topLayout.addComponent(newProduct);
		topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
		topLayout.setExpandRatio(filter, 1);
		topLayout.setStyleName("top-bar");
		return topLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		viewLogic.enter(event.getParameters());
	}

	public void showError(String msg) {
		Notification.show(msg, Type.ERROR_MESSAGE);
	}

	public void showSaveNotification(String msg) {
		Notification.show(msg, Type.TRAY_NOTIFICATION);
	}

	public void setNewProductEnabled(boolean enabled) {
		newProduct.setEnabled(enabled);
	}

	public void clearSelection() {
		grid.getSelectionModel().deselectAll();
	}

	public void selectRow(T entity) {
		grid.getSelectionModel().select(entity);
	}

	public T getSelectedRow() {
		return grid.getSelectedRow();
	}

	public void editProduct(T product) {
//		if (product != null) {
//			form.addStyleName("visible");
//			form.setEnabled(true);
//		} else {
//			form.removeStyleName("visible");
//			form.setEnabled(false);
//		}
//		form.editProduct(product);
	}
}
