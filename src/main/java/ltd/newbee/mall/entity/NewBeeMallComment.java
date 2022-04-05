package ltd.newbee.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBeeMallComment {
    private int commentId;
    private long userId;
    private long goodsId;
    private String context;
    private int point;
    private Date createTime;
}
