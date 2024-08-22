package com.nnk.springboot.controllers;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/list"))
                .andExpect(MockMvcResultMatchers.model().attribute("users", Collections.emptyList()));
    }

    @Test
    @WithMockUser
    public void testAddUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/add"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "testUser")
                        .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        User user = new User();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user));
    }

    @Test
    @WithMockUser
    public void testUpdateUserSuccess() throws Exception {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "updatedUser")
                        .param("password", "newPassword"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser
    public void testDeleteUser() throws Exception {
        User user = new User();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));

        verify(userRepository, times(1)).delete(user);
    }
}
