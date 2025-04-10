package com.moe.common.core.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tangyabo
 * @date 2025/4/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PairVO<K,V> {

    private K key;

    private V value;

}
