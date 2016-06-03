package com.nzion.superbill.dto;


import java.math.BigDecimal;

import com.nzion.domain.billing.PaymentType;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/14/14
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaymentDto {

    public PaymentType paymentMode;

    public BigDecimal amount;
}
