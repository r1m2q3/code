package com.honest.enterprise.core.utils;

import cn.hutool.core.date.DateUtil;
import com.honest.enterprise.core.auth.AuthContext;
import com.honest.enterprise.core.model.BaseModel;
import com.honest.enterprise.core.model.interfaces.BaseDateRange;
import com.honest.enterprise.core.model.interfaces.BasePageInterface;
import org.apache.commons.lang3.StringUtils;


/**
 * @Description: 填充字段工具类
 * @Author: fanjie
 * @Date: 2022-07-17 15:38:12
 */
public final class FillRequireFieldUtils {

    /**
     * 私有化构造器，防止实例化
     */
    private FillRequireFieldUtils() {
    }

    /**
     * 创建时 补充必要字段 包含初始化version
     *
     * @param data
     * @param <T>
     */
    public static <T extends BaseModel> void fillRequireFieldCreate(T data) {
        data.setCreateDt(DateUtil.date());
        data.setUpdateDt(DateUtil.date());
        data.setCreateUserId(AuthContext.getUserId());
        data.setVersion(1);
        data.setIsDeleted(0);
    }

    /**
     * 修改时 补充必要字段 不包含version
     *
     * @param data
     * @param <T>
     */
    public static <T extends BaseModel> void fillRequireFieldUpdate(T data) {
        data.setUpdateDt(DateUtil.date());
        data.setUpdateUserId(AuthContext.getUserId());
    }

    /**
     * 检查并设置默认时间周期
     *
     * @param data
     * @param <T>
     */
    public static <T extends BaseDateRange> void checkAndSetDateRang(T data) {
        String endDt = data.getEndDt();
        String startDt = data.getStartDt();
        if (StringUtils.isBlank(startDt) && StringUtils.isBlank(endDt)) {
            endDt = DateUtil.today();
            startDt = DateUtil.offsetDay(DateUtil.date(), -30).toDateStr();
            data.setStartDt(startDt);
            data.setEndDt(endDt);
        }
    }

    /**
     * 检查并设置默认分页参数
     *
     * @param data
     * @param <T>
     */
    public static <T extends BasePageInterface> void checkAndSetPageParams(T data) {
        Integer pageNum = data.getPageNum();
        if (pageNum == null || pageNum == 0) {
            data.setPageNum(1);
        }
        Integer pageSize = data.getPageSize();
        if (pageSize == null) {
            data.setPageSize(10);
        }
    }
}
