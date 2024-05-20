package com.nnk.springboot;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
public class RatingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingRepository ratingRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testHome() throws Exception {
        // Arrange
        Rating rating1 = new Rating("Moodys Rating 1", "SandPRating 1", "Fitch Rating 1", 10);
        Rating rating2 = new Rating("Moodys Rating 2", "SandPRating 2", "Fitch Rating 2", 20);
        rating1.setId(1);
        rating2.setId(2);
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        // Act & Assert
        mockMvc.perform(get("/rating/list").requestAttr("remoteUser", "user"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", Arrays.asList(rating1, rating2)));
    }
}
