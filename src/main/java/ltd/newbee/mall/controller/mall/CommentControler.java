package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.UserVO;
import ltd.newbee.mall.entity.Comment;
import ltd.newbee.mall.service.CommentService;
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
    CommentService commentService;


    @PostMapping("add-comment")
    @ResponseBody
    public Result addToNewBeeMallStar(@RequestBody Comment comment, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        comment.setUserId(user.getUserId());
        comment.setCreateTime(new Date());
        //添加成功
        String saveResult = commentService.addComment(comment);
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }else{
            return ResultGenerator.genFailResult(saveResult);
        }
        //添加失败
    }
}
