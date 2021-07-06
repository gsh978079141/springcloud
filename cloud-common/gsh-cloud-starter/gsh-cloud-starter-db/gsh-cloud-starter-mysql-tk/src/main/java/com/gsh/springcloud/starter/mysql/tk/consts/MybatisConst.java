package com.gsh.springcloud.starter.mysql.tk.consts;

import org.apache.ibatis.session.RowBounds;

/**
 * @author gsh
 */
public class MybatisConst {

    public static final RowBounds DEFAULT_ROW_BOUNDS = new RowBounds(0, 1000);


    private MybatisConst() {
    }
}
