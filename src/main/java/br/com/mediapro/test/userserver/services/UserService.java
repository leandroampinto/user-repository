package br.com.mediapro.test.userserver.services;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import br.com.mediapro.test.userserver.clients.RandomUserClient;
import br.com.mediapro.test.userserver.clients.DownloadClient;
import br.com.mediapro.test.userserver.exceptions.UserNotFoundException;
import br.com.mediapro.test.userserver.models.AgeRange;
import br.com.mediapro.test.userserver.models.Gender;
import br.com.mediapro.test.userserver.models.User;
import br.com.mediapro.test.userserver.models.UserStats;

@Service
public class UserService {
    private RandomUserClient randomUserClient;
    private DownloadClient downloadClient;

    public UserService(RandomUserClient randomUserClient, DownloadClient downloadClient) {
        this.randomUserClient = randomUserClient;
        this.downloadClient = downloadClient;
    }

    public Stream<User> getUsers() {
        return randomUserClient.getUsers().stream();
    }

    public User getUserById(String id) {
        var maybeUser = getUsers().filter(user -> user.id().equals(id)).findFirst();
        if (maybeUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return maybeUser.get();
    }

    public String getUserThumbnailById(String id) {
        User user = getUserById(id);
        URI uri = user.thumbnailUri();
        return downloadClient.readBase64(uri);
    }

    private static boolean acceptUser(User user, String query) {
        String [] values = new String[] {
            user.name().toLowerCase(),
            user.lastName().toLowerCase(),
            user.city().toLowerCase(),
            user.state().toLowerCase(),
            user.country().toLowerCase(),
            user.email().toLowerCase()
        };
        return Arrays.stream(values).anyMatch(value -> value.contains(query.toLowerCase()));
    }

    public Stream<User> searchUsers(String query) {
        return getUsers().filter(user -> acceptUser(user, query));
    }
}
