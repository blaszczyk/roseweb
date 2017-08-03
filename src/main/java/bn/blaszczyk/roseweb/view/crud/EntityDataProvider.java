package bn.blaszczyk.roseweb.view.crud;

import java.util.Objects;
import java.util.stream.Stream;

import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.Query;

import bn.blaszczyk.rose.model.Writable;
import bn.blaszczyk.rosecommon.RoseException;
import bn.blaszczyk.rosecommon.controller.ModelController;
import bn.blaszczyk.roseweb.RoseWebUI;

public class EntityDataProvider<T extends Writable> extends AbstractDataProvider<T, String> {

	private static final long serialVersionUID = -8322759640688872965L;

	private String filterText = "";

	private final Class<T> type;
	private final ModelController controller = RoseWebUI.getModelController();

	public EntityDataProvider(final Class<T> type) {
		this.type = type;
	}

	public void save(final T entity) {
		boolean newWritable = entity.getId() == -1;

		try {
			controller.update(entity);
		} catch (RoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (newWritable) {
			refreshAll();
		} else {
			refreshItem(entity);
		}
	}

	public void delete(Writable entity) {
		try {
			controller.delete(entity);
		} catch (RoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshAll();
	}

	public void setFilter(String filterText) {
		Objects.requireNonNull(filterText, "Filter text cannot be null");
		if (Objects.equals(this.filterText, filterText.trim())) {
			return;
		}
		this.filterText = filterText.trim();

		refreshAll();
	}

	@Override
	public Integer getId(Writable Writable) {
		Objects.requireNonNull(Writable, "Cannot provide an id for a null Writable.");

		return Writable.getId();
	}

	@Override
	public boolean isInMemory() {
		return true;
	}

	@Override
	public int size(final Query<T, String> t) {
		return (int) fetch(t).count();
	}

	@Override
	public Stream<T> fetch(final Query<T, String> query) {
		try {
			final Stream<T> stream = controller.getEntities(type).stream();
			if (filterText.isEmpty())
				return stream;
			else
				return stream.filter(e -> passesFilter(e, filterText));
		} catch (RoseException e) {
			e.printStackTrace();
			return Stream.empty();
		}
	}

	private boolean passesFilter(final T entity, final String filterText) {
		for (int i = 0; i < entity.getFieldCount(); i++)
			if (String.valueOf(entity.getFieldValue(i)).contains(filterText))
				return true;
		return false;
	}
}
