package br.com.mediapro.test.userserver.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserStats(
    @JsonProperty("age_ranges") Map<AgeRange, Integer> countByAgeRanges,
    @JsonProperty("genders") Map<Gender, Integer> countByGenders,
    @JsonProperty("countries") Map<String, Integer> countByCountries) {
}
