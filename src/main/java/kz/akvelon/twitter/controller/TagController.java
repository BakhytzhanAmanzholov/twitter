package kz.akvelon.twitter.controller;

import kz.akvelon.twitter.dto.response.tweets.TweetResponseDto;
import kz.akvelon.twitter.model.Tag;
import kz.akvelon.twitter.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }

    @GetMapping("/{tag-name}")
    public ResponseEntity<?> findByName(@PathVariable("tag-name") String tagName) {
        Tag tag = tagService.findByName(tagName);

        if (tag == null) {
            return ResponseEntity.badRequest().body("No tag with this name");
        }

        return ResponseEntity.ok(tagService.findByName(tagName));
    }

    @GetMapping("/{tag-name}/tweets")
    public ResponseEntity<List<TweetResponseDto>> getTweetsByTag(@PathVariable("tag-name") String name) {
        return ResponseEntity.ok(tagService.getTweetsByTagName(name));
    }
}
