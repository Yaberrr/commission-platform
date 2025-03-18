package com.moe.admin;

import com.moe.admin.service.*;
import com.moe.admin.domain.dto.order.OrderListDTO;
import com.moe.admin.domain.dto.rule.PolicyConfigDTO;
import com.moe.admin.domain.dto.rule.RuleConfigDTO;
import com.moe.admin.domain.dto.tutorial.TutorialAddDTO;
import com.moe.admin.domain.dto.tutorial.TutorialSortDTO;
import com.moe.admin.domain.dto.user.UserDTO;
import com.moe.admin.domain.dto.user.UserFeedBackDTO;
import com.moe.admin.domain.dto.user.UserFeedbackUpdateDTO;
import com.moe.admin.domain.dto.user.UserInviteDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author tangyabo
 * @date 2025/3/12
 */
@SpringBootTest(classes = MoeAdminApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSms {

    @Autowired
    private ISysOrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFeedbackService userFeedbackService;

    @Autowired
    public TutorialService tutorialService;

    @Autowired
    private UserInviteService userInviteService;

    @Autowired
    private RuleConfigService ruleConfigService;

    @Autowired
    private PolicyConfigService policyConfigService;

    @Test
    public void orderTest(){
//        System.out.println(orderService.selectOrder(new OrderListDTO()));
    }

//    @Test
//    public void userTest(){
//        System.out.println(userService.selectUserVOByDTO(new UserDTO()));
//    }

    @Test
    public void userIdTest(){
        System.out.println(userService.selectUserDetailByUserId(1L));
    }

//    @Test
//    public void userFeedbackTest1(){
//        System.out.println(userFeedbackService.selectUserFeedbackByDTO(new UserFeedBackDTO()));
//    }

    @Test
    public void userFeedbackTest2(){
        System.out.println(userFeedbackService.selectUserFeedbackById(1L));
    }

    @Test
    public void userFeedbackTest3(){
        UserFeedbackUpdateDTO userFeedbackUpdateDTO = new UserFeedbackUpdateDTO();
        userFeedbackUpdateDTO.setReplyImg("1111");
        userFeedbackUpdateDTO.setReplyContent("666");
        userFeedbackUpdateDTO.setId(1L);
        System.out.println(userFeedbackService.updateUserFeedback(userFeedbackUpdateDTO));
    }

//    @Test
//    public void tutorialTest1(){
//        System.out.println(tutorialService.getAllTutorial());
//    }

    @Test
    public void tutorialTest2(){
        System.out.println(tutorialService.selectByTutorialId(1L));
    }

    @Test
    public void tutorialTest3(){
        TutorialAddDTO tutorialAddDTO = new TutorialAddDTO();
        tutorialAddDTO.setVideoCover("3213");
        tutorialAddDTO.setVideoName("666");
        tutorialAddDTO.setVideoUrl("2313122");
        System.out.println(tutorialService.addTutorial(tutorialAddDTO));
    }

    @Test
    public void tutorialTest4(){
        TutorialAddDTO tutorialAddDTO = new TutorialAddDTO();
        tutorialAddDTO.setId(2L);
        tutorialAddDTO.setVideoCover("111");
        tutorialAddDTO.setVideoName("111");
        tutorialAddDTO.setVideoUrl("2222");
        System.out.println(tutorialService.updateTutorial(tutorialAddDTO));
    }

    @Test
    public void tutorialTest5(){
        TutorialSortDTO tutorialSortDTO = new TutorialSortDTO();
        tutorialSortDTO.setId(2L);
        tutorialSortDTO.setType(3);
        System.out.println(tutorialService.sortTutorial(tutorialSortDTO));
    }

//    @Test
//    public void userInviteTest1(){
//        UserInviteDTO userInviteDTO = new UserInviteDTO();
//        userInviteDTO.setLimit(10);
//        userInviteDTO.setStartTime("2025-03-14 00:00:00");
//        userInviteDTO.setEndTime("2025-03-15 00:00:00");
//        System.out.println(userInviteService.selectInviteUserRankVOByDTO(userInviteDTO));
//    }

//    @Test
//    public void ruleConfigTest1(){
//        RuleConfigDTO ruleConfigDTO = new RuleConfigDTO();
//        System.out.println(ruleConfigService.selectRuleConfigVOByUser(ruleConfigDTO));
//    }

    @Test
    public void ruleConfigTest2(){
        RuleConfigDTO ruleConfigDTO = new RuleConfigDTO();
        ruleConfigDTO.setRuleName("111");
        ruleConfigDTO.setRuleType(1);
        ruleConfigDTO.setRuleDesc("11111");
        System.out.println(ruleConfigService.addRuleConfig(ruleConfigDTO));
    }

    @Test
    public void ruleConfigTest3(){
        RuleConfigDTO ruleConfigDTO = new RuleConfigDTO();
        ruleConfigDTO.setId(1L);
        ruleConfigDTO.setRuleName("222");
        ruleConfigDTO.setRuleType(1);
        ruleConfigDTO.setRuleDesc("22222");
        System.out.println(ruleConfigService.updateRuleConfig(ruleConfigDTO));
    }
    @Test
    public void ruleConfigTest4(){
        System.out.println(ruleConfigService.selectRuleConfigVOById(1L));
    }

//    @Test
//    public void policyConfigTest1(){
//        PolicyConfigDTO policyConfigDTO = new PolicyConfigDTO();
//        System.out.println(policyConfigService.selectPolicyConfigByDTO(policyConfigDTO));
//    }

    @Test
    public void policyConfigTest2(){
        System.out.println(policyConfigService.selectPolicyConfigById(1l));
    }

    @Test
    public void policyConfigTest3(){
        PolicyConfigDTO policyConfigDTO = new PolicyConfigDTO();
        policyConfigDTO.setPolicyType(1);
        policyConfigDTO.setPolicyDesc("232");
        policyConfigDTO.setPolicyName("3223");
        System.out.println(policyConfigService.insertPolicyConfig(policyConfigDTO));
    }

    @Test
    public void policyConfigTest4(){
        PolicyConfigDTO policyConfigDTO = new PolicyConfigDTO();
        policyConfigDTO.setId(1L);
        policyConfigDTO.setPolicyType(1);
        policyConfigDTO.setPolicyDesc("444");
        policyConfigDTO.setPolicyName("35553");
        System.out.println(policyConfigService.updatePolicyConfig(policyConfigDTO));
    }

    @Test
    public void policyConfigTest5(){
        System.out.println(policyConfigService.deletePolicyConfig(1L));
    }
}
