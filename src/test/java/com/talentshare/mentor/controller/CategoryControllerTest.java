package com.talentshare.mentor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.talentshare.mentor.dto.ResponseDetails;
import com.talentshare.mentor.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;

public class CategoryControllerTest {

	@Mock
    private CategoryService categoryService;
    
    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowAllCategories() {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);

        ResponseDetails expectedResponse = new ResponseDetails();
        expectedResponse.setStatus(HttpStatus.OK.value());
        expectedResponse.setMessage("Success");
        expectedResponse.setPath("/get-All-categories");
        expectedResponse.setData("List of categories");

        when(categoryService.getAllCategory(httpRequest)).thenReturn(expectedResponse);

        ResponseEntity<ResponseDetails> responseEntity = categoryController.showAllCategories(httpRequest);
        ResponseDetails result = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, result);
    }
}

