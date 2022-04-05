package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallCommentVO;
import ltd.newbee.mall.dao.MallUserMapper;
import ltd.newbee.mall.dao.NewBeeMallCommentMapper;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.entity.NewBeeMallComment;
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
            commentVO.setCreateTime(sdf.format(comment.getCreateTime()));
            commentVOS.add(commentVO);
        }
        return commentVOS;
    }
}
