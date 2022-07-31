package kz.akvelon.twitter.integrations;

import kz.akvelon.twitter.integrations.base.WithSecurity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class Tests extends WithSecurity {

    @Nested
    @DisplayName(value = "Requests for lessons is OK when ... ")
    public class LessonsTests {
        @Test
        public void return_401_for_user_with_invalid_token() throws Exception {
            mockMvc.perform(get("/tags")
                            .header("AUTHORIZATION", BEARER + INVALID_TOKEN))
                    .andDo(print())
                    .andExpect(status().isUnauthorized());
        }

        @Test
        public void return_tags_for_authorized_user() throws Exception {
            mockMvc.perform(get("/tags")
                            .header("AUTHORIZATION", BEARER + VALID_TOKEN))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.response.tags", hasSize(2)))
                    .andExpect(jsonPath("$.response.tags[0].name", is("football")));
        }

        @Test
        public void return_created_tag() throws Exception {
            mockMvc.perform(post("/tags")
                            .header("AUTHORIZATION", BEARER + VALID_TOKEN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"volleyball\"}"))
                    .andExpect(status().isCreated())
                    .andDo(print());
        }
    }
}
