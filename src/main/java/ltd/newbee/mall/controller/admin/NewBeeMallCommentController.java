package ltd.newbee.mall.controller.admin;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallCommentVO;
import ltd.newbee.mall.controller.vo.NewBeeMallOrderItemVO;
import ltd.newbee.mall.service.NewBeeMallCommentService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NewBeeMallCommentController {

    @Autowired
    NewBeeMallCommentService newBeeMallCommentService;

    @GetMapping("/comment-items/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        List<NewBeeMallCommentVO> newBeeMallCommentVOS = newBeeMallCommentService.showComment(id);
        if (!CollectionUtils.isEmpty(newBeeMallCommentVOS)) {
            return ResultGenerator.genSuccessResult(newBeeMallCommentVOS);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    @RequestMapping(value = "/comment/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody long id) {
        if (newBeeMallCommentService.deleteComment(id)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
