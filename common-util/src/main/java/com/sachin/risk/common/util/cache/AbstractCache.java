package com.sachin.risk.common.util.cache;

import java.util.Date;

/**
 * @author shicheng.zhang
 * @since 17-4-24 下午9:20
 */
public abstract class AbstractCache implements Cache {

    /**
     * 上次加载时数据库的最后更新时间
     */
    private Date lastTime;

    /**
     * 上次更新的个数
     */
    private int lastCount;

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public int getLastCount() {
        return lastCount;
    }

    public void setLastCount(int lastCount) {
        this.lastCount = lastCount;
    }
}
