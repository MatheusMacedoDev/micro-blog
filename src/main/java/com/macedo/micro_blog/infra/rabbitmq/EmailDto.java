package com.macedo.micro_blog.infra.rabbitmq;

import java.io.Serializable;

public class EmailDto implements Serializable {
    public String emailFrom;
    public String emailTo;
    public String emailSubject;
    public String emailBody;
}
