package com.nzion.zkoss.composer;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import com.nzion.domain.PasswordPolicy;
import com.nzion.domain.Practice;
import com.nzion.service.PasswordPolicyService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;

public class PasswordPolicyController extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;
	private PasswordPolicy passwordPolicy;
    private Practice practice;

	private PasswordPolicyService passwordPolicyService;

	public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
	this.passwordPolicyService = passwordPolicyService;
	}

	public Practice getPractice() {
	return practice;
	}

	public PasswordPolicy getPasswordPolicy() {
	return passwordPolicy;
	}

	public void setPasswordPolicy(PasswordPolicy passwordPolicy) {
	this.passwordPolicy = passwordPolicy;
	}

	public void onClick$Save(Event event) {
	passwordPolicyService.saveOrUpdate(passwordPolicy);
	UtilMessagesAndPopups.showSuccess("Please logoff and login again for the new setting to take effect");
	}

	@Override
	public void doAfterCompose(Component component) throws Exception {
	component.setAttribute("vo", passwordPolicy);
	super.doAfterCompose(component);
	}

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
	practice = Infrastructure.getPractice();
	passwordPolicy = passwordPolicyService.getEffectivePasswordPolicy();
	return super.doBeforeCompose(page, parent, compInfo);
	}
}