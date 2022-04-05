package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.NewBeeMallComment;

public interface NewBeeMallCommentMapper {
    int insertComment(NewBeeMallComment newBeeMallComment);
    NewBeeMallComment[] selectByGoodsId(long goodsId);
}
