package org.minz.commons.springmvc.data;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author zhengmin
 * @created 2020-09-24
 */
@Data
public class PagingQueryParam {

  @ApiParam("页码")
  private int page = 1;

  @ApiParam("每页条数")
  private int pageSize = 10;

  public Pageable toPageable() {
    return PageRequest.of(page - 1, pageSize);
  }

  public Pageable toPageable(Sort sort) {
    return PageRequest.of(page - 1, pageSize, sort);
  }
}
