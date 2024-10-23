package B2chat.b2chat.controller;
import B2chat.b2chat.Utils;
import B2chat.b2chat.entity.User;
import B2chat.b2chat.service.GitHubService;
import B2chat.b2chat.service.TwitterService;
import B2chat.b2chat.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Tag(name = "Usuarios", description = "Gestión de usuarios")
@RestController
@RequestMapping("/b2chat/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private TwitterService twitterService;

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en la base de datos")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(Utils.CREATE_USER);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario basado en su ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista de todos los usuarios")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            User user = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario por su ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Utils.USER_DELETE);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/github/{username}")
    public Mono<ResponseEntity<String>> getGitHubUserInfo(@PathVariable String username) {
        return gitHubService.getUserInfo(username)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/twitter/{username}/tweets")
    public Mono<ResponseEntity<String>> getUserTweets(@PathVariable String username) {
        return twitterService.getUserTweets(username)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
