package com.me.notionapi.notion.service;

import com.me.notionapi.notion.config.NotionConfigProperties;
import com.me.notionapi.notion.model.Database;
import com.me.notionapi.notion.model.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DatabaseService {
    private final RestTemplate restTemplate;
    private final NotionConfigProperties notionConfigProps;
    private final Logger log = LoggerFactory.getLogger(DatabaseService.class);

    public DatabaseService(RestTemplate restTemplate, NotionConfigProperties notionConfigProps) {
        this.restTemplate = restTemplate;
        this.notionConfigProps = notionConfigProps;
    }

    public List<Page> query() {
        String url = notionConfigProps.getApiUrl() + "/v1/databases/" +
                notionConfigProps.getDatabaseId() + "/query";
        log.info("Querying Notion database: {}", url);
        ResponseEntity<Database> db = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(getDefaultHeaders()),
                Database.class);

        return db.getBody().getPages();
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Notion-Version", notionConfigProps.getApiVersion());
        headers.set("Authorization",notionConfigProps.getAuthToken());
        return headers;
    }
}
