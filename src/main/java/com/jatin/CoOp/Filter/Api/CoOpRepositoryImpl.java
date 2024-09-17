package com.jatin.CoOp.Filter.Api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Supplier;

@Repository
public class CoOpRepositoryImpl implements CoOpRepository {

    private final NamedParameterJdbcTemplate snowflakeNamedJdbcTemplate;

    public CoOpRepositoryImpl(@Qualifier("${spring.datasource.snowflake.namedJdbcTemplate}") NamedParameterJdbcTemplate snowflakeNamedJdbcTemplate) {
        this.snowflakeNamedJdbcTemplate = snowflakeNamedJdbcTemplate;
    }

    @Override
    public FilterValuesResponse findFilter(FilterValueRequest filterValueRequest) {
        StringBuilder sql = new StringBuilder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        // Method to get the complete SQL query

        sql.append(CoOpQueryBuilder.selectColumnsForFilter())
                .append(CoOpQueryBuilder.fromClause())
                .append(CoOpQueryBuilder.joinClauseForFilter())
                .append(CoOpQueryBuilder.whereClauseForFilter());


        addFilterCondition(sql, namedParameters, filterValueRequest.getAccountId(),
                CoOpQueryBuilder::addBUID, CoOpConstants.BUSINESS_UNIT_ID);

        addDateRangeCondition(sql, namedParameters, filterValueRequest.getCreationDateFrom(), filterValueRequest.getCreationDateTo());

        addFilterCondition(sql, namedParameters, filterValueRequest.getAgreementId(),
                CoOpQueryBuilder::addAgreementIdConditionForFilter, CoOpConstants.AGREEMENT_ID_NEW);

        addFilterCondition(sql, namedParameters, filterValueRequest.getAgreementTitle(),
                CoOpQueryBuilder::addAgreementTitleConditionForFilter, CoOpConstants.AGREEMENT_TITLE_NEW);

        addFilterCondition(sql, namedParameters, filterValueRequest.getVendorCode(),
                CoOpQueryBuilder::addVendorCodeConditionForFilter, CoOpConstants.VENDOR_CODE);

        addFilterCondition(sql, namedParameters, filterValueRequest.getPo(),
                CoOpQueryBuilder::addPurchaseOrderConditionForFilter, CoOpConstants.PO);

        addFilterCondition(sql, namedParameters, filterValueRequest.getAsin(),
                CoOpQueryBuilder::addAsinConditionForFilter, CoOpConstants.ASIN);

        sql.append(CoOpQueryBuilder.closeJoinClause());

        List<FilterValuesResponse> results = snowflakeNamedJdbcTemplate.query(sql.toString(), namedParameters, new CoopRowMapper());
        return aggregateFilterResults(results);
    }

    private void addFilterCondition(StringBuilder sql, MapSqlParameterSource namedParameters, List<String> filterValues,
                                    Supplier<String> queryBuilderFunction, String param) {

        Optional<List<String>> filterValuesOpt = Optional.ofNullable(filterValues).filter(value -> !value.isEmpty());
        if (filterValuesOpt.isPresent()) {
            sql.append(queryBuilderFunction.get());
            namedParameters.addValue(param, filterValues);
        }
    }

    private void addDateRangeCondition(StringBuilder sql, MapSqlParameterSource namedParameters, String creationDateFrom, String creationDateTo) {

        Optional<String> creationDateFromOpt = Optional.ofNullable(creationDateFrom).filter(date -> !date.isEmpty());
        Optional<String> creationDateToOpt = Optional.ofNullable(creationDateTo).filter(date -> !date.isEmpty());
        if (creationDateFromOpt.isPresent() && creationDateToOpt.isPresent()) {
            sql.append(CoOpQueryBuilder.addDateRangeConditionForFilter());
            namedParameters.addValue(CoOpConstants.CREATION_DATE_FROM, creationDateFrom);
            namedParameters.addValue(CoOpConstants.CREATION_DATE_TO, creationDateTo);
        }
    }

    private FilterValuesResponse aggregateFilterResults(List<FilterValuesResponse> results) {
        FilterValuesResponse filterValuesResponse = new FilterValuesResponse();
        Set<String> agreementIdSet = new HashSet<>();
        Set<String> agreementTitleSet = new HashSet<>();
        Set<String> asinSet = new HashSet<>();
        Set<String> invoiceNumberSet = new HashSet<>();
        Set<String> poSet = new HashSet<>();
        Set<String> vendorCodeSet = new HashSet<>();

        results.forEach(result -> {
            Optional.ofNullable(result.getAgreementId()).ifPresent(agreementIdSet::addAll);
            Optional.ofNullable(result.getAgreementTitle()).ifPresent(agreementTitleSet::addAll);
            Optional.ofNullable(result.getAsin()).ifPresent(asinSet::addAll);
            Optional.ofNullable(result.getInvoiceNumber()).ifPresent(invoiceNumberSet::addAll);
            Optional.ofNullable(result.getPo()).ifPresent(poSet::addAll);
            Optional.ofNullable(result.getVendorCode()).ifPresent(vendorCodeSet::addAll);
        });

        filterValuesResponse.setAgreementId(new ArrayList<>(agreementIdSet));
        filterValuesResponse.setAgreementTitle(new ArrayList<>(agreementTitleSet));
        filterValuesResponse.setAsin(new ArrayList<>(asinSet));
        filterValuesResponse.setInvoiceNumber(new ArrayList<>(invoiceNumberSet));
        filterValuesResponse.setPo(new ArrayList<>(poSet));
        filterValuesResponse.setVendorCode(new ArrayList<>(vendorCodeSet));

        return filterValuesResponse;
    }

    public static class CoopRowMapper implements RowMapper<FilterValuesResponse> {
        @Override
        public FilterValuesResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new FilterValuesResponse(
                    Collections.singletonList(rs.getString(CoOpConstants.AGREEMENT_ID)),
                    Collections.singletonList(rs.getString(CoOpConstants.AGREEMENT_TITLE)),
                    Collections.singletonList(rs.getString(CoOpConstants.ASIN)),
                    Collections.singletonList(rs.getString(CoOpConstants.INVOICE_NUMBER)),
                    Collections.singletonList(rs.getString(CoOpConstants.PURCHASE_ORDER)),
                    Collections.singletonList(rs.getString(CoOpConstants.MANUFACTURER))
            );
        }
    }
}
