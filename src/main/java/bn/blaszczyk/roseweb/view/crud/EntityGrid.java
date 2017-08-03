package bn.blaszczyk.roseweb.view.crud;

import bn.blaszczyk.rose.model.Entity;
import bn.blaszczyk.rose.model.Writable;
import bn.blaszczyk.rosecommon.tools.TypeManager;

import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.NumberRenderer;

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class EntityGrid<T extends Writable> extends Grid<T> {

	private static final long serialVersionUID = 6267674139996026011L;

	public EntityGrid(final Class<T> type)
    {
        setSizeFull();

        
        final Entity entityModel = TypeManager.getEntity(type);
        addColumn(Writable::getId, new NumberRenderer()).setCaption("Id");
        for(int i = 0; i < entityModel.getFields().size(); i++)
        {
        	final int index = i;
        	addColumn(e -> e.getFieldValue(index)).setCaption(entityModel.getFields().get(i).getName());
        }

//        // Format and add " €" to price
//        final DecimalFormat decimalFormat = new DecimalFormat();
//        decimalFormat.setMaximumFractionDigits(2);
//        decimalFormat.setMinimumFractionDigits(2);
//        addColumn(product -> decimalFormat.format(product.getPrice()) + " €")
//                        .setCaption("Price").setComparator((p1, p2) -> {
//                            return p1.getPrice().compareTo(p2.getPrice());
//                        }).setStyleGenerator(product -> "align-right");
//
//        // Add an traffic light icon in front of availability
//        addColumn(this::htmlFormatAvailability, new HtmlRenderer())
//                .setCaption("Availability").setComparator((p1, p2) -> {
//                    return p1.getAvailability().toString()
//                            .compareTo(p2.getAvailability().toString());
//                });
//
//        // Show empty stock as "-"
//        addColumn(product -> {
//            if (product.getStockCount() == 0) {
//                return "-";
//            }
//            return Integer.toString(product.getStockCount());
//        }).setCaption("Stock Count").setComparator((p1, p2) -> {
//            return Integer.compare(p1.getStockCount(), p2.getStockCount());
//        }).setStyleGenerator(product -> "align-right");
//
//        // Show all categories the product is in, separated by commas
//        addColumn(this::formatCategories).setCaption("Category").setSortable(false);
    }

    public T getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(T product) {
        getDataCommunicator().refresh(product);
    }

//    private String htmlFormatAvailability(Product product) {
//        Availability availability = product.getAvailability();
//        String text = availability.toString();
//
//        String color = "";
//        switch (availability) {
//        case AVAILABLE:
//            color = "#2dd085";
//            break;
//        case COMING:
//            color = "#ffc66e";
//            break;
//        case DISCONTINUED:
//            color = "#f54993";
//            break;
//        default:
//            break;
//        }
//
//        String iconCode = "<span class=\"v-icon\" style=\"font-family: "
//                + VaadinIcons.CIRCLE.getFontFamily() + ";color:" + color
//                + "\">&#x"
//                + Integer.toHexString(VaadinIcons.CIRCLE.getCodepoint())
//                + ";</span>";
//
//        return iconCode + " " + text;
//    }
//
//    private String formatCategories(Product product) {
//        if (product.getCategory() == null || product.getCategory().isEmpty()) {
//            return "";
//        }
//        return product.getCategory().stream()
//                .sorted(Comparator.comparing(Category::getId))
//                .map(Category::getName).collect(Collectors.joining(", "));
//    }
}
