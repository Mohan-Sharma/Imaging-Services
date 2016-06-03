package com.nzion.zkoss.composer;

import com.nzion.domain.Employee;
import com.nzion.domain.Practice;
import com.nzion.domain.Roles;
import com.nzion.domain.UserLogin;
import com.nzion.service.PracticeService;
import com.nzion.service.UserLoginService;
import com.nzion.service.exceptions.ServiceException;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import com.nzion.view.PracticeViewObject;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import java.util.Date;

public class PracticeSetupController extends OspedaleAutowirableComposer {

    private static final long serialVersionUID = 1L;
    PracticeService practiceService;
    UserLoginService userLoginService;
    PracticeViewObject practiceViewObject = null;
    Practice practice;

    public Practice getPractice() {
        return practice;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        comp.setAttribute("vo", practiceViewObject);
    }

    public void savePractice() throws ServiceException, InterruptedException {
        if (practiceViewObject.getPractice().getId() != null) {
            practiceService.updatePractice(practiceViewObject);
            UtilMessagesAndPopups.showSuccess();
            return;
        }
        if (userLoginExists(practiceViewObject.getUserLogin().getUsername())) {
            UtilMessagesAndPopups.showError("User Name" + "  \"" + practiceViewObject.getUserLogin().getUsername() + "  \"" + "already exist");
            return;
        }

        try {
            UserLogin newUserLogin = practiceViewObject.getUserLogin();
            if (newUserLogin == null || UtilValidator.isEmpty(newUserLogin.getUsername())) {
                Messagebox.show("User name cannot be blank.");
                return;
            }
            practiceViewObject.setSuperAdmin(true);
            practiceService.save(practiceViewObject);
            UtilMessagesAndPopups.showSuccess();
        } catch (Throwable t) {
            t.printStackTrace();
            UtilMessagesAndPopups.showError(t.getMessage());
        }
    }


    public boolean userLoginExists(String userName) {
        try {
            userLoginService.getUserByUsername(userName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public PracticeViewObject getPracticeViewObject() {
        return practiceViewObject;
    }

    public void setPracticeViewObject(PracticeViewObject practiceViewObject) {
        this.practiceViewObject = practiceViewObject;
    }

    public void setPracticeService(PracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @Override
    public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
        Practice practice = (Practice) Executions.getCurrent().getArg().get("entity");
        if (practice == null)
            practice = Infrastructure.getPractice();
        UserLogin userLogin = null;
        if (practice != null)
            practice = practiceService.getPractice(practice.getId());
        if (practice == null) {
            practice = new Practice();
            userLogin = new UserLogin();
            Employee employee = new Employee();
            employee.setAccountNumber("SuperAdmin");
            userLogin.addRole(Roles.ADMIN);
            userLogin.setPwdChangedTime(new Date());
            userLogin.setPerson(employee);
        } else
            userLogin = practice.getAdminUserLogin();

        practiceViewObject = new PracticeViewObject(practice, userLogin);
        return super.doBeforeCompose(page, parent, compInfo);
    }
}