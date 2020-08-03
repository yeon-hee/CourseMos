package com.web.curation.model.following;

import java.io.Serializable;
import javax.persistence.Id;
import lombok.Data;

@Data
public class FollowingId implements Serializable{
    @Id
    private String follower;
    @Id
    private String email;
    @Id
    private String userId;
}
