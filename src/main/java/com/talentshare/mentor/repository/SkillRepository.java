package com.talentshare.mentor.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.talentshare.mentor.model.Skill;

public interface SkillRepository extends MongoRepository<Skill, Integer>{

}
