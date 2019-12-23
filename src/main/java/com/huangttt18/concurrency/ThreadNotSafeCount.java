package com.huangttt18.concurrency;

/**
 * @author Daniel 2019/12/20
 */
public class ThreadNotSafeCount {

    private Long count;

    public Long getCount() {
        return count;
    }

    public void increase() {
        ++count;
    }
}
