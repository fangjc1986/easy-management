package com.fangjc1986.support.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ParamsUtil {

    @Value("${AES_SECRET}")
    private String aesSecret;
}
