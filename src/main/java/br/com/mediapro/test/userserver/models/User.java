package br.com.mediapro.test.userserver.models;

import java.net.URI;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public record User (
    @Id String id,
    String name,
    @JsonProperty("last_name") String lastName,
    @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "dd/MM/yyyy")
    Date birthday,
    Gender gender,
    String email,
    String cell,
    String city,
    String state,
    String country,
    @JsonProperty("thumbnail")
    URI thumbnailUri
) {

    public int age() {
        LocalDate now = LocalDate.now();
        LocalDate birthday = LocalDate.ofInstant(birthday().toInstant(), ZoneId.systemDefault());
        Period age = Period.between(birthday, now);
        return age.getYears();
    }
}

