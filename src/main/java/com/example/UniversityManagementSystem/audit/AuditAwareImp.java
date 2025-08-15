package com.example.UniversityManagementSystem.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImp")
public class AuditAwareImp implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return Optional.of("Anonymous");  // fallback username
        }

            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());


    }


}
