package com.naim.recruitingapp.resource;

import com.naim.recruitingapp.model.Application;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;


@Getter
public class ApplicationResource extends ResourceSupport {
    private final Application application;

    public ApplicationResource(Application application) {
        this.application = application;

        final Long id = application.getId();
    }
}
