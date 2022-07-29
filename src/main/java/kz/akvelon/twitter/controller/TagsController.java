package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.controller.api.TagsApi;
import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.service.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagsController implements TagsApi {
    private final TagsService tagsService;

    @Override
    public ResponseEntity<List<Tag>> getTags() {
        return ResponseEntity.ok(tagsService.getTags());
    }

    @Override
    public ResponseEntity<?> addTag(String tagName) {
        return ResponseEntity.ok(tagsService.addTag(tagName));
    }

    @Override
    public ResponseEntity<?> findByName(String tagName) {
        Tag tag = tagsService.findByName(tagName);

        if (tag == null) {
            return ResponseEntity.badRequest().body("No tag with this name");
        }

        return ResponseEntity.ok(tagsService.findByName(tagName));
    }

    @Override
    public ResponseEntity<List<TweetResponseDto>> getTweetsByTag(String tagName) {
        return ResponseEntity.ok(tagsService.getTweetsByTagName(tagName));
    }
}
