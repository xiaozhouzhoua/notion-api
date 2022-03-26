package com.me.notionapi.notion.controller;

import com.me.notionapi.notion.config.NotionConfigProperties;
import com.me.notionapi.notion.model.Page;
import com.me.notionapi.notion.model.Talk;
import com.me.notionapi.notion.service.DatabaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/talks")
public class TalkController {

    private final DatabaseService databaseService;

    public TalkController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping()
    public List<Talk> findAll() {
        List<Page> pages = databaseService.query();
        return pages.stream()
                .map(TalkController::mapPageToTalk)
                .collect(Collectors.toList());
    }

    public static Talk mapPageToTalk(Page page) {
        return new Talk(
                page.getId(),
                page.getProperties().get("Title").get("title").get(0).get("text").get("content").asText(),
                page.getProperties().get("Url").get("url").asText(),
                LocalDateTime.parse(page.getProperties().get("StartDate").get("date").get("start").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                LocalDateTime.parse(page.getProperties().get("EndDate").get("date").get("start").asText(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}
