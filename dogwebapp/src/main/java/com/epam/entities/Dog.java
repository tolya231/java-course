package com.epam.entities;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dog {

  @Min(1)
  @NotNull
  private Long id;
  @Size(min = 1, max = 100)
  private String name;
  @Past
  private LocalDate birthDay;
  @Min(1)
  private Integer weight;
  @Min(1)
  private Integer height;
}
