package com.moe.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 单一list包装
 * @author tangyabo
 * @date 2025/3/26
 */
@Data
@AllArgsConstructor
public class OnlyList<T> {

    private List<T> list;

}
