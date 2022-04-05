package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.NewBeeMallStars;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.util.List;

public interface NewBeeMallStarService {
    PageResult getAllStarItems(long userId, PageQueryUtil pageUtil);
    Boolean cancelStar(long goodId,long userId);
    String addToStar(NewBeeMallStars newBeeMallStars);
}
