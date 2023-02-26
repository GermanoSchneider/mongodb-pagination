package com.example.mongodbreactivepagination.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@AllArgsConstructor(access = PRIVATE)
public class Dummy {

    Long id;

    String title;

    String description;
}
