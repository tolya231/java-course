package entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class Dog {
    private String name;
    private LocalDate birthDate;
    private Integer weight;
    private Integer height;
}
