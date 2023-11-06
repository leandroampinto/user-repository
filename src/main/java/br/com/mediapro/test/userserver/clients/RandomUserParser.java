package br.com.mediapro.test.userserver.clients;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.mediapro.test.userserver.models.Gender;
import br.com.mediapro.test.userserver.models.User;

public class RandomUserParser {

	private static String getId(JsonNode node) {
		return node.get("id").get("value").asText();
	}

	private static String getName(JsonNode node) {
		return node.get("name").get("first").asText();
	}

	private static String getLastName(JsonNode node) {
		return node.get("name").get("last").asText();
	}

	private static Date getBirthday(JsonNode node) {
		Instant birthday = Instant.parse(
			node.get("registered").get("date").asText()
		);
		return Date.from(birthday);
	}

	private static Gender getGender(JsonNode node) {
		JsonNode genderNode = node.get("gender");
        return Gender.valueOf(genderNode.asText().toUpperCase());
	}

	private static String getEmail(JsonNode node) {
		return node.get("email").asText();
	}

	private static String getCell(JsonNode node) {
		return node.get("cell").asText();
	}

	private static String getCity(JsonNode node) {
		return node.get("location").get("city").asText();
	}

	private static String getState(JsonNode node) {
		return node.get("location").get("state").asText();
	}

	private static String getCountry(JsonNode node) {
		return node.get("location").get("country").asText();
	}
	
	private static URI getThumbnailUri(JsonNode node) {
		try {
			return new URI(node.get("picture").get("thumbnail").asText());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private static User parseUser(JsonNode node) {
		String id = getId(node);
		String name = getName(node);
		String lastName = getLastName(node);
		Date birthday = getBirthday(node);
		Gender gender = getGender(node);
		String email = getEmail(node);
		String cell = getCell(node);
		String city = getCity(node);
		String state = getState(node);
		String country = getCountry(node);
		URI thumbnailUri = getThumbnailUri(node);
		User user = new User(id, name, lastName, birthday, gender, email, cell, city, state, country, thumbnailUri);
		return user;
	}

	public static List<User> parseUsers(JsonNode node) {
		Iterator<JsonNode> iterator = node.get("results").elements();
		Spliterator<JsonNode> splitetator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);
		Stream<JsonNode> stream = StreamSupport.stream(splitetator, false);
		return stream.map(currentNode -> parseUser(currentNode)).collect(Collectors.toList());
	}
}