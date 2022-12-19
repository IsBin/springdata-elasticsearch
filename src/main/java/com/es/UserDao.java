package com.es;


import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Mapping
public interface UserDao extends ElasticsearchRepository<User,Long> {

}
