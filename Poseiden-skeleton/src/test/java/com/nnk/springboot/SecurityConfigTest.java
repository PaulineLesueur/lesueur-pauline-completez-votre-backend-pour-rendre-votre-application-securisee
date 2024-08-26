package com.nnk.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginPageOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void protectedUrlRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/admin-dashboard"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
    }

    @Test
    void adminAccessOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void userAccessForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                        .with(SecurityMockMvcRequestPostProcessors.user("user").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void loginSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "user")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void loginFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "user")
                        .param("password", "password")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}
