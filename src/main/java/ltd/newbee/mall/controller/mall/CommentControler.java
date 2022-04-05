package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.entity.NewBeeMallComment;
import ltd.newbee.mall.service.NewBeeMallCommentService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentControler {


    @Autowired
    NewBeeMallCommentService newBeeMallCommentService;


    @PostMapping("add-comment")
    @ResponseBody
    public Result addToNewBeeMallStar(@RequestBody NewBeeMallComment newBeeMallComment , HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        newBeeMallComment.setUserId(user.getUserId());
        newBeeMallComment.setCreateTime(new Date());
        //添加成功
        String saveResult = newBeeMallCommentService.addComment(newBeeMallComment);
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult(saveResult);
        }
        //添加失败
    }
}
