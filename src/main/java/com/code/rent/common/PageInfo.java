package com.code.rent.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
  * 通用分页返回结果
 * @author HeXin
 */
@Data
@Accessors(chain = true)
@Schema(name = "PageInfo",title = "通用分页返回结果")
public class PageInfo<T> {
    @Schema(name = "list",description = "分页详细数据")
    List<T> list;
    @Schema(name = "total",description = "分页数据总数")
    long total;
}
