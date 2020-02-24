package com.epam.entities;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class Dog {

  @NotNull
  private Long id;
  private String name;
  private LocalDate birthDay;
  private Integer weight;
  private Integer height;
}
