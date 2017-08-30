package bn.blaszczyk.roseweb.view.crud;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import bn.blaszczyk.rose.model.Entity;
import bn.blaszczyk.rose.model.EnumField;
import bn.blaszczyk.rose.model.Field;
import bn.blaszczyk.rose.model.PrimitiveField;
import bn.blaszczyk.rose.model.Writable;
import bn.blaszczyk.rosecommon.tools.TypeManager;

public class PrimitivesForm<T extends Writable> extends VerticalLayout
{

	private static final long serialVersionUID = 2643286674848651013L;
	
	private final T entity;
	private final Binder<T> binder;

	public PrimitivesForm(final T entity, final Class<T> type)
	{
		this.entity = entity;
		binder = new Binder<>(type);
		final Entity entityModel = TypeManager.getEntity(entity);
		
		binder.setBean(entity);
		for(int i = 0; i < entity.getFieldCount(); i++)
		{
			final int index = i;
			final Field field = entityModel.getFields().get(i);
			addComponent(newPrimitiveComponent(index, field));
		}
	}
	
	private Component newPrimitiveComponent(final int index, final Field field)
	{
		final String caption = field.getCapitalName();
		final String fieldName = field.getName();
		final Object value = entity.getFieldValue(index);
		if(field instanceof EnumField)
		{
			final List<Object> enumOptions = Arrays.asList(TypeManager.getClass(((EnumField)field).getEnumType()).getEnumConstants());
			final ComboBox<Object> comboBox = new ComboBox<>(caption,enumOptions);
			comboBox.setSelectedItem(value);
			binder.forField(comboBox).bind(fieldName);
			return comboBox;
		}
		else
			switch (((PrimitiveField)field).getType())
			{
			case CHAR:
			case VARCHAR:
				final TextField textField = new TextField(caption, (String) value, this::valueChanged);
				binder.forField(textField).bind(fieldName);
				return textField;
			case DATE:
				final LocalDate date = ((Date)value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				final DateField dateField = new DateField(caption, date);
				dateField.addValueChangeListener(this::valueChanged);
				binder.forField(dateField).withConverter(new LocalDateToDateConverter()).bind(fieldName);
				return dateField;
			case BOOLEAN:
				final CheckBox checkBox = new CheckBox(caption, (Boolean)value);
				checkBox.addValueChangeListener(this::valueChanged);
				binder.forField(checkBox).bind(fieldName);
				return checkBox;
			case INT:
				final TextField intField = new TextField(caption, String.valueOf(value), this::valueChanged);
				binder.forField(intField)
					.withConverter(new StringToIntegerConverter(0, "no integer value entered"))
					.bind(field.getName());
				return intField;
			case NUMERIC:
				final TextField numField = new TextField(caption, String.valueOf(value), this::valueChanged);
				binder.forField(numField)
					.withConverter(new StringToBigDecimalConverter(BigDecimal.ZERO, "no integer value entered"))
					.bind(field.getName());
				return numField;
			}
		return null; // make compiler happy
	}
	
	private void valueChanged(final ValueChangeEvent<?> event)
	{
		//
	}
	

}
