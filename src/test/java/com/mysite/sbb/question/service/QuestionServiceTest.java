package com.mysite.sbb.question.service;

import com.mysite.sbb.member.constant.Department;
import com.mysite.sbb.member.constant.Gender;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.repository.MemberRepository;
import com.mysite.sbb.question.dto.QuestionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Test
    void create() {
        Member member = Member.builder()
                .username("user1")
                .password("1234")
                .email("user1@test.com")
                .gender(Gender.MALE)
                .department(Department.CSE)
                .active(true)
                .build();
        memberRepository.save(member);

        for (int i = 1; i < 301; i++) {
            QuestionDto questionDto = QuestionDto.builder()
                    .subject("질문 제목 " + i)
                    .content("질문 내용 " + i)
                    .build();
            questionService.create(questionDto, member);
        }
        // System.out.println("==== Size : " +
        // questionService.getQuestionList().size());

    }
}