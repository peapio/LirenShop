package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallCommentVO;
import ltd.newbee.mall.dao.MallUserMapper;
import ltd.newbee.mall.dao.NewBeeMallCommentMapper;
import ltd.newbee.mall.dao.NewBeeMallOrderItemMapper;
import ltd.newbee.mall.dao.NewBeeMallOrderMapper;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.entity.NewBeeMallComment;
import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.entity.NewBeeMallOrderItem;
import ltd.newbee.mall.service.NewBeeMallCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewBeeMallCommentServiceImpl implements NewBeeMallCommentService {
    @Autowired
    NewBeeMallCommentMapper newBeeMallCommentMapper;
    @Autowired
    MallUserMapper mallUserMapper;
    @Autowired
    NewBeeMallOrderMapper newBeeMallOrderMapper;
    @Autowired
    NewBeeMallOrderItemMapper newBeeMallOrderItemMapper;
    @Override
    public String addComment(NewBeeMallComment newBeeMallComment) {
        if(newBeeMallCommentMapper.insertComment(newBeeMallComment)>0){
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public List<NewBeeMallCommentVO> showComment(long goodsId) {
        String starDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(starDateFormat);
        List<NewBeeMallCommentVO> commentVOS = new ArrayList<>();
        for (NewBeeMallComment comment : newBeeMallCommentMapper.selectByGoodsId(goodsId)) {
            NewBeeMallCommentVO commentVO = new NewBeeMallCommentVO();
            MallUser mallUser = mallUserMapper.selectByPrimaryKey(comment.getUserId());
            commentVO.setContext(comment.getContext());
            commentVO.setGoodsId(comment.getGoodsId());
            commentVO.setNickName(mallUser.getNickName());
            commentVO.setStarCnt(comment.getPoint());
            commentVO.setCreateTime(sdf.format(comment.getCreateTime()));
            commentVO.setCommentId(comment.getCommentId());
            commentVOS.add(commentVO);
        }
        return commentVOS;
    }
    /*
    * 判断商品是否被用户买过
    * */
    @Override
    public boolean isOrdered(long userId, long goodsId) {

//        根据用户ID得到订单ID（list)
        List<NewBeeMallOrder> newBeeMallOrders = newBeeMallOrderMapper.selectByUserId(userId);
        for (NewBeeMallOrder newBeeMallOrder : newBeeMallOrders) {
//        每个订单ID得到每个订单物品项goodsID
            List<NewBeeMallOrderItem> newBeeMallOrderItems = newBeeMallOrderItemMapper.selectByOrderId(newBeeMallOrder.getOrderId());
            for (NewBeeMallOrderItem newBeeMallOrderItem : newBeeMallOrderItems) {
                if (newBeeMallOrderItem.getGoodsId()==goodsId) {
                    return true;
                }
            }
        }
//        判断

        return false;
    }

    @Override
    public boolean deleteComment(long commentId) {
        return newBeeMallCommentMapper.deleteComment(commentId)>0;
    }
}
