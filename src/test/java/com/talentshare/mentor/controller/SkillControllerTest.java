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
import com.talentshare.mentor.service.SkillService;

import jakarta.servlet.http.HttpServletRequest;

public class SkillControllerTest {

	@Mock
    private SkillService skillService;
	
	@InjectMocks
    private SkillController skillController;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowAllSkills() {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);

        ResponseDetails expectedResponse = new ResponseDetails();
        expectedResponse.setStatus(HttpStatus.OK.value());
        expectedResponse.setMessage("Success");
        expectedResponse.setPath("/get-All-skills");
        expectedResponse.setData("List of skills");

        when(skillService.getAllSkills(httpRequest)).thenReturn(expectedResponse);

        ResponseEntity<ResponseDetails> responseEntity = skillController.showAllMentor(httpRequest);
        ResponseDetails result = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, result);
    }
}

