package utils;

import com.epam.dto.DogDto;
import com.epam.dto.PersonDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

public class DogGenerator {

  public static DogDto dog() {
    return new DogDto()
        .setName(generateRandomString(1, 100))
        .setHeight(generateRandomNumber(1, 200 ))
        .setWeight(generateRandomNumber(1, 200 ))
        .setBirthDay(LocalDate.now().minusDays(generateRandomNumber(1, 500)));
  }

  public static DogDto dogWithOwners(int n) {
    DogDto dogDto = dog();
    List<PersonDto> owners = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      owners.add(new PersonDto().setName(generateRandomString(1, 100)).setDog(dogDto));
    }
    return dogDto.setOwners(owners);
  }


  private static String generateRandomString(int minLen, int maxLen) {
    int len = generateRandomNumber(minLen, maxLen);
    return RandomStringUtils.randomAlphanumeric(len);
  }

  private static int generateRandomNumber(int min, int max) {
    return new Random().nextInt(max - min) + min;
  }
}
