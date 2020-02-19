package com.epam.entities;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
public class Dog {

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
