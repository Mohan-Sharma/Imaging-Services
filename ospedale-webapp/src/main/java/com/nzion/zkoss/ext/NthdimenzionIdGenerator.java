package com.nzion.zkoss.ext;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.sys.IdGenerator;

public class NthdimenzionIdGenerator implements IdGenerator {

    @Override
    public String nextComponentUuid(Desktop desktop, Component comp, ComponentInfo compInfo) {
        int i = Integer.parseInt(desktop.getAttribute("Id_Num").toString());
        i++;// Start from 1
        desktop.setAttribute("Id_Num", String.valueOf(i));
        return "zk_comp_" + i;
    }

    public String nextDesktopId(Desktop desktop) {
        if (desktop.getAttribute("Id_Num") == null) {
            String number = "0";
            desktop.setAttribute("Id_Num", number);
        }
        return null;
    }

    public String nextPageUuid(Page page) {
	return null;
	}

}
