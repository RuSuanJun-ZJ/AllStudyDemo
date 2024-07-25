package com.zyy.study.alldemo.es;

import com.zyy.study.alldemo.model.Product;
import com.zyy.study.alldemo.repository.ESRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataESProductDaoTest {
    @Autowired
    private ESRepository esRepository;

    /**
     * 新增
     */
    @Test
    public void save() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("http://www.atguigu/hw.jpg");
        esRepository.save(product);
    }

    @Test
    public void findById() {
        Product product = esRepository.findById(1l).get();
        System.out.println(product);

    }
    /**
     * term 查询
     * search(termQueryBuilder) 调用搜索方法，参数查询构建器对象
     */
    @Test
    public void termQuery(){
        Iterable<Product> products = esRepository.findProductByTitle("华为");
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
