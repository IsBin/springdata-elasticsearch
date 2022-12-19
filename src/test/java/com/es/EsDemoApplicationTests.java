package com.es;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        System.out.println("创建索引");
    }

    @Test
    void deleteIndex(){
        boolean b = elasticsearchRestTemplate.deleteIndex(User.class);
        System.out.println(b);
    }

    //新增文档
    @Test
    void saveDocument(){
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setIcon("1");
        user.setAge(10);
        User save = userDao.save(user);
        System.out.println(save);
    }

    //修改文档
    @Test
    void updateDocument(){
        User user = new User();
        user.setId(1L);
        user.setName("张三111");
        user.setIcon("1");
        user.setAge(10);
        User save = userDao.save(user);
        System.out.println(save);
    }

    //根据ID查询文档
    @Test
    void findDocumentById(){
        User user = userDao.findById(1L).get();
        System.out.println(user);
    }

    //删除文档
    @Test
    void deleteDocument(){
        userDao.deleteById(1L);
    }

    //批量新增文档
    @Test
    void saveBatch(){
        List<User> users  = new ArrayList<>();
        for(int i=0;i<10;i++){
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setName("姓名" + i);
            user.setAge(i+10);
            user.setIcon(i+"");
            users.add(user);
        }
        userDao.saveAll(users);
    }

    //查询所有文档
    @Test
    void findAll(){
        Iterable<User> all = userDao.findAll();
        for(User user : all){
            System.out.println(user);
        }
    }

    //分页、排序 文档
    @Test
    void findByPage(){
        Sort sort = Sort.by(Sort.Order.desc("id"));
        int page = 0;
        int size = 5;

        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<User> all = userDao.findAll(pageRequest);
        for(User user : all){
            System.out.println(user);
        }
    }

    //搜索文档
    @Test
    void termQuery(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","姓");
        Iterable<User> search = userDao.search(termQueryBuilder);
        for(User user : search){
            System.out.println(user);
        }
    }

    //搜索文档+分页
    @Test
    void termQueryAndPage(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name","姓");
        PageRequest pageRequest = PageRequest.of(0,5);
        Iterable<User> search = userDao.search(termQueryBuilder,pageRequest);
        for(User user : search){
            System.out.println(user);
        }
    }
}
