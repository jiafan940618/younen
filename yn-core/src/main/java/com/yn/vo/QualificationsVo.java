package com.yn.vo;

import com.yn.model.Apolegamy;
import lombok.Data;

import java.util.Set;

/**
 * 资质
 */
@Data
public class QualificationsVo {

    protected Long id;
    private String imgUrl;
    private String text;
    private Set<ApolegamyVo> apolegamy;
}
