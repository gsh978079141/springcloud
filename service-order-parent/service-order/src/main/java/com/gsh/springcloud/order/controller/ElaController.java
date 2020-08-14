package com.gsh.springcloud.order.controller;

import com.gsh.springcloud.order.elmodel.ElaModel;
import com.gsh.springcloud.order.service.OrderService;
import com.gsh.springcloud.order.service.es.ElaDao;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author gsh
 * @since 2018-11-14
 */
@RestController
@RequestMapping("/ela")
//@RefreshScope
public class ElaController {

  private static final String INDEX_NAME = "elaModel";

  @Resource
  OrderService orderService;

  @Resource
  ElaDao elaDao;

//  @PostMapping("/addOrderIndex")
//  public void addOrderIndex() {
//    List<Orders> orders = orderService.selectList();
//    List<IndexQuery> indexQueries = Lists.newArrayList();
//    orders.forEach(t -> {
//      IndexQuery tmp = new IndexQuery();
//      tmp.setId(t.getId().toString());
//      tmp.setSource(t.toString());
//      tmp.setIndexName(INDEX_NAME);
//      tmp.setType(INDEX_NAME);
//    });
//    elaService.addIndex(indexQueries);
//  }

  @PostMapping("/save")
  public void save(@RequestBody ElaModel elaModel) {
    elaDao.save(elaModel);
  }

  @GetMapping("getById")
  public Optional<ElaModel> getById(@RequestParam("id") String id) {
    return elaDao.findById(id);
  }

  @GetMapping("getByConditions")
  public Iterable<ElaModel> getByConditions(@RequestBody ElaModel elaModel) {
    return elaDao.search(QueryBuilders.fuzzyQuery("name", elaModel.getName()));
  }

  @GetMapping("/getAll")
  public Iterable<ElaModel> getAll() {
    return elaDao.findAll();
  }


}

