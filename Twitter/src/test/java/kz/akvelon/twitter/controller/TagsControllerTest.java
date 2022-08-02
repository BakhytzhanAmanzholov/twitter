package kz.akvelon.twitter.controller;


import kz.akvelon.twitter.controller.base.BaseControllerTest;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.service.TagsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.HashSet;

import static kz.akvelon.twitter.controller.config.ConfigurationForControllersTest.MOCK_USERNAME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("noSecurity")
@DisplayName("TagsController is work when ...")
public class TagsControllerTest extends BaseControllerTest {
    @MockBean
    TagsService tagsService;

    @BeforeEach
    public void setUp() {
        Tag tag = Tag.builder()
                .id(1L)
                .tagName("football")
                .tweetsCount(0L)
                .tweets(new HashSet<>())
                .build();

        when(tagsService.getTags())
                .thenReturn(Collections.singletonList(tag));
    }

    @WithUserDetails(value = MOCK_USERNAME)
    @Test
    public void return_tags_for_authorized_user() throws Exception {
        mockMvc.perform(get("/tags"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.tags", hasSize(1)))
                .andExpect(jsonPath("$.response.tags[0].id", is(1)))
                .andExpect(jsonPath("$.response.tags[0].name", is("football")));
    }
}
