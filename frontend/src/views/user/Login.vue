

<template>
  <div class="user" id="login">
    <div class="wrapC">
      <br />
      <div
        class="mt-10 mb-5 font-weight-bold text-center text-sm-h1 text-h2 deep-orange--text text--darken-1"
      >Cosmos</div>
      <div class="logo">
        <img width="100px" height="100px" src="..\..\assets\images\Logo.png" alt="logo" />
      </div>
      <div class="input-with-label">
        <input
          v-model="email"
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
          @keyup.enter="Login"
          id="email"
          placeholder="이메일을 입력하세요."
          type="text"
          autocapitalize="none"
        />
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="password"
          type="password"
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password"
          @keyup.enter="Login"
          placeholder="비밀번호를 입력하세요."
        />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>
      <button
        class="btn btn--back btn--login"
        @click="onLogin"
        :disabled="!isSubmit"
        :class="{disabled : !isSubmit}"
      >로그인</button>

      <!-- <div class="sns-login">
        <div class="text">
          <p>SNS 간편 로그인</p>
          <div class="bar"></div>
        </div>

        <kakaoLogin :component="component" />
        <GoogleLogin :component="component" />
      </div>-->
      <div class="add-option">
        <div class="text">
          <p>혹시</p>
          <div class="bar"></div>
        </div>
        <div class="wrap">
          <p>비밀번호를 잊으셨나요?</p>
          <router-link to="/user/find/password" class="btn--text">비밀번호 찾기</router-link>
        </div>
        <div class="wrap">
          <p>아직 회원이 아니신가요?</p>
          <router-link to="/user/join" class="btn--text">가입하기</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
import GoogleLogin from "../../components/user/snsLogin/Google.vue";
import UserApi from "../../api/UserApi";
import axios from "axios";

export default {
  components: {
    // KakaoLogin,
    // GoogleLogin,
  },
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
  watch: {
    password: function (v) {
      this.checkForm();
    },
    email: function (v) {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onLogin() {
      if (this.isSubmit) {
        let { email, password } = this;
        let data = {
          email,
          password,
        };
        this.isSubmit = false;
        UserApi.requestLogin(
          data,
          (response) => {
            console.log("로그인 성공");
            alert("로그인에 성공하였습니다.");
            localStorage.setItem("token", response.data.object.token);
            localStorage.setItem("userId", response.data.object.user.userId);
            localStorage.setItem("email", response.data.object.user.email);
            // this.$store.state.token = response.data.object.token;
            // this.$store.state.userId = response.data.object.user.userId;
            // this.$store.state.email = response.data.object.user.email;

            this.isSubmit = true;
            this.$router.push("/feed/main");
          },
          (error) => {
            alert("로그인에 실패하였습니다.");
            this.isSubmit = true;
          }
        );
      }
    },
  },

  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        passowrd: false,
      },
      isSubmit: false,
      component: this,
    };
  },
};
</script>

<style scoped>
.logo {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}
.add-option {
  margin-top: 50px;
}
.btn--login {
  background-color: rgb(244, 81, 30);
}
.input-with-label > input {
  border-color: rgb(244, 81, 30);
}
</style>

