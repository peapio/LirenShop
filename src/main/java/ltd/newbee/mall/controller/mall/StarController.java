package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.UserVO;
import ltd.newbee.mall.entity.LirenMallStars;
import ltd.newbee.mall.service.OrderService;
import ltd.newbee.mall.service.ShoppingCartService;
import ltd.newbee.mall.service.StarService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class StarController {

    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private OrderService orderService;
    @Resource
    private StarService starService;
    @GetMapping("/stars")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("userId", user.getUserId());
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("starList", starService.getAllStarItems(user.getUserId(),pageUtil));
        request.setAttribute("path", "stars");
        return "mall/my-stars";
    }

    @PostMapping("/shop-star")
    @ResponseBody
    public Result addToNewBeeMallStar(@RequestBody LirenMallStars lirenMallStars,
                                                 HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        lirenMallStars.setUserId(user.getUserId());
        String saveResult = starService.addToStar(lirenMallStars);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @DeleteMapping("/cancelStar/{cancelGoodId}")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@PathVariable("cancelGoodId") Long cancelGoodId,
                                                   HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean cancelResult = starService.cancelStar(cancelGoodId, user.getUserId());
        //删除成功
        if (cancelResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }


}
