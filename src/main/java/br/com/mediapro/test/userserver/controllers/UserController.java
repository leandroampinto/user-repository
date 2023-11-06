package br.com.mediapro.test.userserver.controllers;

import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mediapro.test.userserver.models.User;
import br.com.mediapro.test.userserver.models.UserStats;
import br.com.mediapro.test.userserver.services.UserService;
import br.com.mediapro.test.userserver.services.UserStatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "Users endpoints")
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserStatService userStatService;

    public UserController(UserService userService, UserStatService userStatService) {
        this.userService = userService;
        this.userStatService = userStatService;
    }

    @Operation(summary = "Retrieve all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/")
    public Stream<User> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary="Retrieve the user by Id")
    @ApiResponses({
        @ApiResponse(responseCode="200",content={@Content(schema=@Schema(implementation=User.class),mediaType="application/json")}),
        @ApiResponse(responseCode="404",content={@Content(schema=@Schema())}),
        @ApiResponse(responseCode="500",content={@Content(schema=@Schema())})
    })
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @Operation(
        summary="Retrieve the user thumbnail by Id",
        description = "Retrieve the user thumbnail by Id use Base 64 encoding"
    )
    @ApiResponses({
        @ApiResponse(responseCode="200",content={@Content(schema=@Schema(implementation=User.class),mediaType="application/json")}),
        @ApiResponse(responseCode="404",content={@Content(schema=@Schema())}),
        @ApiResponse(responseCode="500",content={@Content(schema=@Schema())})
    })
    @GetMapping("/{id}/thumbnail")
    public String getUserThumbnailById(@PathVariable("id") String id) {
        return userService.getUserThumbnailById(id);
    }

    @Operation(
        summary="Search the users by a substring",
        description = "Search the users by a substring in fields: name, last_name, city, state, country and email"
    )
    @ApiResponses({
        @ApiResponse(responseCode="200",content={@Content(schema=@Schema(implementation=User.class),mediaType="application/json")}),
        @ApiResponse(responseCode="500",content={@Content(schema=@Schema())})
    })
    @GetMapping("/search")
    public Stream<User> searchUsers(@RequestParam("query") String query) {
        return userService.searchUsers(query);
    }

    @Operation(
        summary="Retrieve stats of use"
    )
    @ApiResponses({
        @ApiResponse(responseCode="200",content={@Content(schema=@Schema(implementation=User.class),mediaType="application/json")}),
        @ApiResponse(responseCode="500",content={@Content(schema=@Schema())})
    })
    @GetMapping("/stats")
    public UserStats retrieveStatsForUsers() {
        return userStatService.retrieveStatsForUsers();
    }
}
