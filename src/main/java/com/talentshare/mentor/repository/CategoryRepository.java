package com.talentshare.mentor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.talentshare.mentor.model.Category;

public interface CategoryRepository extends MongoRepository<Category, Integer> {

}
