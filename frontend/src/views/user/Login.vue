<template>
  <v-container fluid fill-height class="pt-0">
    <v-row class="fill-height">
      <v-col cols="12" md="6" class="pa-0">
        <v-parallax src="../../assets/images/Logo.png" style="height:100%; min-height: 350px;">
          <v-row align="center" justify="center">
            <v-col cols="12" style="margin-bottom:100px; margin-left:50px">
              <p
                class="mt-10 mb-5 font-weight-bold"
                style="color:rgb(239,91,91); font-size:82px; font-family: Cafe24Oneprettynight; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;"
              >Course Mos</p>
            </v-col>
          </v-row>
        </v-parallax>
      </v-col>
      <v-col cols="12" md="6" style="align-self: flex-end;">
        <template>
          <v-row>
            <v-col cols="11" sm="6" offset-sm="3" class="mx-auto pa-0">
              <v-text-field
                v-model="email"
                name="input-10-1"
                color="#0c6212"
                label="이메일"
                placeholder="이메일를 입력하세요."
                :error-messages="error.email"
                outlined
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="11" sm="6" offset-sm="3" class="mx-auto pa-0">
              <v-text-field
                v-model="password"
                :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                :type="passwordShow ? 'text' : 'password'"
                name="input-10-1"
                color="#0c6212"
                label="비밀번호를 입력하세요."
                :error-messages="error.password"
                outlined
                @click:append="passwordShow = !passwordShow"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col class="text-center py-0" cols="12">
              <div class>
                <v-btn
                  class="teal darken-4 white--text"
                  @click="onLogin"
                  :disabled="!isSubmit"
                  :class="{disabled : !isSubmit}"
                >로그인</v-btn>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <v-row class="text-left">
                <v-col>
                  <p style="font-family: 'Noto Sans KR', sans-serif;">
                    비밀번호를 잊으셨나요?
                    <span>
                      <a
                        @click="onClickFindPwd()"
                        style="color:rgb(239,91,91); font-family: 'Noto Sans KR', sans-serif;"
                        class="float-right btn--text text-decoration-none font-weight-bold"
                      >비밀번호 찾기</a>
                    </span>
                  </p>

                  <p style="font-family: 'Noto Sans KR', sans-serif;">
                    아직 회원이 아니신가요?
                    <span>
                      <a
                        @click="onClickJoin()"
                        style="color:rgb(239,91,91); font-family: 'Noto Sans KR', sans-serif;"
                        class="float-right btn--text text-decoration-none font-weight-bold"
                      >가입하기</a>
                    </span>
                  </p>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </template>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
// import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import KakaoLogin from "../../components/user/snsLogin/Kakao.vue";
import GoogleLogin from "../../components/user/snsLogin/Google.vue";
import UserApi from "../../api/UserApi";
import axios from "axios";

export default {
  components: {
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
      this.checkPasswordForm();
    },
    email: function (v) {
      this.checkEmailForm();
    },
  },
  methods: {
    checkForm() {
      if(!this.error.password && !this.error.email) this.isSubmit = true;
      else this.isSubmit = false;
    },
    checkPasswordForm() {

      if (
        this.password.length >= 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
      this.checkForm();
    },
    checkEmailForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      this.checkForm();
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
            alert("로그인에 성공하였습니다.");
            localStorage.setItem("token", response.data.object.token);
            localStorage.setItem("userId", response.data.object.user.userId);
            localStorage.setItem("email", response.data.object.user.email);

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
    onClickJoin() {
      this.$router.push("/user/join");
    },
    onClickFindPwd() {
      this.$router.push("/user/find/password");
    }
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
      passwordShow: false,
    };
  },
};
</script>

<style scoped>
@font-face {
  font-family: "Cafe24Oneprettynight";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Cafe24Oneprettynight.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);

.notosanskr * {
  font-family: "Noto Sans KR", sans-serif;
}
</style>

