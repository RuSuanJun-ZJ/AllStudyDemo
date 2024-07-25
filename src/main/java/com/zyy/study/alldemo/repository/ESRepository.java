package com.zyy.study.alldemo.repository;

import com.zyy.study.alldemo.model.Product;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESRepository extends ElasticsearchRepository<Product,Long> {
    Iterable<Product> findProductByTitle(String title);
}
