package com.simsimbookstore.frontserver.order.client;

import com.simsimbookstore.frontserver.order.dto.RefundResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "order-admin-refund-api-server", url = "http://localhost:8000/api/admin")
public interface OrderAdminReturnClient {

    @RequestMapping(method = RequestMethod.GET, value = "/orders/refund")
    List<RefundResponseDto> refundList();

    @RequestMapping(method = RequestMethod.POST, value = "/orders/refund/{orderBookId}/confirm")
    ResponseEntity<Void> confirmReturn(@PathVariable Long orderBookId);

    @RequestMapping(method = RequestMethod.POST, value = "/orders/refund/{orderBookId}/reject")
    ResponseEntity<Void> rejectReturn(@PathVariable Long orderBookId);
}