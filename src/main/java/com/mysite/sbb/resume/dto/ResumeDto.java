package com.mysite.sbb.resume.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDto {
    @Size(max = 200, message = "제목은 200자 이하로 작성하세요.")
    @NotEmpty(message = "제목은 필수 항목 입니다.")
    private String subject;

    @NotEmpty(message = "내용은 필수 항목 입니다.")
    private String content;

    private MultipartFile profileImageFile;
    private String profileImage;

    @NotEmpty(message = "성명은 필수항목입니다.")
    private String realName;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String dob;

    @NotEmpty(message = "연락처는 필수항목입니다.")
    private String phoneNumber;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    private String email;

    @NotEmpty(message = "주소는 필수항목입니다.")
    private String address;

    @NotEmpty(message = "성별은 필수항목입니다.")
    private String gender;

    // Work Conditions
    private String desiredSalary;
    private String desiredJob;
    private List<String> workDays;

    private String workStartTime;
    private String workEndTime;

    private String experience;
    private String licenses;
}
