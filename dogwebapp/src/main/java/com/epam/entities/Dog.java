package com.epam.entities;

import com.epam.dto.DogDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Dog {

  @NotNull
  private Long id;
  private String name;
  private LocalDate birthDay;
  private Integer weight;
  private Integer height;

  public static DogDto dogToDogDto(Dog dog) {
    return new DogDto().setId(dog.id)
        .setName(dog.name)
        .setWeight(dog.weight)
        .setHeight(dog.height)
        .setBirthDay(dog.birthDay);
  }
}
