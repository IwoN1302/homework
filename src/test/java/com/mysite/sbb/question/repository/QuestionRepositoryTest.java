package com.mysite.sbb.question.repository;

import com.mysite.sbb.question.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testSave(){
        Question q1 = new Question();
        q1.setSubject("sbb가 뭔가요?");
        q1.setContent("sbb는 질의 응답 게시판 인가요?");
        questionRepository.save(q1);

        Question q2 = Question.builder()
                .content("스프링부트는 어려울까요?")
                .subject("스프링 부트 난이도?")
                .build();
        Question saved = questionRepository.save(q2);
        assertEquals(2, saved.getId());
    }

}