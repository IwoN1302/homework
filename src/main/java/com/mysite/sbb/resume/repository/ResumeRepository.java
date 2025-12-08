package com.mysite.sbb.resume.repository;

import com.mysite.sbb.resume.entity.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Page<Resume> findAll(Pageable pageable);
}
