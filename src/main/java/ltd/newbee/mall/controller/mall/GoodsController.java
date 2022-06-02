package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.MallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.CommentVO;
import ltd.newbee.mall.controller.vo.GoodsDetailVO;
import ltd.newbee.mall.controller.vo.UserVO;
import ltd.newbee.mall.controller.vo.SearchPageCategoryVO;
import ltd.newbee.mall.entity.LirenMallGoods;
import ltd.newbee.mall.service.CategoryService;
import ltd.newbee.mall.service.CommentService;
import ltd.newbee.mall.service.GoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private GoodsService goodsService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private CommentService commentService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = categoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", goodsService.searchNewBeeMallGoods(pageUtil));
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            MallException.fail("参数异常");
        }
        UserVO user = (UserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
        LirenMallGoods goods = goodsService.getNewBeeMallGoodsById(goodsId);
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            MallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        List<CommentVO> commentVOS = commentService.showComment(goodsId);
        boolean isOrdered = commentService.isOrdered(user.getUserId(),goodsId);
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        request.setAttribute("goodsComments",commentVOS);
        request.setAttribute("isOrdered",isOrdered);
//        得到userId得到goodsId
        //TODO 根据商品ID得到商品评论赋值给goodsComment
        return "mall/detail";
    }

}
