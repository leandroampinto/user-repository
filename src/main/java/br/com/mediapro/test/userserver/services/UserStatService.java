package br.com.mediapro.test.userserver.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.mediapro.test.userserver.models.AgeRange;
import br.com.mediapro.test.userserver.models.Gender;
import br.com.mediapro.test.userserver.models.UserStats;

@Service
public class UserStatService {
    private UserService userService;

    public UserStatService(UserService userService) {
        this.userService = userService;
    }

    public UserStats retrieveStatsForUsers() {
        Map<Gender, Integer> countByGenders = new HashMap<>();
        Map<String, Integer> countByCountries = new HashMap<>();
        Map<AgeRange, Integer> countByAgeRanges = new HashMap<>();
        userService.getUsers().forEach(user -> {
            Gender gender = user.gender();
            int genderCount = countByGenders.getOrDefault(gender, 0);
            genderCount++;
            countByGenders.put(gender, genderCount);

            String country = user.country();
            int countryCount = countByCountries.getOrDefault(country, 0);
            countryCount++;
            countByCountries.put(country, countryCount);

            AgeRange ageRange = AgeRange.get(user.age());
            int ageRangeCount = countByAgeRanges.getOrDefault(ageRange, 0);
            ageRangeCount++;
            countByAgeRanges.put(ageRange, ageRangeCount);
        });
        return new UserStats(countByAgeRanges, countByGenders, countByCountries);
    }
}
