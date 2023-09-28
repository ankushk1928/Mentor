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
import com.talentshare.mentor.model.Skill;
import com.talentshare.mentor.repository.SkillRepository;
import com.talentshare.mentor.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

public class SkillServiceTest {

	@Mock
    private SkillRepository skillRepository;
    
    @Mock
    private CommonUtil commonUtil;
    
    @InjectMocks
    private SkillService skillService;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSkills_Success() {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);

        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill(1,"Skill1"));
        skills.add(new Skill(2, "Skill2"));

        when(skillRepository.findAll()).thenReturn(skills);
        when(httpRequest.getRequestURI()).thenReturn("/api/v1/mentor/skill");

        ResponseDetails expectedResponse = new ResponseDetails();
        expectedResponse.setStatus(HttpStatus.OK.value());
        expectedResponse.setMessage("Success");
        expectedResponse.setPath("/skills");
        expectedResponse.setData(skills);

        when(commonUtil.prepareResponse(anyString(), anyInt(), anyString(), any())).thenReturn(expectedResponse);

        ResponseDetails result = skillService.getAllSkills(httpRequest);

        assertEquals(expectedResponse, result);
    }

    @Test
    public void testGetAllSkills_NoSkillsFound() {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);

        when(skillRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> skillService.getAllSkills(httpRequest));
    }
}

