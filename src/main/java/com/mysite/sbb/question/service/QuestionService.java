package com.mysite.sbb.question.service;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.question.dto.QuestionDto;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getQuestionList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("created"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    public Question getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당질문 X"));
        return question;

//        Optional<Question> q = questionRepository.findById(id);
//        if(q.isPresent()){
//            Question question = q.get();
//            System.out.println(question);
//        } else {
//            throw new EntityNotFoundException("해당 질문 X");
//        }
    }

    public void create(QuestionDto questionDto, Member member) {
        Question question = Question.builder()
                .subject(questionDto.getSubject())
                .content(questionDto.getContent())
                .author(member)
                .build();

        questionRepository.save(question); // insert
    }

    public void modify(Question question, @Valid QuestionDto questionDto) {
        question.setSubject(questionDto.getSubject());
        question.setContent(questionDto.getContent());
        questionRepository.save(question);  // update
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
