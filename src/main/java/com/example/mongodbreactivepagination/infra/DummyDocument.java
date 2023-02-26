package com.example.mongodbreactivepagination.infra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@Document
@AllArgsConstructor(access = PRIVATE)
public class DummyDocument {

    @Id
    Long id;

    String title;

    String description;
}
