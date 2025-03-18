package com.moe.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.tutorial.TutorialAddDTO;
import com.moe.admin.domain.dto.tutorial.TutorialSortDTO;
import com.moe.admin.domain.vo.user.TutorialDetailVO;
import com.moe.admin.domain.vo.user.TutorialVO;

import java.util.List;

public interface TutorialService {

    Page<TutorialVO> getAllTutorial(IPage page);

    TutorialDetailVO selectByTutorialId(Long id);

    int addTutorial(TutorialAddDTO tutorialAddDTO);

    int updateTutorial(TutorialAddDTO tutorialAddDTO);

    int sortTutorial(TutorialSortDTO tutorialSortDTO);

    int deleteTutorial(Long id);
}
