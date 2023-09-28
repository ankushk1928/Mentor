package com.talentshare.mentor.controller;

import com.talentshare.mentor.model.Skill;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talentshare.mentor.dto.ResponseDetails;
import com.talentshare.mentor.service.SkillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/skill")
@CrossOrigin(origins="*")

public class SkillController {

	@Autowired
	SkillService skillService;

	@Operation(
			summary="Retrieves list of skills",
			description="Retrives all the skills from Database",
			responses= {
					@ApiResponse(
							description = "Valid User",
							responseCode = "200"
							),
					@ApiResponse(
							description = "Unauthorised User",
							responseCode = "400"
							),
					@ApiResponse(
							description = "Internal Server Error",
							responseCode = "500"
							)
			})
	@GetMapping("/get-all-skills")
	public ResponseEntity<ResponseDetails> showAllMentor( HttpServletRequest httpRequest ){
  log.info("Skill controller called");
		ResponseDetails response= skillService.getAllSkills(httpRequest);

		return new ResponseEntity<>(response,HttpStatus.OK);

	}
}
