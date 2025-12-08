package com.mysite.sbb.resume.service;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.resume.dto.ResumeDto;
import com.mysite.sbb.resume.entity.Resume;
import com.mysite.sbb.resume.repository.ResumeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public Page<Resume> getResumeList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("created"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return resumeRepository.findAll(pageable);
    }

    public Resume getResume(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 이력서 X"));
    }

    public void create(ResumeDto resumeDto, Member member) {
        Resume resume = Resume.builder()
                .subject(resumeDto.getSubject())
                .content(resumeDto.getContent())
                .author(member)
                .build();

        resumeRepository.save(resume);
    }

    public void modify(Resume resume, @Valid ResumeDto resumeDto) {
        resume.setSubject(resumeDto.getSubject());
        resume.setContent(resumeDto.getContent());
        resumeRepository.save(resume);
    }

    public void delete(Resume resume) {
        resumeRepository.delete(resume);
    }
}
