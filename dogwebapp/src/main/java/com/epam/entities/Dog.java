package com.epam.entities;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dog {

  @Positive
  @NotNull()
  private Long id;
  @Size(min = 1, max = 100)
  private String name;
  @PastOrPresent
  private LocalDate birthDate;
  @Positive
  private Integer weight;
  @Positive
  private Integer height;
}
