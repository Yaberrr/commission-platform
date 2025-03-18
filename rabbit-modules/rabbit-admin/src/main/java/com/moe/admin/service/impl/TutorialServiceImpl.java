package com.moe.admin.service.impl;

import com.moe.admin.mapper.TutorialMapper;
import com.moe.admin.service.TutorialService;
import com.moe.common.core.domain.config.Tutorial;
import com.moe.admin.domain.dto.tutorial.TutorialAddDTO;
import com.moe.admin.domain.dto.tutorial.TutorialSortDTO;
import com.moe.admin.domain.vo.user.TutorialDetailVO;
import com.moe.admin.domain.vo.user.TutorialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TutorialServiceImpl implements TutorialService {

    @Autowired
    private TutorialMapper tutorialMapper;

    @Override
    public List<TutorialVO> getAllTutorial() {
        return tutorialMapper.selectAllTutorial();
    }

    @Override
    public TutorialDetailVO selectByTutorialId(Long id) {
        return tutorialMapper.selectTutorialById(id);
    }

    @Override
    public int addTutorial(TutorialAddDTO tutorialAddDTO) {
        return tutorialMapper.insertTutorial(tutorialAddDTO);
    }

    @Override
    public int updateTutorial(TutorialAddDTO tutorialAddDTO) {
        return tutorialMapper.updateTutorial(tutorialAddDTO);
    }

    @Override
    public int sortTutorial(TutorialSortDTO tutorialSortDTO) {
        List<Tutorial> list = tutorialMapper.getAll();
        list.sort(Comparator.comparingInt(Tutorial::getSort));

        int currentIndex = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(tutorialSortDTO.getId())) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            return 0;
        }

        if (tutorialSortDTO.getType() == 1) {
            if (currentIndex > 0) {
                Tutorial current = list.get(currentIndex);
                Tutorial previous = list.get(currentIndex - 1);

                int tempSort = current.getSort();
                current.setSort(previous.getSort());
                previous.setSort(tempSort);
            }
        } else if (tutorialSortDTO.getType() == 2) {
            if (currentIndex < list.size() - 1) {
                Tutorial current = list.get(currentIndex);
                Tutorial next = list.get(currentIndex + 1);

                int tempSort = current.getSort();
                current.setSort(next.getSort());
                next.setSort(tempSort);
            }
        } else if (tutorialSortDTO.getType() == 3) {
            for (Tutorial tutorial : list) {
                if (!tutorialSortDTO.getId().equals(tutorial.getId())) {
                    tutorial.setSort(tutorial.getSort() + 1);
                } else {
                    tutorial.setSort(0);
                }
            }
        }
        for (Tutorial tutorial : list) {
            tutorialMapper.updateSort(tutorial);
        }
        return 1;
    }

    @Override
    public int deleteTutorial(Long id) {
        return 0;
    }
}
