package com.talentshare.mentor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.talentshare.mentor.dto.ResponseDetails;
import com.talentshare.mentor.exception.ResourceNotFoundException;
import com.talentshare.mentor.model.Category;
import com.talentshare.mentor.repository.CategoryRepository;
import com.talentshare.mentor.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

public class CategoryServiceTest {

	@Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private CommonUtil commonUtil;
    
    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCategory_Success() {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        when(httpRequest.getRequestURI()).thenReturn("/api/v1/mentor/category");
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1,"Category 1"));
        categories.add(new Category(2,"Category 2"));

        when(categoryRepository.findAll()).thenReturn(categories);

        ResponseDetails expectedResponse = new ResponseDetails();
        expectedResponse.setStatus(HttpStatus.OK.value());
        expectedResponse.setMessage("Success");
        expectedResponse.setPath("/categories");
        expectedResponse.setData(categories);

        when(commonUtil.prepareResponse(anyString(), anyInt(), anyString(), any())).thenReturn(expectedResponse);

        ResponseDetails result = categoryService.getAllCategory(httpRequest);

        assertEquals(expectedResponse, result);
    }

    @Test
    public void testGetAllCategory_NoCategoriesFound() {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);

        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getAllCategory(httpRequest));
    }
}
