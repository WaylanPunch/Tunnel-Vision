package com.way.tunnelvision.entity.service;

import java.util.List;

/**
 * Created by pc on 2016/3/7.
 */
public interface GreenDaoHelperInterface {
    public <T> void addData(T t);
    public void deleteData(Long id);
    public <T> T getDataById(Long id);
    public List getAllData();
    public boolean hasKey(Long id);
    public long getTotalCount();
    public void deleteAll();
}
