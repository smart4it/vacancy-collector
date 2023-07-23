package ru.smart4it.collector.event;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record Task(Long version, UUID id, String timestamp, String title, String protocol, String type,
                   List<Request> requests, Pagination pagination) {

    public record Request(String method, String url, List<String> queryParams, String body) {
    }

    public record Pagination(Long total, Long pageSize) {
    }
}
