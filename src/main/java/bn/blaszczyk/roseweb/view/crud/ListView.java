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

public class ListView<T extends Writable> extends HorizontalLayout implements View {
	private static final long serialVersionUID = 5423863915998405006L;

	private EntityGrid<T> grid;
	private EntityForm<T> form;
	private TextField filter;

	private ListLogic<T> viewLogic;
	private Button newEntity;

	private EntityDataProvider<T> dataProvider;

	private final Class<T> type;

	public ListView(final Class<T> type) {
		this.type = type;
		this.dataProvider = new EntityDataProvider<T>(type);
		viewLogic = new ListLogic<>(this, type);
		setSizeFull();
		final HorizontalLayout topLayout = createTopBar();

		grid = new EntityGrid<T>(type);
		grid.setDataProvider(dataProvider);
		grid.asSingleSelect().addValueChangeListener(event -> viewLogic.rowSelected(event.getValue()));

		form = new EntityForm<T>(type);

		VerticalLayout barAndGridLayout = new VerticalLayout();
		barAndGridLayout.addComponent(topLayout);
		barAndGridLayout.addComponent(grid);
		barAndGridLayout.setSizeFull();
		barAndGridLayout.setExpandRatio(grid, 1);
		barAndGridLayout.setStyleName("crud-main-layout");

		addComponent(barAndGridLayout);
		addComponent(form);

		setExpandRatio(barAndGridLayout, 1);

		viewLogic.init();
	}

	public HorizontalLayout createTopBar() {
		filter = new TextField();
		filter.setPlaceholder("Filter");
		filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));
		filter.setWidthUndefined();

		newEntity = new Button("New " + type.getSimpleName());
		newEntity.addStyleName(ValoTheme.BUTTON_PRIMARY);
		newEntity.setIcon(VaadinIcons.PLUS_CIRCLE);
		newEntity.addClickListener(click -> viewLogic.newEntity());
		newEntity.setWidthUndefined();

		final HorizontalLayout topLayout = new HorizontalLayout();
		topLayout.setWidth("100%");
		
		topLayout.addComponent(filter);		
		topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
		topLayout.setExpandRatio(filter, 3);

		topLayout.addComponent(newEntity);
		topLayout.setComponentAlignment(newEntity, Alignment.MIDDLE_RIGHT);
		topLayout.setExpandRatio(newEntity, 2);
		
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
		newEntity.setEnabled(enabled);
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

	public void editProduct(final T entity) {
		if (entity != null) {
			form.setWidth("400px");
			form.addStyleName("visible");
			form.setEnabled(true);
		} else {
			form.setWidth("0px");
			form.removeStyleName("visible");
			form.setEnabled(false);
		}
		form.setEntity(entity);
	}
}
