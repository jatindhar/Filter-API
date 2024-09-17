package com.jatin.CoOp.Filter.Api;

import java.util.List;

public class FilterValuesResponse {

    private List<String> agreementId;
    private List<String> agreementTitle;
    //private List<String> disputeReason;
    private List<String> vendorCode;
    private List<String> invoiceNumber;
    private List<String> po;
    private List<String> asin;

    public FilterValuesResponse() {

    }
    public FilterValuesResponse(List<String> agreementId, List<String> agreementTitle, List<String> vendorCode,
                                List<String> invoiceNumber, List<String> asin, List<String> po) {
        this.agreementId = agreementId;
        this.agreementTitle = agreementTitle;
        this.vendorCode = vendorCode;
       // this.disputeReason = disputeReason;
        this.invoiceNumber = invoiceNumber;
        this.asin = po;
        this.po = asin;
    }
    public void setAgreementId(List<String> agreementId) {
        this.agreementId = agreementId;
    }

    public void setAgreementTitle(List<String> agreementTitle) {
        this.agreementTitle = agreementTitle;
    }

//    public void setDisputeReason(List<String> disputeReason) {
//        this.disputeReason = disputeReason;
//    }

    public void setVendorCode(List<String> vendorCode) {
        this.vendorCode = vendorCode;
    }

    public void setInvoiceNumber(List<String> invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setAsin(List<String> asin) {
        this.asin = asin;
    }

    public void setPo(List<String> po) {
        this.po = po;
    }

    public List<String> getAgreementId() {
        return agreementId;
    }

    public List<String> getAgreementTitle() {
        return agreementTitle;
    }

//    public List<String> getDisputeReason() {
//        return disputeReason;
//    }

    public List<String> getVendorCode() {
        return vendorCode;
    }

    public List<String> getInvoiceNumber() {
        return invoiceNumber;
    }

    public List<String> getPo() {
        return po;
    }

    public List<String> getAsin() {
        return asin;
    }
}
