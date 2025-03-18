package com.moe.admin.mapper;

import com.moe.common.core.domain.config.Tutorial;
import com.moe.admin.domain.dto.tutorial.TutorialAddDTO;
import com.moe.admin.domain.vo.user.TutorialDetailVO;
import com.moe.admin.domain.vo.user.TutorialVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TutorialMapper {

    List<TutorialVO> selectAllTutorial();

    TutorialDetailVO selectTutorialById(@Param("id") Long id);

    int insertTutorial(@Param("dto") TutorialAddDTO tutorialAddDTO);

    int updateTutorial(@Param("dto") TutorialAddDTO tutorialAddDTO);

    List<Tutorial> getAll();

    int updateSort(@Param("tutorial") Tutorial tutorial);

    int deleteTutorialById(@Param("id") Long id);
}
