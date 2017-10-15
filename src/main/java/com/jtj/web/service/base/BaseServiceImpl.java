package com.jtj.web.service.base;

import com.jtj.web.common.*;
import com.jtj.web.common.exception.AssetException;
import com.jtj.web.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2017/3/15.
 */
public class BaseServiceImpl<E extends BaseEntity,T extends BaseDto,D extends BaseDao<E,T>>
        implements BaseService<E,T> {

    @Autowired
    private D dao;


    @Override
    public ResultDto<Object> add(E t) {
        ResultDto<Object> result = new ResultDto<>();
        dao.add(t);
        result.setResultCode(ResultCode.SUCCESS_POST);
        return result;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public ResultDto<Object> delete(Long[] ids) throws AssetException {
        ResultDto<Object> result = new ResultDto<>();
        int count = dao.delete(ids);
        int all = ids.length;
        if (count == all){
            result.setResultCode(ResultCode.SUCCESS_DELETE);
            return result;
        }
        result.setResultCode(ResultCode.OPERATE_FAIL);
        result.setMessage("存在"+(all - count)+"/"+all+"数据有误！");
        throw new AssetException(result);
    }

    @Override
    public ResultDto<Object> update(E t) {
        ResultDto<Object> result = new ResultDto<>();
        dao.update(t);
        result.setResultCode(ResultCode.SUCCESS_PUT);
        return result;
    }

    @Override
    public ResultDto<PageDto<E>> getList(T dto) {
        ResultDto<PageDto<E>> result = new ResultDto<>(ResultCode.SUCCESS_GET);
        PageDto<E> page = new PageDto<>();
        page.setList(dao.getList(dto));
        page.setCount(dao.getNum(dto));
        result.setObject(page);
        return result;
    }
}