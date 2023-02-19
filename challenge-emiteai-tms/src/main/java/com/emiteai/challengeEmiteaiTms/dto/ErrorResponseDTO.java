package com.emiteai.challengeEmiteaiTms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

  private String message;
  private List<String> details;
}
