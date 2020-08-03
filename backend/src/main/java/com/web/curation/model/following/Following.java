package com.web.curation.model.following;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(FollowingId.class)
public class Following {

    @Id
    @Column(name = "follower")
    private String follower; 

    @Id
    @Column(name = "email")
    private String email;

    @Id
    @Column(name = "user_id")
    private String userId;

}