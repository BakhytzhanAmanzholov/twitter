package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.base.BaseControllerTest;
import kz.akvelon.twitter.service.TagsService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("noSecurity")
@DisplayName("TagsController is work when ...")
public class TagsControllerTest extends BaseControllerTest {
    @MockBean
    TagsService tagsService;



}
