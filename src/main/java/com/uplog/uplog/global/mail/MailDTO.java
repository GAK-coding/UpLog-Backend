package com.uplog.uplog.global.mail;

import com.uplog.uplog.domain.team.model.PowerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MailDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailRequest{
        private String email;
        private int type;
        private String link;
        private PowerType powerType;
    }
}
