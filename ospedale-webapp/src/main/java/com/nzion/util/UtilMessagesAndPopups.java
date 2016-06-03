package com.nzion.util;

import com.nzion.zkoss.ext.MultiLineMessageBox;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.sys.ExecutionsCtrl;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

/**
 * @author Sandeep Prusty
 *         Apr 28, 2010
 */
public class UtilMessagesAndPopups {

    public static final String TITLE = "Alert";

    public static void showConfirmation(EventListener eventListener) {
        showConfirmation("Do you want to proceed ?", eventListener);
    }

    public static void showConfirmation(String message, EventListener eventListener) {
        confirm(message, TITLE, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, eventListener);
    }

    public static void showSuccess() {
        showSuccess("Action Completed Successfully");
    }

    public static void showSuccess(String message) {
        display(message, Messagebox.OK, Messagebox.INFORMATION);
    }

    public static void showMessage(String message) {
        display(message, Messagebox.OK, Messagebox.EXCLAMATION);
    }

    public static void showError(String message) {
        display(message, MultiLineMessageBox.OK, Messagebox.ERROR);
    }

    public static void displayError(String message) {
        display(message, MultiLineMessageBox.OK, MultiLineMessageBox.ERROR);
    }

    public static void displaySuccess() {
        displaySuccess("Action Completed Successfully");
    }

    public static void displaySuccess(String message) {
        display(message, MultiLineMessageBox.OK, MultiLineMessageBox.INFORMATION);
    }

    public static void confirm(String message, String title, int buttons, String icon, EventListener eventListener) {
        try {
            Messagebox.show(message, title, buttons, icon, eventListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int display(String message, int buttons, String icon) {
        if (icon != null && icon.equals(MultiLineMessageBox.ERROR)) {
            MultiLineMessageBox.doSetTemplate();
            try {
                return MultiLineMessageBox.show(message, TITLE, buttons, icon, true);
            } catch (InterruptedException e) {
                throw new RuntimeException("Contact Administrator", e);
            }
        } else {
            final Label l = (Label) ExecutionsCtrl.getCurrentCtrl().getCurrentPage().getFellow("successMsg", true);
            if(!StringUtils.isEmpty(message))
                l.getParent().setVisible(true);
            l.setValue(message);
            //Clients.evalJavaScript("test()");
            return 0;
        }
    }

    public static void clearMessage() {
        final Label l = (Label) ExecutionsCtrl.getCurrentCtrl().getCurrentPage().getFellow("successMsg", true);
        l.setValue(null);
    }
}