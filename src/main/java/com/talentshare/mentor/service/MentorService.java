package com.talentshare.mentor.service;

import com.talentshare.mentor.constants.MessageConstants;
import com.talentshare.mentor.dto.ResponseDetails;
import com.talentshare.mentor.exception.*;
import com.talentshare.mentor.model.Mentor;
import com.talentshare.mentor.model.MentorsData;
import com.talentshare.mentor.repository.MentorRepository;
import com.talentshare.mentor.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MentorService {
  @Autowired
  private MentorRepository mentorRepository;

  @Autowired
  private CommonUtil commonUtil;

  @Autowired
  MongoTemplate mongoTemplate;

  public ResponseDetails verifyLogInDetails(String email, String password, HttpServletRequest httpRequest) {
    log.debug("Verifying login details for username " + email);
    Optional<Mentor> existingMentor = mentorRepository.findByEmail(email);
    if (existingMentor.isPresent() && existingMentor.get().isActive() == true) {
      if (commonUtil.matchPassword(password, existingMentor.get().getPassword())) {
        log.info("Login verification successful");
        ResponseDetails responseDetails = commonUtil.prepareResponse(MessageConstants.LOGIN_SUCCESS,
          HttpStatus.OK.value(), httpRequest.getRequestURI(), existingMentor);
        return responseDetails;
      } else {
        log.warn("Password is incorrect");
        throw new WrongCredentialsException(MessageConstants.WRONG_PASSWORD);
      }
    } else {
      log.warn("Username is incorrect");
      throw new WrongCredentialsException(MessageConstants.WRONG_USERNAME);
    }
  }

  public ResponseDetails findMentorBySkills(List<String> skills, HttpServletRequest httpRequest) {
    log.debug("Searching for mentors with skills: {}", skills);
    List<Mentor> mentors = mentorRepository.findBySkillsIn(skills);
    if (mentors.isEmpty()) {
      log.warn("No Mentors found");
      throw new ResourceNotFoundException(MessageConstants.MENTORS_NOT_FOUND + skills.toString());
    } else {
      List<Mentor> mentorsList = mentors.stream().filter(mentor -> mentor.isActive() == true)
        .distinct().collect(Collectors.toList());
      MentorsData mentorsData = new MentorsData(mentorsList.size(), mentorsList);
      log.info(mentorsData.toString());
      ResponseDetails responseDetails = commonUtil.prepareResponse(MessageConstants.SUCESS,
        HttpStatus.OK.value(), httpRequest.getRequestURI(), mentorsData);
      return responseDetails;
    }
  }

  public ResponseDetails getMentorById(int empid, HttpServletRequest httpServletRequest) {
    log.debug("Searching for mentors by empid", +empid);
    Optional<Mentor> mentor1 = mentorRepository.findByEmployeeId(empid);
    if (mentor1.isPresent()) {
      log.info("Mentor fetched successful");
      ResponseDetails responseDetails = commonUtil.prepareResponse(MessageConstants.MENTOR_FOUND,
        HttpStatus.OK.value(), httpServletRequest.getRequestURI(), mentor1);
      return responseDetails;
    } else {
      log.warn("Invalid Mentor");
      throw new InvalidMentorException(MessageConstants.INVALID_MENTOR + empid);
    }
  }

  public ResponseDetails getAllMentors(HttpServletRequest httpServletRequest) {
    log.debug("Searching for all mentors");
    List<Mentor> mentors = mentorRepository.findAll();
    if (mentors.isEmpty()) {
      log.info("No Mentor Found");
      throw new NoMentorsFoundException(MessageConstants.MENTORS_NOT_FOUND);
    } else {
      List<Mentor> activeMentors = mentors.stream()
        .filter(mentor -> mentor.isActive() == true).collect(Collectors.toList());
      MentorsData mentorsData = new MentorsData(activeMentors.size(), activeMentors);
      log.info(mentorsData.toString());
      ResponseDetails responseDetails = commonUtil.prepareResponse("List Found",
        HttpStatus.OK.value(), httpServletRequest.getRequestURI(), mentorsData);
      return responseDetails;
    }
  }

  public ResponseDetails deleteMentor(int empid, HttpServletRequest httpServletRequest) {
    log.debug("Searching for mentor to delete", +empid);
    Optional<Mentor> mentor1 = mentorRepository.findByEmployeeId(empid);
    if (mentor1.isPresent()) {
      log.info("Mentor is present");
      Mentor mentor = mentor1.get();
      mentor.setActive(false);
      Query query = new Query(Criteria.where("employeeId").is(mentor.getEmployeeId()));
      Update updateField = Update.update("isActive", false);
      mongoTemplate.updateFirst(query, updateField, Mentor.class);
      log.info("Mentor deactivated- Deleted Successfully: {}", mentor);// Save the updated mentor with the new status
      ResponseDetails responseDetails = commonUtil.prepareResponse("Deleted Successfully",
        HttpStatus.OK.value(), httpServletRequest.getRequestURI(), mentor1);
      return responseDetails;
    } else {
      log.info(" Mentor not activated");
      throw new InvalidMentorException(MessageConstants.INVALID_MENTOR + empid);
    }
  }

  public ResponseDetails registerMentor(Mentor mentor, HttpServletRequest httpRequest) {
    log.info("registerMentor menthod called!");
    if (mentorRepository.existsByEmployeeId(mentor.getEmployeeId())) {
      throw new EmployeeIdAlreadyRegisteredException("Employee ID " + mentor.getEmployeeId() + " is already registered.");
    }
    if (mentorRepository.existsByEmail(mentor.getEmail())) {
      throw new EmailAlreadyRegisteredException("email " + mentor.getEmail() + " is already registered.");
    }
    mentor.setPassword(commonUtil.encryptPassword(mentor.getPassword()));
    mentor.setActive(true);
    mentor.setCreatedAt(LocalDateTime.now());
    Mentor savedMentor = mentorRepository.save(mentor);
    log.info("Response from Register Service: " + savedMentor);
    String responseMessage = "Mentor " + savedMentor.getFirstName() + " " + savedMentor.getLastName() + " registered successfully.";
    ResponseDetails response = commonUtil.prepareResponse(responseMessage, HttpStatus.OK.value(), httpRequest.getRequestURI(), savedMentor);
    return response;
  }

  public ResponseDetails updateMentor(int empid, Mentor mentor, HttpServletRequest httpRequest) {
    log.info("updateMentor menthod called!");
    Optional<Mentor> existingMentor = mentorRepository.findByEmployeeId(empid);
    if (existingMentor.isPresent() && mentor.getEmployeeId() == empid) {
      mentor.setActive(true);
      mentor.setCreatedAt(LocalDateTime.now());
      Query query = new Query();
      query.addCriteria(Criteria.where("employeeId").is(empid));
      mongoTemplate.findAndReplace(query, mentor);
      Optional<Mentor> updatedMentor = mentorRepository.findByEmployeeId(empid);
      String responseMessage = "Mentor " + mentor.getFirstName() + " " + mentor.getLastName() + " updated.";
      ResponseDetails responseDetails = commonUtil.prepareResponse(responseMessage, HttpStatus.OK.value(), httpRequest.getRequestURI(), updatedMentor);
      return responseDetails;
    } else {
      throw new ResourceNotFoundException("Mentor not found with id : " + empid);
    }
  }
}
