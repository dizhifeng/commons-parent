package org.minz.commons.springmvc.data;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

@ApiModel("分页响应对象")
public class PagingResponse<T> extends Response {

  @ApiModelProperty("分页对象")
  @Getter
  private Pagenation paging;

  public PagingResponse(int page, int pageSize, long total, Object content) {
    setCode(DEAFAULT_SUCCESS_CODE);
    setMsg(DEAFAULT_SUCCESS_MSG);
    long pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
    boolean last = (page + 1) * pageSize >= total;
    this.paging = new Pagenation(total, pageCount, page, pageSize, last);
    setData(content);
  }

  public PagingResponse(Page<T> page) {
    setCode(DEAFAULT_SUCCESS_CODE);
    setMsg(DEAFAULT_SUCCESS_MSG);
    this.paging = new Pagenation(page.getTotalElements(), page.getTotalPages(),
        page.getNumber() + 1, page.getSize(), page.isLast());
    setData(page.getContent());
  }

  public PagingResponse(Page<T> page, List list) {
    setCode(DEAFAULT_SUCCESS_CODE);
    setMsg(DEAFAULT_SUCCESS_MSG);
    this.paging = new Pagenation(page.getTotalElements(), page.getTotalPages(),
        page.getNumber() + 1, page.getSize(), page.isLast());
    setData(list);
  }

  public static void pageToHeader(HttpServletResponse response, Page pagingData) {
    Map<String, Object> paging = new HashMap<>();
    paging.put("page", pagingData.getNumber() + 1);
    paging.put("pageSize", pagingData.getSize());
    paging.put("total", pagingData.getTotalElements());
    paging.put("pageCount", pagingData.getTotalPages());
    response.setHeader("paging", JSON.toJSONString(paging));
  }

  /**
   * 分页数据
   */
  @Data
  @AllArgsConstructor
  private class Pagenation {

    /**
     * 数据总数
     */
    @ApiModelProperty("记录总数")
    private long total;

    /**
     * 页码总数
     */
    @ApiModelProperty("页码总数")
    private long pageCount;

    /**
     * 当前页码
     */
    @ApiModelProperty("当前页码，第一页为1")
    private int page;

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小，每页返回多少条数据")
    private int pageSize;

    /**
     * 是否最后一页
     */
    @ApiModelProperty("是否最后一页")
    private boolean last;
  }
}
