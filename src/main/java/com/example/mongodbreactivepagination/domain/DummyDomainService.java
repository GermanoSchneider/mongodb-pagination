package com.example.mongodbreactivepagination.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface DummyDomainService {
    Mono<Page<Dummy>> findAll(Pageable pageable);
}
