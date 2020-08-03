package com.web.curation.model.request;

import java.io.Serializable;
import javax.persistence.Id;
import lombok.Data;

@Data
public class RequestId implements Serializable{
    @Id
    private String follower;
    @Id
    private String email;
}
