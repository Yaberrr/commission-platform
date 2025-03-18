package com.moe.admin.service;

import com.moe.common.core.domain.config.Tutorial;
import com.moe.common.core.domain.dto.tutorial.TutorialAddDTO;
import com.moe.common.core.domain.dto.tutorial.TutorialSortDTO;
import com.moe.common.core.domain.vo.user.TutorialDetailVO;
import com.moe.common.core.domain.vo.user.TutorialVO;

import java.util.List;

public interface TutorialService {

    List<TutorialVO> getAllTutorial();

    TutorialDetailVO selectByTutorialId(Long id);

    int addTutorial(TutorialAddDTO tutorialAddDTO);

    int updateTutorial(TutorialAddDTO tutorialAddDTO);

    int sortTutorial(TutorialSortDTO tutorialSortDTO);

    int deleteTutorial(Long id);
}
