package br.com.mediapro.test.userserver.services;

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.mediapro.test.userserver.clients.RandomUserClient;
import br.com.mediapro.test.userserver.clients.DownloadClient;
import br.com.mediapro.test.userserver.exceptions.UserNotFoundException;
import br.com.mediapro.test.userserver.models.User;

@Service
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

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
        final int min = 3;
        final int max = 64;
        if (min <= query.length() && query.length() <= max) {
            return getUsers().filter(user -> acceptUser(user, query));
        }
        else {
            logger.warn(
                "Returning all users, because the query '{}' parameters isn't between {} and {}",
                query,
                min,
                max
            );
            return getUsers();
        }
    }
}
