package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.dto.AnswerDto;
import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.repository.AnswerRepository;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.resume.entity.Resume;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Resume resume, String content, Member member) {
        Answer answer = Answer.builder()
                .resume(resume)
                .content(content)
                .author(member)
                .build();
        answerRepository.save(answer);
    }

    public Answer getAnswer(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 답변은 존재하지 않습니다."));
        return answer;
    }

    public void modify(Answer answer, @Valid AnswerDto answerDto) {
        answer.setContent(answerDto.getContent());
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }
}
