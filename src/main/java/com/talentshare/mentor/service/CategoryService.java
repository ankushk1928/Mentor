package com.talentshare.mentor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.talentshare.mentor.constants.MessageConstants;
import com.talentshare.mentor.dto.ResponseDetails;
import com.talentshare.mentor.exception.ResourceNotFoundException;
import com.talentshare.mentor.model.Category;
import com.talentshare.mentor.repository.CategoryRepository;
import com.talentshare.mentor.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CommonUtil commonUtil;
	
	public ResponseDetails getAllCategory(HttpServletRequest httpRequest) {
		
		List<Category> categories = categoryRepository.findAll();
		if(categories.isEmpty()) {
			throw new ResourceNotFoundException(MessageConstants.CATEGORY_NOT_FOUND);
		}
		else {
			ResponseDetails response = commonUtil.prepareResponse(MessageConstants.SUCESS, HttpStatus.OK.value(), httpRequest.getRequestURI(), categories);
			return response;
		}
	}
}
