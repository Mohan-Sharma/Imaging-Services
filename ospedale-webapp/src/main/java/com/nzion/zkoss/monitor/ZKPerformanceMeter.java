package com.nzion.zkoss.monitor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.sys.ExecutionsCtrl;
import org.zkoss.zk.ui.util.PerformanceMeter;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import com.nzion.diagnostic.ZKRequestMonitor;
import com.nzion.util.Infrastructure;

public class ZKPerformanceMeter implements PerformanceMeter {

	/**
	 * Returns current request statistics object set by ZK performance meter
	 * Returns null, if no request statistics object is availabele
	 */
	public static ZKRequestMonitor getCurrentRequestStatistics() {
	((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getSession();
	return (Executions.getCurrent() == null) ? null : (ZKRequestMonitor) Executions.getCurrent().getDesktop()
			.getSession().getAttribute("RequestStatisticsCurrentObject");
	}

	/**
	 * Returns actual statistics object identified by exection id
	 * At first statistics call in request new statistics object is created and set into RequestStatisticsMap session variable. Upon subsequent
	 * calls in request the same object is returned.
	 *
	 * This object is set to RequestStatisticsCurrentObject session variable as well for use of other statistics methods. Use getCurrentRequestStatistics()
	 * static method to use this session variable.
	 *
	 * @param requestId request identifier
	 * @param exec ZK exekuce
	 * @return objekt statistik
	 */
	@SuppressWarnings("unchecked")
	public static ZKRequestMonitor getCurrentRequestStatistics(String requestId, Execution exec) {
	ZKRequestMonitor ret;

	Map<String, ZKRequestMonitor> map = (Map<String, ZKRequestMonitor>) exec.getDesktop().getSession().getAttribute(
			"RequestStatisticsMap");

	if (map == null) {
		map = new HashMap<String, ZKRequestMonitor>();
		exec.getDesktop().getSession().setAttribute("RequestStatisticsMap", map);
	}

	if (map.containsKey(requestId)) {
		ret = map.get(requestId);
	} else {
		ret = new ZKRequestMonitor(requestId, exec.getDesktop().getId(), exec.getDesktop().getRequestPath(),
				Infrastructure.getUserName(), exec.getRemoteAddr());
		map.put(requestId, ret);
	}

	exec.getDesktop().getSession().setAttribute("RequestStatisticsCurrentObject", ret);
	if (ExecutionsCtrl.getCurrentCtrl() != null && ExecutionsCtrl.getCurrentCtrl().getCurrentPage() != null) {
		Label sucessLabel = (Label) ExecutionsCtrl.getCurrentCtrl().getCurrentPage().getFellowIfAny("successMsg", true);
		if (sucessLabel != null) {
			sucessLabel.setValue(null);
		}
	}
	return ret;
	}

	public void resetMessage() {
	if (ExecutionsCtrl.getCurrentCtrl() != null && ExecutionsCtrl.getCurrentCtrl().getCurrentPage() != null) {
		Div successDiv = (Div) ExecutionsCtrl.getCurrentCtrl().getCurrentPage().getFellowIfAny("msgDiv", true);
		if (successDiv != null) {
            successDiv.setVisible(false);
		}
	}
	}
	
	public void requestStartAtClient(String requestId, Execution exec, long time) {
	resetMessage();
	}

	public void requestCompleteAtClient(String requestId, Execution exec, long time) {
	resetMessage();
	}

	public void requestReceiveAtClient(String requestId, Execution exec, long time) {
	resetMessage();
	}

	public void requestStartAtServer(String requestId, Execution exec, long time) {
	}

	public void requestCompleteAtServer(String requestId, Execution exec, long time) {
	}

	private boolean isStatisticsEnabled(Execution exec) {
	return !exec.getDesktop().getRequestPath().contains("ZKMonitor");
	}
}