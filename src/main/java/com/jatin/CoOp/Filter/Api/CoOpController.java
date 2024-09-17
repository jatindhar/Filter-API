package com.jatin.CoOp.Filter.Api;


	
	import jakarta.validation.Valid;
	import org.springframework.web.bind.annotation.*;
	import java.util.concurrent.Callable;

	@RestController
	@RequestMapping(CoOpController.RESOURCE_PATH)
	public class CoOpController {

	    public static final String RESOURCE_PATH = "/summary/coop";
	    public static final String FILTER_API = "/filter-value";

	    private final CoOpService coOpService;

	    public CoOpController(CoOpService coOpService) {
	        this.coOpService = coOpService;
	    }

	    @PostMapping(FILTER_API)
	    public Callable<FilterValuesResponse> getFilterValues(@Valid @RequestBody FilterValueRequest filterValueRequest) {
	        return () -> coOpService.findFilterValues(filterValueRequest);
	    }

	}


