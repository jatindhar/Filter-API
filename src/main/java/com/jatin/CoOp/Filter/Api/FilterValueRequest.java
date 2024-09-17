package com.jatin.CoOp.Filter.Api;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class FilterValueRequest {

    @NotNull(message = "Provide valid Account ID")
    private List<String> accountId;
    private List<String> agreementId;
    private List<String> agreementTitle;
    private List<String> asin;
    private String creationDateFrom;
    private String creationDateTo;
    private List<String> invoiceNumber;
    private List<String> po;
    private List<String> vendorCode;

    public List<String> getAccountId() {
        return accountId;
    }

    public void setAccountId(List<String> accountId) {
        this.accountId = accountId;
    }

    public List<String> getAgreementTitle() {
        return agreementTitle;
    }

    public void setAgreementTitle(List<String> agreementTitle) {
        this.agreementTitle = agreementTitle;
    }

    public List<String> getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(List<String> agreementId) {
        this.agreementId = agreementId;
    }

    public List<String> getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(List<String> invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public List<String> getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(List<String> vendorCode) {
        this.vendorCode = vendorCode;
    }

    public List<String> getPo() {
        return po;
    }

    public void setPo(List<String> po) {
        this.po = po;
    }

    public List<String> getAsin() {
        return asin;
    }

    public void setAsin(List<String> asin) {
        this.asin = asin;
    }

    public String getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(String creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public String getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(String creationDateTo) {
        this.creationDateTo = creationDateTo;
    }
}
