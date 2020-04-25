package com.epam.dto;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
public class DogDto {

  private Long id;

  @Size(min = 1, max = 100)
  @NotNull
  private String name;

  @Past
  private LocalDate birthDay;

  @Min(1)
  @NotNull
  private Integer weight;

  @Min(1)
  @NotNull
  private Integer height;
}
