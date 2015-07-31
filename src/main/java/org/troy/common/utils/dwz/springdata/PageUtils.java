package org.troy.common.utils.dwz.springdata;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.troy.common.utils.dwz.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 解决dwz page 的遗留问题，使程序更易移植和替换
 * 
 * @author wangj
 * 2013-5-17
 */

public class PageUtils {

    private static final String DATA = "data";

    private static final String PAGE = "page";

    /**
     * 生成spring data JPA 对象 描述
     * 
     * @param page
     * @return
     */
    public static Pageable createPageable(Page page) {
        if (StringUtils.isNotBlank(page.getOrderField())) {
            // 忽略大小写
            if (page.getOrderDirection().equalsIgnoreCase(Page.ORDER_DIRECTION_ASC)) {
                return new PageRequest(page.getPlainPageNum() - 1, page.getNumPerPage(),
                    Sort.Direction.ASC, page.getOrderField());
            } else {
                return new PageRequest(page.getPlainPageNum() - 1, page.getNumPerPage(),
                    Sort.Direction.DESC, page.getOrderField());
            }
        }

        return new PageRequest(page.getPlainPageNum() - 1, page.getNumPerPage());
    }

    /**
     * 将springDataPage的属性注入page 描述
     * 
     * @param page
     * @param springDataPage
     */
    public static void injectPageProperties(Page page,
                                            org.springframework.data.domain.Page<?> springDataPage) {
        page.setTotalCount(springDataPage.getTotalElements());
    }

    /**
     * 将Page对象 和 dataList对象组装进json字符串中
     * 
     * @param page
     * @param dataList
     * @return
     */
    public static String toJsonString(Page page, List<?> dataList) {
        JSONObject json = new JSONObject();
        JSONObject pageJson = (JSONObject) JSON.toJSON(page);
        JSONArray dataListJson = (JSONArray) JSON.toJSON(dataList);
        json.put(DATA, dataListJson != null ? dataListJson : new JSONArray());
        json.put(PAGE, pageJson != null ? pageJson : new JSONObject());
        return json.toJSONString();
    }

    public static <T> List<T> getListData(org.springframework.data.domain.Page<T> springDataPage,
                                          Page page) {
        page.setTotalCount(springDataPage.getTotalElements());
        return springDataPage.getContent();
    }
}
