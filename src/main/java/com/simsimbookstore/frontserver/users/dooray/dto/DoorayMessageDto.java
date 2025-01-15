package com.simsimbookstore.frontserver.users.dooray.dto;

import com.nimbusds.openid.connect.sdk.assurance.evidences.Attestation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoorayMessageDto {
    private String botName;
    private String text;
    private List<Attachment> attachments;

    public void addAttachment(Attachment attachment) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }
        this.attachments.add(attachment);
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Attachment{
        String title;
        String text;
        String titleLink;
        String color;
    }
}


