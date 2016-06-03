package com.nzion.view.component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.nzion.service.common.GenericHomeScreenSearchService;
import com.nzion.service.common.GenericHomeScreenService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDisplay;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilMisc;
import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;
import com.nzion.util.ViewUtil;
import com.nzion.view.component.OspedalePagingListModel.PagedDataModelService;
import com.nzion.zkoss.ext.CsvDataExporter;
import com.nzion.zkoss.ext.DataExporter;
import com.nzion.zkoss.ext.Navigation;

/**
 * @author Sandeep Prusty
 *         Aug 9, 2010
 */

public class HomeGridMacroController extends GenericForwardComposer implements PagedDataModelService {

    private static final int HISTORY_FETCH_SIZE = 5;

    private Class<?> entityClass;

    private String[] fields;

    private String[] labels;

    private String[] searchfields;

    private ListitemRenderer listitemRenderer;

    private static GenericHomeScreenService service = Infrastructure.getSpringBean("genericHomeScreenService");

    private GenericHomeScreenSearchService searchService;

    private DataExporter exporter = new CsvDataExporter();

    private Listbox listbox;

    private String addPageUrl;

    private String editPageUrl;

    private String advancedSearchPageUrl;

    private Boolean addModePopup;

    private Boolean customRefresh;

    private Boolean editModePopup;

    private String exportFileName;

    private String defaultSortColumn;

    private boolean isEditMode = true;

    private String containerName;

    private List<?> listModel = null;

    private int pageSize;

    private boolean defaultSortDesc;

    private int searchStrength = 3;

    private EventListener listitemDoubleClickListener = new EventListener() {
        public void onEvent(Event event) throws Exception {
            isEditMode = true;
            navigate(editPageUrl, editModePopup, UtilMisc.toMap("entity", ((Listitem) event.getTarget()).getValue()));
        }
    };

    private final ListitemRenderer delegatingListitemRenderer = new ListitemRenderer() {
        @Override
        public void render(Listitem item, Object data, int index) throws Exception {
            item.setValue(data);
            if (UtilValidator.isNotEmpty(editPageUrl))
                item.addEventListener("onDoubleClick", listitemDoubleClickListener);
            listitemRenderer.render(item, data, index);
        }
    };

    private final EventListener refreshGridListener = new EventListener() {
        @Override
        public void onEvent(Event event) throws Exception {
            if (isEditMode || customRefresh) {
                refreshListbox();
                return;
            } else {
                loadEntities();
            }
        }
    };

    public HomeGridMacroController(Map<String, Object> args) {
        parseArgs(args);
    }

    public ListitemRenderer getListitemRenderer() {
        return listitemRenderer;
    }

