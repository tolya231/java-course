package com.epam.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString(exclude = {"dog"})
@NoArgsConstructor
@Accessors(chain = true)
public class PersonDto {

  private Long id;

  @Size(min = 1, max = 100)
  @NotNull
  private String name;

  private DogDto dog;

}
