package com.nzion.zkoss.ext;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treeitem;

import java.util.*;

import com.nzion.security.PermissionGroup;
import com.nzion.security.SecurityGroup;
import com.nzion.util.UtilValidator;

public class PermissionGroupTreeModelAndRenderer extends AbstractTreeModel implements TreeitemRenderer {

    private static final long serialVersionUID = 1L;

    private SecurityGroup secGroup;

    public PermissionGroupTreeModelAndRenderer(PermissionGroup root) {
        super(root);
    }

    public Object getChild(Object parent, int index) {
        return ((PermissionGroup) parent).getChildren().get(index);
    }

    public int getChildCount(Object parent) {
        return ((PermissionGroup) parent).getChildren().size();
    }

    public boolean isLeaf(Object node) {
        return UtilValidator.isEmpty(((PermissionGroup) node).getChildPermissionGroups());
    }

    public void render(Treeitem item, Object data, int index) {
        PermissionGroup pg = (PermissionGroup) data;
        //		item.setSelected(secGroup.getGrantedSecurityPermissions().contains(data));
        item.setLabel(pg.getName());
        item.setValue(pg);
        selectHierarchy(item, secGroup.getGrantedSecurityPermissions().contains(data));
    }

    public void selectHierarchy(Treeitem item, boolean value) {
        if (!value || item == null)
            return;
        item.setSelected(value);
        item.setOpen(value);
        selectHierarchy(item.getParentItem(), value);
    }

    public void setSecGroup(SecurityGroup secGroup) {
        this.secGroup = secGroup;
    }

}
