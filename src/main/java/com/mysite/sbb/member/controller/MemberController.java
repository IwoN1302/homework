package com.mysite.sbb.member.controller;

import com.mysite.sbb.member.dto.MemberDto;
import com.mysite.sbb.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginError", "아이디 또는 비밀번호가 잘못되었습니다.");
        return "member/login";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto memberDto,
                         BindingResult bindingResult,
                         Model model) {

        if(bindingResult.hasErrors()){
            return "member/signup";
        }

        if(!memberDto.getPassword1().equals(memberDto.getPassword2())){
            bindingResult.rejectValue("password2", "PasswordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "member/signup";
        }

        try {
            memberService.create(memberDto);
        } catch (DataIntegrityViolationException e){
            log.info("===== 회원 등록 실패: 이미 등록된 사용자 입니다.");
            model.addAttribute("errorMsg", "이미 등록된 사용자 입니다.");
            return "member/signup";
        }

        return "redirect:/";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/signup";
    }
}
