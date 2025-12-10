package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.resume.dto.ResumeDto;
import com.mysite.sbb.resume.entity.Resume;
import com.mysite.sbb.resume.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/answer")
@Slf4j
@RequiredArgsConstructor
public class AnswerController {

    private final ResumeService resumeService;
    private final AnswerService answerService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
            Principal principal) {
        Answer answer = answerService.getAnswer(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        answerService.delete(answer);

        return "redirect:/resume/detail/" + answer.getResume().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
            @Valid AnswerDto answerDto,
            BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "answer/inputForm";
        }
        Answer answer = answerService.getAnswer(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        answerService.modify(answer, answerDto);

        return "redirect:/resume/detail/" + answer.getResume().getId();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id, Model model,
            Principal principal) {
        Answer answer = answerService.getAnswer(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        AnswerDto answerDto = new AnswerDto();
        answerDto.setContent(answer.getContent());
        model.addAttribute("answerDto", answerDto);
        return "answer/inputForm";
    }

    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SCOUT')")
    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Long id,
            @RequestParam("content") String content,
            Principal principal) {
        log.info("============= id : {}, {}", id, content);
        Resume resume = resumeService.getResume(id);

        Member member = memberService.getMember(principal.getName());

        answerService.create(resume, content, member);

        return "redirect:/resume/detail/" + id;
    }

}
