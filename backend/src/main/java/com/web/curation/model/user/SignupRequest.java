package com.web.curation.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Valid
@ToString
public class SignupRequest {
    @ApiModelProperty(required = true)
    @NotNull
    String email;
    @ApiModelProperty(required = true)
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
    String password;
    @ApiModelProperty(required = true)
    // @NotNull
    String profilePhoto;
    @ApiModelProperty(required = true)
    @NotNull
    String userId;
    @ApiModelProperty(required = true)
    // @NotNull
    String profileComment;

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getProfileComment() {
        return profileComment;
    }
    public String getProfilePhoto() {
        return profilePhoto;
    }
    public String getUserId() {
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setProfileComment(String profileComment) {
        this.profileComment = profileComment;
    }
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
