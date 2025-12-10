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
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.UUID;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public Page<Resume> getResumeList(int page, Member author) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("created"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return resumeRepository.findByAuthor(author, pageable);
    }

    public Resume getResume(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 이력서 X"));
    }

    public void create(ResumeDto resumeDto, Member member) {
        String profileImage = saveFile(resumeDto.getProfileImageFile());

        Resume resume = Resume.builder()
                .subject(resumeDto.getSubject())
                .content(resumeDto.getContent())
                .realName(resumeDto.getRealName())
                .dob(resumeDto.getDob())
                .phoneNumber(resumeDto.getPhoneNumber())
                .email(resumeDto.getEmail())
                .address(resumeDto.getAddress())
                .gender(resumeDto.getGender())
                .desiredSalary(resumeDto.getDesiredSalary())
                .desiredJob(resumeDto.getDesiredJob())
                .workDays(
                        String.join(",", resumeDto.getWorkDays() != null ? resumeDto.getWorkDays() : new ArrayList<>()))
                .workHours(resumeDto.getWorkStartTime() + "~" + resumeDto.getWorkEndTime())
                .experience(resumeDto.getExperience())
                .licenses(resumeDto.getLicenses())
                .profileImage(profileImage)
                .author(member)
                .build();

        resumeRepository.save(resume);
    }

    private String saveFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploads";
                File directory = new File(projectPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + file.getOriginalFilename();
                File saveFile = new File(projectPath, fileName);
                file.transferTo(saveFile);
                return "/uploads/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void modify(Resume resume, @Valid ResumeDto resumeDto) {
        String profileImage = saveFile(resumeDto.getProfileImageFile());
        if (profileImage != null) {
            resume.setProfileImage(profileImage);
        }

        resume.setSubject(resumeDto.getSubject());
        resume.setContent(resumeDto.getContent());
        resume.setRealName(resumeDto.getRealName());
        resume.setDob(resumeDto.getDob());
        resume.setPhoneNumber(resumeDto.getPhoneNumber());
        resume.setEmail(resumeDto.getEmail());
        resume.setAddress(resumeDto.getAddress());
        resume.setGender(resumeDto.getGender());
        resume.setDesiredSalary(resumeDto.getDesiredSalary());
        resume.setDesiredJob(resumeDto.getDesiredJob());
        if (resumeDto.getWorkDays() != null) {
            resume.setWorkDays(String.join(",", resumeDto.getWorkDays()));
        } else {
            resume.setWorkDays("");
        }
        resume.setWorkHours(resumeDto.getWorkStartTime() + "~" + resumeDto.getWorkEndTime());
        resume.setExperience(resumeDto.getExperience());
        resume.setLicenses(resumeDto.getLicenses());

        resumeRepository.save(resume);
    }

    public void delete(Resume resume) {
        resumeRepository.delete(resume);
    }
}
