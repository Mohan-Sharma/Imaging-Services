<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" dir="ltr">
<div style="float:left;width:50%;margin-left:25%">
    <table class="table-bordered" width="100%">
        <caption>Bill Outstanding as of Today</caption>
        <tbody>
        <tr style="background-color:grey;color:#ffffff">
            <th style="text-align:left">Category</th>
            <th  style="text-align:right">Amount</th>
        </tr>
        <#assign patientBillSummary = billingRepository.getOverallInPatientBillSummary()?default()/>
        <#list billingRepository.getOverallInPatientBillSummary() as l>
        <tr>
            <td style="text-align:left">${l[0]?if_exists}</td>
            <td style="text-align:right">${l[1]?if_exists}</td>
        </tr>
        </#list>
        </tbody>
    </table>
    <br/>
</div>

<div style="clear:both"/>
<table width="100%" cellpadding="5" cellspacing="2">
    <tbody>
    <#assign pAdmissionList=pAdmissions?default()/>
    <#list pAdmissionList as patientAdmission>
    <tr>
        <td colspan="2">
            <table width="100%" class="form-horizontal">
                <tr  style="background-color:grey;color:white">
                    <td width="10%" style="text-align:right">Patient:</td>
                    <td width="15%">${patientAdmission.patient.firstName?if_exists} ${patientAdmission.patient.lastName?if_exists}</td>
                    <td width="5%"  style="text-align:right">MRN:</td>
                    <td width="5%">${patientAdmission.patient.accountNumber?if_exists}</td>
                    <td width="5%"  style="text-align:right">Admission No:</td>
                    <td width="5%">${patientAdmission.accountNumber?if_exists}</td>
                    <td width="5%"  style="text-align:right">Ward/Room/Bed:</td>
                    <td width="20%">
                    ${patientAdmission.latestAllocatedBed.ward?if_exists}/${patientAdmission.latestAllocatedBed.bed.room.roomNo?if_exists}/${patientAdmission.latestAllocatedBed.bed?if_exists}
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td width="50%">
            <table width="100%" border="1">
                <thead>
                <tr style="background-color:grey;color:#ffffff">
                    <th style="text-align:left">Category</th>
                    <th  style="text-align:right">Amount</th>
                </tr>
                </thead>
                <tbody>
                    <#assign balanceTotal=0.0>
                    <#assign unpaidInvoiceList = billingRepository.getUnpaidInvoiceSummary(patientAdmission)?default()>
                    <#list unpaidInvoiceList as l>
                    <tr>
                        <td  style="text-align:left">${l[0]?if_exists}</td>
                        <td style="text-align:right">${l[1]?if_exists}</td>
                        <#assign balanceTotal=balanceTotal?default(0.00)+l[1]?default(0.00)>
                    </tr>
                    </#list>
                </tbody>
                <tr>
                    <td  style="text-align:right">Total Balance:</td>
                    <td  style="text-align:right">${balanceTotal?if_exists}</td>
                </tr>
            </table>
        </td>
        <td  width="50%" style="vertical-align:top">
            <table width="100%" border="1">
                <thead>
                <tr style="background-color:grey;color:#ffffff">
                    <th style="text-align:left">Receipt Number</th>
                    <th  style="text-align:right">Payment Date</th>
                    <th  style="text-align:right">Amount</th>
                </tr>
                </thead>
                <tbody>
                    <#assign total=0.0>
                    <#assign paymentReceiveList = billingRepository.getPaymentReceived(patientAdmission)?default()>
                    <#list paymentReceiveList as payment>
                    <tr>
                        <td  style="text-align:left">${payment.receiptNumber?if_exists}</td>
                        <td  style="text-align:left">${payment.paymentDate?if_exists}</td>
                        <td style="text-align:right">${payment.amount.amount?if_exists}</td>
                        <#assign total=total?default(0.00)+payment.amount.amount?default(0.00)>
                    </tr>
                    </#list>
                <tr>
                    <td></td>
                    <td  style="text-align:right">Total Deposit:</td>
                    <td  style="text-align:right">${total?if_exists}</td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>

    <tr>
        <td colspan="2" style="font-size:16px">
            Balance = ${balanceTotal?if_exists} - ${total?if_exists} = ${balanceTotal-total}
        </td>
    </tr>  
    
    
    <tr>
        <#assign invoice = billingRepository.getInvoiceForPatientAdmission(patientAdmission)>
        <td>Invoice No:
            <a href="billingTxnItemInpatient.zul?invoiceId=${invoice.id?if_exists}" target="_new">${invoice.invoiceNumber?if_exists}</a></td>
    </tr>
    <tr>
        <td colspan="2"><hr/></td>
    </tr>
    </#list>
    </tbody>
</table>
</html>