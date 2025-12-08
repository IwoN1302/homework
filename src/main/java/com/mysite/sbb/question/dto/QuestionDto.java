package com.mysite.sbb.question.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDto {
    @Size(max=200, message = "제목은 200자 이하로 작성하세요.")
    @NotEmpty(message = "제목은 필수 항목 입니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수 항목 입니다.")
    private String content;
}
