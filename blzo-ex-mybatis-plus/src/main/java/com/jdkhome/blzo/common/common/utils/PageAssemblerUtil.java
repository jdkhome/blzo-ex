package com.jdkhome.blzo.common.common.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jdkhome.blzo.ex.utils.tools.AssemblerUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageAssemblerUtil {
    public static <T, D> IPage<D> pageTo(IPage<T> origPage, Class<D> c) throws  Exception{
        Page<D> page = new Page<D>();
        page.setCurrent(origPage.getCurrent());
        page.setSize(origPage.getSize());
        page.setAsc(origPage.ascs());
        //page.setAscs(new ArrayList().a);
        page.setDesc(origPage.descs());
        //page.setDescs(origPage.descs());
        page.setOptimizeCountSql(origPage.optimizeCountSql());
        page.setTotal(origPage.getTotal());
        page.setPages(origPage.getPages());
        page.setRecords(AssemblerUtil.listTo(origPage.getRecords(), c));

        return page;

    }






}
