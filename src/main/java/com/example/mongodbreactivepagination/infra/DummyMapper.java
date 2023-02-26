package com.example.mongodbreactivepagination.infra;

import com.example.mongodbreactivepagination.domain.Dummy;
import lombok.NoArgsConstructor;

@NoArgsConstructor
class DummyMapper {

    static Dummy fromDocument(DummyDocument document) {

        return Dummy
                .builder()
                .id(document.getId())
                .title(document.getTitle())
                .description(document.getDescription())
                .build();
    }
}
