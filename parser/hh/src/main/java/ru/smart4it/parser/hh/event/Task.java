package ru.smart4it.parser.hh.event;

import java.util.List;
import java.util.UUID;

public record Task(Long version, UUID id, String timestamp, String title, String protocol, String type,
                   List<Request> requests, Pagination pagination) {

    public record Request(String method, String url, List<QueryParam> queryParams, String body) {
    }

    public record Pagination(Long total, Long pageSize) {
    }

    public record QueryParam(String param, String value) {
    }
}
