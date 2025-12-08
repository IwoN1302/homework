package com.mysite.sbb.answer.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDto {
    @NotEmpty(message = "내용은 필수 항목 입니다.")
    private String content;
}
