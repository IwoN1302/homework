package com.mysite.sbb.resume.controller;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.resume.dto.ResumeDto;
import com.mysite.sbb.resume.entity.Resume;
import com.mysite.sbb.resume.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
            Principal principal) {
        Resume resume = resumeService.getResume(id);

        if (!resume.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        resumeService.delete(resume);

        return "redirect:/resume/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
            @Valid ResumeDto resumeDto,
            BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "resume/resumeForm";
        }
        Resume resume = resumeService.getResume(id);

        if (!resume.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        resumeService.modify(resume, resumeDto);

        return "redirect:/resume/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id, Model model,
            Principal principal) {
        Resume resume = resumeService.getResume(id);

        if (!resume.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setSubject(resume.getSubject());
        resumeDto.setContent(resume.getContent());
        resumeDto.setRealName(resume.getRealName());
        resumeDto.setDob(resume.getDob());
        resumeDto.setPhoneNumber(resume.getPhoneNumber());
        resumeDto.setEmail(resume.getEmail());
        resumeDto.setAddress(resume.getAddress());
        resumeDto.setGender(resume.getGender());
        resumeDto.setDesiredSalary(resume.getDesiredSalary());
        resumeDto.setDesiredJob(resume.getDesiredJob());
        if (resume.getWorkDays() != null) {
            resumeDto.setWorkDays(Arrays.asList(resume.getWorkDays().split(",")));
        }

        if (resume.getWorkHours() != null && resume.getWorkHours().contains("~")) {
            String[] times = resume.getWorkHours().split("~");
            if (times.length > 0)
                resumeDto.setWorkStartTime(times[0]);
            if (times.length > 1)
                resumeDto.setWorkEndTime(times[1]);
        }
        resumeDto.setExperience(resume.getExperience());
        resumeDto.setLicenses(resume.getLicenses());
        resumeDto.setProfileImage(resume.getProfileImage());

        model.addAttribute("resumeDto", resumeDto);
        return "resume/resumeForm";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
            Principal principal) {
        Member member = memberService.getMember(principal.getName());
        Page<Resume> paging;
        if (member.getUserType() == com.mysite.sbb.member.constant.UserType.SCOUT) {
            paging = resumeService.getAllResumeList(page);
        } else {
            paging = resumeService.getResumeList(page, member);
        }
        System.out.println("=== paging : " + paging);
        model.addAttribute("paging", paging);
        return "resume/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Resume resume = resumeService.getResume(id);
        model.addAttribute("resume", resume); // Changed "question" to "resume"
        log.info("resume : {}", resume);
        return "resume/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("resumeDto", new ResumeDto()); // Changed "questionDto" to "resumeDto"
        return "resume/resumeForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid ResumeDto resumeDto,
            BindingResult bindingResult,
            Principal principal,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("resumeDto", resumeDto);
            return "resume/resumeForm";
        }

        Member member = memberService.getMember(principal.getName());

        resumeService.create(resumeDto, member);
        return "redirect:/resume/list";
    }

}
