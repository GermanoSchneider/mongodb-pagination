package com.example.mongodbreactivepagination.infra;

import com.example.mongodbreactivepagination.domain.DummyDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Import({DummyMongoAdapter.class})
@ExtendWith(SpringExtension.class)
class DummyMongoAdapterTest {

    @Autowired
    DummyDomainService dummyDomainService;

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    @DisplayName("Should return 20 records across three pages of dummy data")
    void shouldReturnDummyPagedDataWithSuccess() {

        saveDummyData();

        validatePage(0, 9);
        validatePage(1, 9);
        validatePage(2, 2);
    }

    private Pageable getPage(int page) {

        return PageRequest.of(page, 9);
    }

    private void validatePage(int numberPage, int numberElements) {

        StepVerifier.create(dummyDomainService.findAll(getPage(numberPage)))
                .assertNext(page -> {
                    assertEquals(20, page.getTotalElements());
                    assertEquals(9, page.getSize());
                    assertEquals(3, page.getTotalPages());
                    assertEquals(numberElements, page.getNumberOfElements());
                }).verifyComplete();
    }

    private void saveDummyData() {

        Flux.range(1, 20)
                .map(this::createDummy)
                .flatMap(reactiveMongoTemplate::save)
                .subscribe();
    }

    private DummyDocument createDummy(int id) {

        return DummyDocument.builder()
                .id((long) id)
                .title("whatever name you want to put here")
                .build();
    }
}
