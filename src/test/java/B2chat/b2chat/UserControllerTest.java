package B2chat.b2chat;

import B2chat.b2chat.entity.User;
import B2chat.b2chat.service.GitHubService;
import B2chat.b2chat.service.TwitterService;
import B2chat.b2chat.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {UserControllerTest.TestSecurityConfig.class, UserControllerTest.TestConfig.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Configuration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .anyRequest().permitAll();
            return http.build();
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private GitHubService gitHubService;

    @MockBean
    private TwitterService twitterService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        user1 = new User(1L, "rolandoMS", "password123Aa", "rolando@example.com");
        user2 = new User(2L, "rolySM", "password456", "roly@example.com");
    }

    @Test
    public void shouldCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/b2chat/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(content().string("User created successfully"));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/b2chat/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("rolandoMS"))
                .andExpect(jsonPath("$.email").value("rolando@example.com"));
    }

    @Test
    public void shouldReturnNotFoundForInvalidUserId() throws Exception {
        when(userService.getUserById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/b2chat/api/users/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/b2chat/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("rolandoMS"))
                .andExpect(jsonPath("$[0].email").value("rolando@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("rolySM"))
                .andExpect(jsonPath("$[1].email").value("roly@example.com"));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        User updatedUser = new User(1L, "rolandoMS", "new_password", "rolando@example.com");

        when(userService.updateUser(Mockito.eq(1L), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/b2chat/api/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("new_password"));
    }

    @Test
    public void shouldReturnBadRequestForInvalidUpdate() throws Exception {
        User updatedUser = new User(2L, "rolySM", "password456", "roly@example.com");

        when(userService.updateUser(Mockito.eq(2L), any(User.class)))
                .thenThrow(new IllegalArgumentException("User not found"));

        mockMvc.perform(put("/b2chat/api/users/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/b2chat/api/users/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    public void shouldReturnNotFoundForInvalidDelete() throws Exception {
        doThrow(new IllegalArgumentException("User not found")).when(userService).deleteUser(2L);

        mockMvc.perform(delete("/b2chat/api/users/delete/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}