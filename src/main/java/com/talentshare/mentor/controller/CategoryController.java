package com.talentshare.mentor.controller;

import java.awt.print.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentshare.mentor.dto.ResponseDetails;
import com.talentshare.mentor.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins="*")

public class CategoryController {

	@Autowired
	CategoryService categoryService;


	@Operation(summary = "Show All Categories")
	@ApiResponses(value = {

	  @ApiResponse(responseCode = "200", description = "Successfully Retrieved all the Categories",
			  		content = { @Content(mediaType = "application/json",
			  		schema = @Schema(implementation = Book.class)) }),
	  @ApiResponse(responseCode = "400", description = "Bad request. Invalid input data"),
	  @ApiResponse(responseCode = "500", description = "Internal server error") })

	@GetMapping("/get-all-categories")
	public ResponseEntity<ResponseDetails> showAllCategories(HttpServletRequest httpRequest){
		ResponseDetails response = categoryService.getAllCategory(httpRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
