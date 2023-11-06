package br.com.mediapro.test.userserver.clients;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.mediapro.test.userserver.models.User;

@Component
public class RandomUserClient {
	private static final String URL = "https://randomuser.me/api/?seed=1234567890&results=200";

	private RestTemplate restTemplate;
	
	public RandomUserClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Cacheable("users")
    public List<User> getUsers() {
		JsonNode node = restTemplate.getForObject(URL, JsonNode.class);
    	return RandomUserParser.parseUsers(node);
    }
}