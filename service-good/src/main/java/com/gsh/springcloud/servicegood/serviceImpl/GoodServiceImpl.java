package com.gsh.springcloud.servicegood.serviceImpl;

import com.gsh.springcloud.servicegood.entity.Good;
import com.gsh.springcloud.servicegood.service.GoodService;
import com.gsh.springcloud.servicegood.dao.GoodMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author gsh
 * @since 2018-11-12
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

}
