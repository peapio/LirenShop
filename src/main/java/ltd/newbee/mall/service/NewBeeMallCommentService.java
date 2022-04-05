package ltd.newbee.mall.service;

import ltd.newbee.mall.controller.vo.NewBeeMallCommentVO;
import ltd.newbee.mall.entity.NewBeeMallComment;

import java.util.List;

public interface NewBeeMallCommentService {
    String addComment(NewBeeMallComment newBeeMallComment);
    List<NewBeeMallCommentVO> showComment(long goodsId);
}
