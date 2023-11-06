package br.com.mediapro.test.userserver.clients;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class DownloadClient {
	private HttpClient httpClient;
	
	public DownloadClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

    @Cacheable("download")
	public String readBase64(URI uri) {
		try {
        HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
		HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
		return Base64.getEncoder().encodeToString(response.body());
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}