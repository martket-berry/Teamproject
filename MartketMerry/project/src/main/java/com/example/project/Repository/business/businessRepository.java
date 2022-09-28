package com.example.project.Repository.business;

import com.example.project.entity.businessMember.businessMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface businessRepository extends JpaRepository<businessMember, String> {
}
