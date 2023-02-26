package com.example.mongodbreactivepagination.infra;

import com.example.mongodbreactivepagination.domain.Dummy;
import com.example.mongodbreactivepagination.domain.DummyDomainService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

import static reactor.core.publisher.Mono.just;
import static reactor.core.publisher.Mono.zip;
import static reactor.function.TupleUtils.function;

@Component
@AllArgsConstructor
class DummyMongoAdapter implements DummyDomainService {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<Page<Dummy>> findAll(Pageable pageable) {

        var query = new Query().with(pageable);

        var count = reactiveMongoTemplate.count(Query.of(query).skip(0).limit(0), DummyDocument.class).cache();

        var dummies = reactiveMongoTemplate
                .find(query, DummyDocument.class)
                .map(DummyMapper::fromDocument)
                .collectList()
                .cache();


        return zip(dummies, just(pageable), count)
                .map(function(this::generatePage));
    }

    private Page<Dummy> generatePage(
            List<Dummy> dummies,
            Pageable pageable,
            Long numberOfElements
    ) {
        return PageableExecutionUtils.getPage(dummies, pageable, () -> numberOfElements);
    }
}
