package com.didi.autocheckin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didi.autocheckin.module.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (FileInfo)表数据库访问层
 * @Author: 翟有良
 */
@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<FileInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<FileInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<FileInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<FileInfo> entities);

}