    public String[] getLabels() {
        return labels;
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setLabels(String commaCompositedLabels) {
        if (UtilValidator.isEmpty(commaCompositedLabels))
            return;
        this.labels = commaCompositedLabels.split(",");
        for (int i = 0; i < this.labels.length; ++i) {
            this.labels[i] = this.labels[i].trim();
        }
    }

    @Override
    public void doAfterCompose(Component component) throws Exception {
        super.doAfterCompose(component);
        listbox = (Listbox) component.getFellow("listbox");
        listbox.setItemRenderer(delegatingListitemRenderer);
        pageSize = listbox.getPaginal().getPageSize();
        long count = searchService.getCountForAllIncludingInactives(entityClass);
        setTotalNumberOfItemsForDisplay(count);
        if (listModel == null) {
            loadEntities();
        } else {
            listbox.setModel(new OspedalePagingListModel(pageSize, listModel));
            setTotalNumberOfItemsForDisplay(listModel.size());
        }
    }

    public void loadEntities() {
        long count = searchService.getCountForAllIncludingInactives(entityClass);
        listbox.setModel(new OspedalePagingListModel(pageSize, count, this));
        setTotalNumberOfItemsForDisplay(count);
    }

    public void setTotalNumberOfItemsForDisplay(long totalNumberOfItemsForDisplay) {
        String footerLabel = totalNumberOfItemsForDisplay > 0 ? null : "No records available";
        ((Listfooter) listbox.getListfoot().getFirstChild()).setLabel(footerLabel);
    }

    // The statements order matter here.
    private void parseArgs(Map<String, Object> args) {
        exportFileName = (String) args.get("exportFileName");
        setEntityClass((String) args.get("entityclass"));
        setLabels((String) args.get("labels"));
        setSearchfields((String) args.get("searchfields"));
        setFields((String) args.get("fields"));
        editPageUrl = (String) args.get("editpage");
        setAddPageUrl((String) args.get("addpage"));
        advancedSearchPageUrl = (String) args.get("advancedsearchpage");
        editModePopup = (String) args.get("editmodepopup") == null ? null : Boolean.parseBoolean((String) args.get("editmodepopup"));
        setAddModePopup(Boolean.parseBoolean((String) args.get("addmodepopup")));
        setCustomRefresh(Boolean.parseBoolean((String) args.get("customrefresh")));
        searchService = (GenericHomeScreenSearchService) args.get("searchservice");
        if (searchService == null)
            searchService = service;
        this.listitemRenderer = (ListitemRenderer) args.get("listitemrenderer");
        if (listitemRenderer == null)
            this.listitemRenderer = new DefaultListItemRenderer();
        DataExporter exporter = (DataExporter) args.get("exporter");
        Object listener = args.get("rowdblclicklistener");
        if (listener != null)
            this.listitemDoubleClickListener = (EventListener) listener;
        if (exporter != null)
            this.exporter = exporter;
        parseSortColumn((String) args.get("defaultsort"));
        containerName = (String) args.get("containername");
        listModel = (List<?>) args.get("listModel");
        String searchStrengthString = (String) args.get("searchstrength");
        if (UtilValidator.isNotEmpty(searchStrengthString))
            searchStrength = Integer.parseInt(searchStrengthString);
    }

    private void parseSortColumn(String string) {
        if (UtilValidator.isEmpty(string)) {
            defaultSortColumn = fields[0];
            return;
        }
        defaultSortColumn = string;
        if (defaultSortColumn.startsWith("-")) {
            defaultSortColumn = defaultSortColumn.substring(1);
            defaultSortDesc = true;
        }
    }

    public void setAddPageUrl(String addPageUrl) {
        this.addPageUrl = addPageUrl;
        if (UtilValidator.isEmpty(editPageUrl))
            editPageUrl = addPageUrl;
    }

    public void setAddModePopup(Boolean addModePopup) {
        this.addModePopup = addModePopup;
        if (editModePopup == null)
            editModePopup = addModePopup;
    }

    public void setCustomRefresh(Boolean customRefresh) {
        this.customRefresh = customRefresh;
        if(customRefresh == null )
            this.customRefresh = customRefresh;
    }

    public void searchClicked(String searchString) {
        List<?> entities = null;
        if (searchString == null || (searchString.trim().length() != 0 && searchString.trim().length() < searchStrength)) {
            UtilMessagesAndPopups.showError("Please enter atleast " + searchStrength + " characters to search.");
            return;
        }
        entities = searchService.search(searchString, entityClass, defaultSortColumn, defaultSortDesc, searchfields);
        listbox.setModel(new OspedalePagingListModel(pageSize, entities));
        setTotalNumberOfItemsForDisplay(entities.size());
    }

    public void refreshListbox() {
        OspedalePagingListModel listModel = (OspedalePagingListModel) listbox.getModel();
        listModel.refresh();
    }

    public void export() {
        List<?> entities = ViewUtil.getSelectedItems(listbox);
        if (UtilValidator.isEmpty(entities))
            entities = getAllRelevantData();
        exporter.export(entities, fields, labels, exportFileName);
    }

    public void deleteClicked() throws InterruptedException {
        final List<?> toBeDeleteds = ViewUtil.getSelectedItems(listbox);
        if (UtilValidator.isEmpty(toBeDeleteds)) {
            UtilMessagesAndPopups.showError("Please select items to delete");
            return;
        }
        Messagebox.show("Are you sure", "Confirm", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
            public void onEvent(Event event) throws Exception {
                if (event.getData().equals(Messagebox.YES)) {
                    service.delete(toBeDeleteds);
                    loadEntities();
                }
            }
        });
    }

    public void addClicked() throws InterruptedException {
        isEditMode = false;
        navigate(addPageUrl, addModePopup, null);
    }

    public void addClicked(List listModel) throws InterruptedException {
        isEditMode = false;
        Map<String,Object> map = new HashedMap();
        map.put("listModel", listModel);
        map.put("listbox", listbox);
        navigate(addPageUrl, addModePopup, map);
    }

    public void activateClicked() {
        final List<?> toBeActivateds = ViewUtil.getSelectedItems(listbox);
        if (UtilValidator.isEmpty(toBeActivateds)) {
            UtilMessagesAndPopups.displayError("Please select items to activate");
            return;
        }
        service.activate(toBeActivateds);
        refreshListbox();
        UtilMessagesAndPopups.showSuccess();
    }

    public void deActivateClicked(String reason) {
        final List<?> toBeDeActivateds = ViewUtil.getSelectedItems(listbox);
        if (UtilValidator.isEmpty(toBeDeActivateds)) {
            UtilMessagesAndPopups.displayError("Please select items to deactivate");
            return;
        }
        service.deActivate(toBeDeActivateds, reason);
        refreshListbox();
        UtilMessagesAndPopups.showSuccess();
    }

