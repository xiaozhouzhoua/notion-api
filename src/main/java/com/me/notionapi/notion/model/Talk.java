package com.me.notionapi.notion.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Talk {
    private String id;
    private String title;
    private String url;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
