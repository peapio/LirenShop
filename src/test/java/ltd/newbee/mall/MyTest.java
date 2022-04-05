package ltd.newbee.mall;

import ltd.newbee.mall.controller.vo.NewBeeMallCommentVO;
import ltd.newbee.mall.dao.NewBeeMallCommentMapper;
import ltd.newbee.mall.dao.NewBeeMallStarMapper;
import ltd.newbee.mall.entity.NewBeeMallComment;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallCommentService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.service.impl.NewBeeMallCommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyTest {
    @Autowired
    NewBeeMallCommentService newBeeMallCommentService;

    @Test
    public void test() {
        List<NewBeeMallCommentVO> commentVOS = newBeeMallCommentService.showComment(10908);
        for (NewBeeMallCommentVO commentVO : commentVOS) {
            System.out.println(commentVO);
        }

    }
}
