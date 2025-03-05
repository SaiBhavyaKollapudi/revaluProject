package com.example.docsApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllDocuments() throws Exception {
        mockMvc.perform(get("/api/show_all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void testGetByCategory() throws Exception {
        mockMvc.perform(get("/api/categories/End product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void testGetByTag() throws Exception {
        mockMvc.perform(get("/api/tags/new-product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void testGetByInvalidCategory() throws Exception {
        mockMvc.perform(get("/api/categories/invalid"))
                .andExpect(status().isNotFound()) // Expecting 404 for invalid category
                .andExpect(MockMvcResultMatchers.content().string("No documents found for category: invalid"));
    }

    @Test
    void testGetByInvalidTag() throws Exception {
        mockMvc.perform(get("/api/tags/unknown"))
                .andExpect(status().isNotFound()) // Expecting 404 for invalid tag
                .andExpect(MockMvcResultMatchers.content().string("No documents found for tag: unknown"));
    }

}
