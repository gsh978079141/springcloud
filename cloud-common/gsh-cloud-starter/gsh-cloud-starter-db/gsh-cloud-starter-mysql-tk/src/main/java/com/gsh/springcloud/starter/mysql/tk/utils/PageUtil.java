package com.gsh.springcloud.starter.mysql.tk.utils;

import com.gsh.springcloud.starter.mysql.tk.consts.MybatisConst;
import org.apache.ibatis.session.RowBounds;

/**
 * @author gsh
 */
public class PageUtil {

    /**
     * 根据 pageNum 和 pageSize 组装 RowBounds
     *
     * @param pageNum
     * @param pageSize
     * @return RowBounds
     */
    public static final RowBounds getRowBounds(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            return MybatisConst.DEFAULT_ROW_BOUNDS;
        }
        int offset = pageNum - 1;
        if (offset < 0) {
            offset = 0;
        }
        return new RowBounds(offset, pageSize);
    }

    public static final RowBounds getDftRowBounds() {
        return MybatisConst.DEFAULT_ROW_BOUNDS;
    }


}
