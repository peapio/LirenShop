package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.dao.NewBeeMallStarMapper;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.NewBeeMallShoppingCartItem;
import ltd.newbee.mall.entity.NewBeeMallStars;
import ltd.newbee.mall.service.NewBeeMallStarService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewBeeMallStarServiceImpl implements NewBeeMallStarService {
    @Autowired
    private NewBeeMallGoodsMapper newBeeMallGoodsMapper;
    @Autowired
    private NewBeeMallStarMapper newBeeMallStarMapper;
    @Override
    public PageResult getAllStarItems(long userId, PageQueryUtil pageUtil) {
        List<NewBeeMallGoods> Results = new ArrayList<>();
        Long[] AllStarItemId = newBeeMallStarMapper.selectByUserId(userId);
        for (Long goodId : AllStarItemId) {
            NewBeeMallGoods newBeeMallGoods = newBeeMallGoodsMapper.selectByPrimaryKey(goodId);
            Results.add(newBeeMallGoods);
        }
        int total = Results.size();
        PageResult pageResult = new PageResult(Results, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean cancelStar(long goodId,long userId) {
        List<Integer> temp;
        temp = newBeeMallStarMapper.selectByUserIdAndGoodId(goodId,userId);
        if(temp.size()>0){
            for (Integer starId : temp) {
                newBeeMallStarMapper.deleteByPrimaryKey(starId);
            }
            return true;
        }
        return false;
    }

    @Override
    public String addToStar(NewBeeMallStars newBeeMallStars) {
        List<Integer> temp;
        temp = newBeeMallStarMapper.selectByUserIdAndGoodId(newBeeMallStars.getGoodId(),newBeeMallStars.getUserId());
        if (temp.size()!=0) {
            //已存在则修改该记录
            return "已收藏！";
        }
        if(newBeeMallStarMapper.insertStar(newBeeMallStars)>0){
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();

    }
}
