// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.
package com.web.curation.model.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class User {
    // @Id
    private String userId;
    // @JsonIgnore
    private String password;
    @Id
    private String email;

    private String profilePhoto;
    private String profileComment;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createDate;

}
