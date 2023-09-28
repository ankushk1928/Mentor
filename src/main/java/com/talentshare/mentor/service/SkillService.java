package com.talentshare.mentor.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.talentshare.mentor.constants.MessageConstants;
import com.talentshare.mentor.dto.ResponseDetails;
import com.talentshare.mentor.exception.ResourceNotFoundException;
import com.talentshare.mentor.model.Skill;
import com.talentshare.mentor.repository.SkillRepository;
import com.talentshare.mentor.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class SkillService {

	@Autowired
	SkillRepository skillRepository;

	@Autowired
	private CommonUtil commonUtil;

	public ResponseDetails getAllSkills(HttpServletRequest httpRequest) {

		List<Skill> skills = skillRepository.findAll();
    log.info("skill service" +skills.toString());

		if(skills.isEmpty()) {
			throw new ResourceNotFoundException(MessageConstants.SKILL_NOT_FOUND);
		}
		else {
			ResponseDetails response= commonUtil.prepareResponse(MessageConstants.SUCESS, HttpStatus.OK.value(), httpRequest.getRequestURI(), skills);
			return response;
		}
	}
}
