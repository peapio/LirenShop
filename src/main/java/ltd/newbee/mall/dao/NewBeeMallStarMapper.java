package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.NewBeeMallStars;

import java.util.List;

public interface NewBeeMallStarMapper {
    int deleteByPrimaryKey(int starId);
    Long[] selectByUserId(long userId);
    int insertStar(NewBeeMallStars newBeeMallStars);
    List<Integer> selectByUserIdAndGoodId(long goodId,long userId);
}
