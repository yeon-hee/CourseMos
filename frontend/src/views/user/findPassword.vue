<template>
  <div class="user join wrapC">
    <h1>비밀번호 찾기</h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input v-model="userId" id="userId" 
          v-bind:class="{error : error.userId, complete:!error.userId&&userId.length!==0}"
          placeholder="아이디를 입력하세요." type="text" 
        />
        <div class="error-text" v-if="error.userId">{{error.userId}}</div>
        <label for="userId">아이디</label>
      </div>

      <div class="input-with-label">
        <input v-model="email" id="email" 
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}" 
          placeholder="이메일을 입력하세요." type="text" autocapitalize="none"/>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
        <label for="email">이메일</label>
      </div>
    </div>

    <button class="btn-bottom" @click="find">비밀번호 찾기</button>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import UserApi from "../../api/UserApi";
export default {
  created() {
    this.component = this;
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  data: () => {
    return {
      userId:"",
      email: "",
      password: "",
      passwordSchema: new PV(),
      passwordConfirm: "",
      isTerm: false,
      isLoading: false,
      error: {
        userId: false,
        email: false,
        password: false,
        passwordConfirm: false,
        term: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false
    };
  },
  watch: {
    userId: function(v) {
      this.checkIdForm();
    },
    email: function(v) {
      this.checkEmailForm();
    },
    password : function(v) {
      this.checkPasswordForm();
    },
    passwordConfirm : function(v) {
      this.checkPasswordConfirmForm();
    },
  },
  methods: {
    checkIdForm() {
      var regex = /[^A-Z^a-z^0-9]+/;
      if ((this.userId.length >= 0 && this.userId.length < 5) || (regex.exec(this.userId) != null) ) {
        this.error.userId = "아이디는 5글자 이상, 특수문자 미포함입니다.";
      }
      else this.error.userId = false;
      this.checkForm();
    },
    checkEmailForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email)) 
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;
      this.checkForm();
    },
    checkForm() {
      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
        find() {
        let data = {
            userId: this.userId,
            email: this.email
        }
            UserApi.findPassword( 
              data,
                res => {
                  console.dir(res);
                  alert('비밀번호는 '+ res.data + '입니다');
                  this.$router.push("/");
                },
                error => {
                    console.log(error);
                }
            );
        },
    checkDuplicate(data) {
      UserApi.checkDuplicate(
      )
    }
  },
};
</script>


