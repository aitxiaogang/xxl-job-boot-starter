package com.xiaogang.xxljobadminsdk.constants;

import lombok.Getter;

/**
 * @author: xiaogang
 */
@Getter
public enum GlueTypeEnum {
    BEAN("BEAN"),
    GLUE_GROOVY("GLUE(Java)"),
    GLUE_SHELL("GLUE(Shell)"),
    GLUE_PYTHON("GLUE(Python)"),
    GLUE_PHP("GLUE(PHP)"),
    GLUE_NODEJS("GLUE(Nodejs)"),
    GLUE_POWERSHELL("GLUE(PowerShell)"),
    ;
    private String remark;

    GlueTypeEnum(String remark) {
        this.remark = remark;
    }
}