    public void advancedSearchClicked() {
        if (UtilValidator.isNotEmpty(advancedSearchPageUrl))
            navigate(advancedSearchPageUrl, true, null);
    }

    private Window navigate(String urlKey, Boolean popup, Map<String, Object> args) {
        if (Boolean.TRUE.equals(popup)) {
            Window windowHandle = Navigation.navigateToModalWindow(urlKey, args);
            windowHandle.addEventListener("onDetach", refreshGridListener);
            return windowHandle;
        }
        Navigation.navigate(urlKey, args, containerName);
        return null;
    }

    public List<?> getLastUpdatedEntities() {
        return service.getLatestUpdateds(this.entityClass, HISTORY_FETCH_SIZE);
    }

    public void printClicked() throws SuspendNotAllowedException, InterruptedException {
        Sessions.getCurrent().setAttribute("com.nzion.homegrid.printlist", this);
        Executions.getCurrent().sendRedirect("/home-grid-printlist.zul", "_blank");
    }

    public List<?> getAllRelevantData() {
        OspedalePagingListModel listModel = (OspedalePagingListModel) listbox.getModel();
        List<?> data = listModel.getAll();
        return data == null ? Collections.emptyList() : data;
    }

    public void setExporter(DataExporter exporter) {
        this.exporter = exporter;
    }

    public String[] getFields() {
        return fields;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        try {
            this.entityClass = Class.forName(entityClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (UtilValidator.isEmpty(exportFileName))
            exportFileName = this.entityClass.getSimpleName() + "s.csv";
    }

    public void setFields(String commaCompositedFields) {
        commaCompositedFields = commaCompositedFields.replaceAll(" ", "");
        this.fields = commaCompositedFields.split(",");
        if (searchfields == null)
            searchfields = fields;
        if (labels != null)
            return;
        labels = new String[fields.length];
        for (int i = 0; i < fields.length; ++i)
            labels[i] = UtilDisplay.camelcaseToUiForCopositeFiedl(fields[i]);
    }

    public void setSearchfields(String commaCompositedFields) {
        if (commaCompositedFields == null) return;
        commaCompositedFields = commaCompositedFields.replaceAll(" ", "");
        this.searchfields = commaCompositedFields.split(",");
    }

    private class DefaultListItemRenderer implements ListitemRenderer {
        public void render(Listitem item, Object data, int index) throws Exception {
            for (int i = 0; i < fields.length; ++i) {
                String fieldName = fields[i];
                if("Active".equals(fieldName))continue;
                Object object = UtilReflection.getNestedFieldValue(data, fieldName);
                Listcell cell = new Listcell();
                String cellLabel = object == null ? "" : "" + object;
                cell.setLabel(cellLabel);
                cell.setParent(item);
            }
            Object object = UtilReflection.getNestedFieldValue(data, "Active");
            if (object instanceof Boolean) {
                Boolean objVal = (Boolean) object;
                if (!objVal.booleanValue())
                    item.setSclass("inactive");
            }
        }
    }

    public Comparator<?> getAscComparator(int columnIndex) {
        return new AscComparator(columnIndex);
    }

    public Comparator<?> getDescComparator(int columnIndex) {
        return new DescComparator(columnIndex);
    }

    public class AscComparator implements Comparator<Object> {
        private final String fieldName;

        public AscComparator(int columnPosition) {
            this.fieldName = fields[columnPosition];
        }

        public AscComparator(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public int compare(Object o1, Object o2) {
            Object field1 = UtilReflection.getNestedFieldValue(o1, fieldName);
            Object field2 = UtilReflection.getNestedFieldValue(o2, fieldName);
            String field1String = field1 == null ? "" : field1.toString();
            String field2String = field2 == null ? "" : field2.toString();
            return String.CASE_INSENSITIVE_ORDER.compare(field1String, field2String);
        }
    }

    public class DescComparator implements Comparator<Object> {
        private final String fieldName;

        public DescComparator(int columnPosition) {
            this.fieldName = fields[columnPosition];
        }

        public DescComparator(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public int compare(Object o1, Object o2) {
            Object field1 = UtilReflection.getNestedFieldValue(o1, fieldName);
            Object field2 = UtilReflection.getNestedFieldValue(o2, fieldName);
            String field1String = field1 == null ? "" : field1.toString();
            String field2String = field2 == null ? "" : field2.toString();
            return String.CASE_INSENSITIVE_ORDER.compare(field2String, field1String);
        }
    }

    public Listbox getListbox() {
        return listbox;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public List<? extends Object> getData(int start, int pageSize) {
        return searchService.getAllIncludingInactivesPageWise(entityClass, pageSize, start, defaultSortColumn, defaultSortDesc);
    }
}