package com.fangjc1986.support.provider;

import com.fangjc1986.support.helper.SystemHelper;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SystemHelper.loginUserName());
    }
}
