package bn.blaszczyk.roseweb.view.crud;

import bn.blaszczyk.rose.RoseException;
import bn.blaszczyk.rose.model.Writable;
import bn.blaszczyk.rosecommon.controller.ModelController;
import bn.blaszczyk.rosecommon.tools.TypeManager;
import bn.blaszczyk.roseweb.RoseWebUI;

import java.io.Serializable;
import com.vaadin.server.Page;

public class ListLogic<T extends Writable> implements Serializable {

	private static final long serialVersionUID = 375826348894376431L;
	
	private final ListView<T> view;
	private final ModelController controller = RoseWebUI.getModelController();
	private final Class<T> type;

	public ListLogic(final ListView<T> view, final Class<T> type) {
		this.type = type;
		this.view = view;
	}

	public void init() {
		editProduct(null);
	}

	public void cancelProduct() {
		setFragmentParameter("");
		view.clearSelection();
	}

	private void setFragmentParameter(String productId) {
		String fragmentParameter;
		if (productId == null || productId.isEmpty()) {
			fragmentParameter = "";
		} else {
			fragmentParameter = productId;
		}

		Page page = RoseWebUI.get().getPage();
		page.setUriFragment("!list/" + fragmentParameter, false);
	}

	public void enter(String productId) {
		if (productId != null && !productId.isEmpty()) {
			if (productId.equals("new")) {
				newEntity();
			} else {
				// Ensure this is selected even if coming directly here from
				// login
				try {
					int pid = Integer.parseInt(productId);
					T entity = controller.getEntityById(type, pid);
					view.selectRow(entity);
				} catch (NumberFormatException e) {
				} catch (RoseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void saveProduct(T product) {
		view.showSaveNotification(product.getEntityName() + " (" + product.getId() + ") updated");
		view.clearSelection();
		try {
			controller.update(product);
		} catch (RoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setFragmentParameter("");
	}

	public void deleteProduct(Writable entity) {
		view.showSaveNotification(entity.getEntityName() + " (" + entity.getId() + ") removed");
		view.clearSelection();
		try {
			controller.delete(entity);
		} catch (RoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setFragmentParameter("");
	}

	public void editProduct(Writable entity) {
		if (entity == null) {
			setFragmentParameter("");
		} else {
			setFragmentParameter(entity.getId() + "");
			try {
				controller.update(entity);
			} catch (RoseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void newEntity() {
		view.clearSelection();
		setFragmentParameter("new");
		try {
			view.editProduct(TypeManager.newInstance(type));
		} catch (RoseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rowSelected(T product) {
		view.editProduct(product);
	}
}
