package ltd.newbee.mall.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NewBeeMallCommentVO {
    private int commentId;
    private int StarCnt;
    private String context;
    private String nickName;
    private long goodsId;
    private String createTime;
}
