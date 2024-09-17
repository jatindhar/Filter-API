package com.jatin.CoOp.Filter.Api;


import java.util.List;

public class CoOpQueryBuilder {

    public static String selectColumnsForFilter() {
        return "SELECT DISTINCT COG.agreement_id, COG.agreement_title, COG.manufacturer, COG.invoice_number, COG.purchase_order, COG.asin";
    }

    public static String fromClause() {
        return " FROM supply_chain.co_op_gold COG ";
    }

    public static String joinClauseForFilter() {
        return " WHERE COG.invoice_number IN (SELECT DM.invoice_number FROM supply_chain.dispute_management DM " +
                "JOIN supply_chain.vw_account_mapping VM ON DM.vc_account_id = VM.vc_account_id";
    }

    public static String whereClauseForFilter() {
        return " WHERE DM.dispute_type = 'co-op'";
    }

    public static String addDateRangeConditionForFilter() {
        return " AND DM.dispute_date BETWEEN :creationDateFrom AND :creationDateTo";
    }

    public static String addBUID() {
        return " AND VM.business_unit_id IN (:businessUnitId)";

    }

    public static String addAgreementIdConditionForFilter() {
        return  " AND agreement_id IN (:agreementId)";

    }

    public static String addAgreementTitleConditionForFilter() {
        return "AND agreement_title IN (:agreementTitle)";
    }

    public static String addVendorCodeConditionForFilter() {
        return " AND manufacturer IN (:vendorCode)";

    }

    public static String addPurchaseOrderConditionForFilter() {
        return "AND purchase_order IN (:po)";

    }

    public static String addAsinConditionForFilter() {
        return " AND asin IN (:asin)";

    }

    public static String closeJoinClause() {
        return ")";
    }
}



