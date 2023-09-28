package com.talentshare.mentor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.talentshare.mentor.model.Mentor;

public interface MentorRepository extends MongoRepository<Mentor, Integer> {

	Optional<Mentor> findByEmployeeId(int employeeId);
  Optional<Mentor> findByEmail(String email);


  List<Mentor> findBySkillsIn(List<String> skills);

	boolean existsByEmail(String email);

	boolean existsByEmployeeId(int empid);
}
