package ltd.newbee.mall.controller.vo;

import lombok.Data;

import java.util.Date;

@Data
public class NewBeeMallCommentVO {
    private String context;
    private String nickName;
    private long goodsId;
    private String createTime;
}
