package com.nzion.zkoss.composer.emr;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Textbox;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;

import com.nzion.domain.Patient;
import com.nzion.domain.Provider;
import com.nzion.domain.Speciality;
import com.nzion.domain.billing.AcctgTransTypeEnum;
import com.nzion.domain.billing.AcctgTransactionEntry;
import com.nzion.domain.billing.DebitCreditEnum;
import com.nzion.util.UtilValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class AcctgSearchController{
	private static final long serialVersionUID = 1L;

	
	private Long providerId;
	
	private Long patientId;
	
	private Long encounterId;
	
	private Long invoiceId;
	
	private AcctgTransTypeEnum acctgTransTypeEnum;
	
	private String ipNumber;

    @Wire("#footer")
    private Footer footer;

    @Wire("#acctgListGrid")
    private Grid grid;

    @Wire
    private HtmlMacroComponent providerLookUpBox;
    
    @Wire
    private HtmlMacroComponent patientLookUpBox;
    
    private List<AcctgTransactionEntry> acctgTransactionEntries;

    @Init
    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, true);
    }

    @Command("Search")
    @NotifyChange("acctgTransactionEntries")
    public void search(@BindingParam("fromDate")Datebox fromDate,@BindingParam("thruDate")Datebox thruDate,@BindingParam("extPatientCheckbox")Checkbox extPatientCheckbox){
	/*acctgTransactionEntries = soapNoteService.getAcctgTransEntryByCriteria(fromDate.getValue(),thruDate.getValue(),providerId, patientId, encounterId, invoiceId, ipNumber, acctgTransTypeEnum,extPatientCheckbox.isChecked());
		 com.nzion.service.common.CommonCrudService commonCrudService = com.nzion.util.Infrastructure.getSpringBean("commonCrudService");
        BigDecimal totalCredits = BigDecimal.ZERO;
        BigDecimal totalDebits = BigDecimal.ZERO;
        for(AcctgTransactionEntry entry : acctgTransactionEntries){
        	if(entry.getPatient()!=null){
        	Patient patient = entry.getPatient();
        	patient = commonCrudService.getById(Patient.class, patient.getId());
        	}
         	Provider provider = entry.getProvider();
         	Set<Speciality> specialities = provider!=null?provider.getSpecialities():new HashSet<Speciality>();
         	if( com.nzion.util.UtilValidator.isNotEmpty(specialities) && specialities.size() ==1){
         		entry.setSpeciality(specialities.iterator().next());
         	}
            if(DebitCreditEnum.CREDIT.equals(entry.getDebitOrCredit()))
                totalCredits=totalCredits.add(entry.getAmount());
            else if(DebitCreditEnum.DEBIT.equals(entry.getDebitOrCredit())){
                totalDebits=totalDebits.add(entry.getAmount());
            }
        }
        int colspan = grid.getColumns().getChildren().size();
        footer.getChildren().clear();
        footer.setSpan(colspan);
        Div div = new Div();
        div.setStyle("text-align:right");
        div.setParent(footer);
        Label l1 = new Label(" Total Credits = "+totalCredits );
        l1.setStyle("font-size:14px");
        l1.setParent(div);
        Separator sep = new Separator();
        sep.setParent(div);
        Label l2 = new Label("  Total Debits  = "+totalDebits);
        l2.setStyle("font-size:14px");
        l2.setParent(div);
*/	}



	@Command("Reset")
    @NotifyChange({"acctgTransactionEntries","acctgTransTypeEnum"})
    public void reset(@BindingParam("fromDate")Datebox fromDate,@BindingParam("thruDate")Datebox thruDate,@BindingParam("ipNumberTxt")org.zkoss.zul.Textbox ipNumberTxt,@BindingParam("transactionTypeEnumCombobox")Combobox transactionTypeEnumCombobox){
/*    	fromDate.setValue(null);
    	thruDate.setValue(null);
    	ipNumberTxt.setValue(null);
    	providerLookUpBox.recreate();
    	patientLookUpBox.recreate();
    	this.acctgTransTypeEnum = null;
*/	}
    


	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getEncounterId() {
		return encounterId;
	}

	public void setEncounterId(Long encounterId) {
		this.encounterId = encounterId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	
	public AcctgTransTypeEnum getAcctgTransTypeEnum() {
		return acctgTransTypeEnum;
	}

	public void setAcctgTransTypeEnum(AcctgTransTypeEnum acctgTransTypeEnum) {
		this.acctgTransTypeEnum = acctgTransTypeEnum;
	}


	public String getIpNumber() {
		return ipNumber;
	}

	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}

	public List<AcctgTransactionEntry> getAcctgTransactionEntries() {
		return acctgTransactionEntries;
	}

	public void setAcctgTransactionEntries(
			List<AcctgTransactionEntry> acctgTransactionEntries) {
		this.acctgTransactionEntries = acctgTransactionEntries;
	}
	
}



