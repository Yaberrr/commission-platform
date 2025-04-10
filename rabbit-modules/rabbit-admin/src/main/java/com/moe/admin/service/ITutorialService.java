package com.moe.admin.service;

import com.moe.admin.domain.dto.tutorial.TutorialAddDTO;
import com.moe.admin.domain.dto.tutorial.TutorialSortDTO;
import com.moe.admin.domain.vo.user.TutorialDetailVO;
import com.moe.admin.domain.vo.user.TutorialVO;
import com.moe.common.core.domain.config.Tutorial;

import java.util.List;

public interface ITutorialService {

    List<TutorialVO> getTutorialList();

    TutorialDetailVO selectByTutorialId(Long id);

    int addTutorial(Tutorial tutorial);

    int updateTutorial(Tutorial tutorial);

    int sortTutorial(TutorialSortDTO tutorialSortDTO);

    int deleteTutorial(Long id);
}
