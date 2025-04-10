package com.moe.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.common.core.domain.config.Tutorial;
import com.moe.admin.domain.dto.tutorial.TutorialAddDTO;
import com.moe.admin.domain.vo.user.TutorialDetailVO;
import com.moe.admin.domain.vo.user.TutorialVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TutorialMapper extends BaseMapper<Tutorial> {

    List<TutorialVO> getTutorialList();

    TutorialDetailVO selectTutorialById(@Param("id") Long id);

    List<Tutorial> getAll();

    int updateSort(@Param("tutorial") Tutorial tutorial);

    int deleteTutorialById(@Param("id") Long id);
}
