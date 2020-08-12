

<template>
  <v-container fluid fill-height class="pt-0">

    <v-row class="fill-height">
      <v-col cols="12" md="6" class="pa-0">
        <v-parallax
          src="..\..\assets\images\Logo.png"
          style="height:100%; min-height: 350px;"
          >
          <v-row
            align="center"
            justify="center"
          >
            <v-col class="text-center" cols="12">
              <h1 class="mt-10 mb-5 font-weight-bold text-center text-sm-h1 text-h2 deep-orange--text text--darken-1">Cosmos</h1>
            </v-col>
          </v-row>
        </v-parallax>
      </v-col>
      <v-col cols="12" md="6" style="align-self: flex-end;">
        <template>
          <v-row>
            <v-col cols="12" sm="6" offset-sm="3">
              <v-text-field
                v-model="email"
                name="input-10-1"
                label="이메일을 입력하세요."
                hint="이메일 형식이 아닙니다."
                counter
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="12" sm="6" offset-sm="3">
              <v-text-field
                v-model="password"
                :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                :type="passwordShow ? 'text' : 'password'"
                name="input-10-1"
                label="비밀번호를 입력하세요."
                hint="영문,숫자 포함 8 자리이상이어야 합니다."
                counter
                @click:append="passwordShow = !passwordShow"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col class="text-center" cols="12">
              <div class="my-2">
                <v-btn class="deep-orange white--text"
                  @click="onLogin"
                  :disabled="!isSubmit"
                  :class="{disabled : !isSubmit}">로그인</v-btn>
              </div>
            </v-col>
          </v-row>


          <v-row>
            <v-col>
              <v-subheader inset style="justify-content: flex-end;">
                혹시
              </v-subheader>
              <v-divider class="deep-orange"
              ></v-divider>

              <v-row class="text-right">
                <v-col>
                  <p>비밀번호를 잊으셨나요? 
                    <span>
                      <a href="#/user/find/password" class="btn--text text-decoration-none deep-orange--text font-weight-bold">비밀번호 찾기</a>
                    </span>
                  </p>
                  
                  <p>아직 회원이 아니신가요?
                    <span>
                      <a href="#/user/join" class="btn--text text-decoration-none deep-orange--text font-weight-bold">가입하기</a>
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
      passwordShow: false,
    };
  },
};
</script>

<style scoped>
/* .logo {
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
} */
</style>

