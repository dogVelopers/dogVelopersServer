package com.dogvelopers.dogvelopers.dto.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter // bean 주입해야하니까 setter 꼭 들어가야함
@ConfigurationProperties(prefix = "cloud.aws.s3") // aws bucket 값을 받아올 수 있도록
@Component
public class S3Component {

    private String bucket;

}