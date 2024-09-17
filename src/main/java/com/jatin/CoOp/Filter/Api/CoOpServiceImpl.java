package com.jatin.CoOp.Filter.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CoOpServiceImpl implements CoOpService {

    @Autowired
    private CoOpRepository coOpRepository;

    @Override
    public FilterValuesResponse findFilterValues(FilterValueRequest filterValueRequest) {
        return coOpRepository.findFilter(filterValueRequest);
    }
}
