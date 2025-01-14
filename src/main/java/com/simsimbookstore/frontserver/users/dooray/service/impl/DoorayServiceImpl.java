package com.simsimbookstore.frontserver.users.dooray.service.impl;
import com.simsimbookstore.frontserver.users.dooray.dto.DoorayMessageDto;
import com.simsimbookstore.frontserver.users.dooray.feign.DoorayServiceClient;
import com.simsimbookstore.frontserver.users.dooray.service.DoorayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class DoorayServiceImpl implements DoorayService {
    private final DoorayServiceClient doorayServiceClient;


    @Override
    public String sendReleaseCode(){
        SecureRandom random = new SecureRandom();
        int number = random.nextInt(10000);
        String code = String.format("%04d", number);

        DoorayMessageDto doorayMessageDto = DoorayMessageDto.builder()
                .botName("심심북 스토어")
                .text("휴면 계정 해제 코드")
                .attachments(new ArrayList<>())
                .build();

        doorayMessageDto.addAttachment(
                DoorayMessageDto.Attachment.builder()
                        .title("심심북 스토어")
                        .text(String.format("[심심북 스토어] 인증 번호는 [%s] 입니다.", code))
                        .titleLink("https://simsimbook.store")
                        .color("black")
                        .build());

        doorayServiceClient.sendMessage(doorayMessageDto);
        return code;
    }
}
