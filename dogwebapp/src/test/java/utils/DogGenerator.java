package utils;

import com.epam.dto.DogDto;
import java.time.LocalDate;
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


  private static String generateRandomString(int minLen, int maxLen) {
    int len = generateRandomNumber(minLen, maxLen);
    return RandomStringUtils.randomAlphanumeric(len);
  }

  private static int generateRandomNumber(int min, int max) {
    return new Random().nextInt(max - min) + min;
  }
}
