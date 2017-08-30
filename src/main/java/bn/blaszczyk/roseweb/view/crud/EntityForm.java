package bn.blaszczyk.roseweb.view.crud;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import bn.blaszczyk.rose.model.Writable;

public class EntityForm<T extends Writable> extends VerticalLayout
{
	private static final long serialVersionUID = -1439498283530649947L;

	private PrimitivesForm<T> primitivesForm;
	
	private final Class<T> type;
	
	
	public EntityForm(final Class<T> type)
	{
		this.type = type;
//		setStyleName("entity-form-wrapper");
	}
	
	public void setEntity(final T entity)
	{
		removeAllComponents();
		if(entity == null)
			return;
		this.primitivesForm = new PrimitivesForm<>(entity,type);
		addComponents(
				new Label(entity.getClass().getSimpleName() + " - " + entity.getId()),
				primitivesForm
				);
	}

}
