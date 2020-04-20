package com.epam.dto;

import com.epam.utils.LocalDateAttributeConverter;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
@Entity
@Table(name = "DOG")
public class DogDto {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dog_generator")
  @SequenceGenerator(name="dog_generator", sequenceName = "dog_seq")
  private Long id;

  @Size(min = 1, max = 100)
  @NotNull
  @Column(name = "name")
  private String name;

  @Past
  @Column(name = "birthDay")
  @Convert(converter = LocalDateAttributeConverter.class)
  private LocalDate birthDay;

  @Min(1)
  @NotNull
  @Column(name = "weight")
  private Integer weight;

  @Min(1)
  @NotNull
  @Column(name = "height")
  private Integer height;
}
