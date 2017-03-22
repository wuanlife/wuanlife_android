package com.wuanla.www.wuanlife_120.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by lezi on 2017/3/22.
 */
@Entity
public class Test {


    @Id(autoincrement = true)
    public Long id;


    public String name;

    @Generated
    public Test(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
