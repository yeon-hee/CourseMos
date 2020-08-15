<template>
  <v-container class="join py-0">
    <v-row>
      <v-col cols="12" md="6" class="pa-0">
        <v-parallax src="../../assets/images/Logo.png" style="height:100%; min-height: 670px;">
          <v-row justify="center">
            <v-col cols="12" style="margin-left:10px">
              <p class="mt-10 mb-0 font-weight-bold" style="color:#0c6212; font-size:50px;">비밀번호 찾기</p>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="11" sm="6" offset-sm="3" class="mx-auto pb-0">
              <!-- <label for="userId">아이디</label> -->
              <v-text-field
                v-model="userId"
                id="userId"
                label="아이디"
                color="#0c6212"
                v-bind:class="{error : error.userId, complete:!error.userId&&userId.length!==0}"
                placeholder="아이디를 입력하세요."
                type="text"
                outlined
              ></v-text-field>
              <div class="error-text" style="color:#0c6212;" v-if="error.userId">{{error.userId}}</div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="11" sm="6" offset-sm="3" class="mx-auto pt-0">
              <!-- <label for="email">이메일</label> -->
              <v-text-field
                v-model="email"
                id="email"
                label="이메일"
                color="#0c6212"
                v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
                placeholder="이메일을 입력하세요."
                type="text"
                autocapitalize="none"
                outlined
              ></v-text-field>
              <div class="error-text" style="color:#0c6212;" v-if="error.email">{{error.email}}</div>
            </v-col>
          </v-row>

          <v-row>
            <br />
            <br />
            <br />
            <br />
          </v-row>

          <v-row>
            <v-col class="text-center py-0" cols="12">
              <div class>
                <v-btn class="teal darken-4 white--text" style="width:350px;" @click="find">비밀번호 찾기</v-btn>
              </div>
            </v-col>
          </v-row>
        </v-parallax>
      </v-col>
    </v-row>
  </v-container>
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
      userId: "",
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
        term: false,
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false,
    };
  },
  watch: {
    userId: function (v) {
      this.checkIdForm();
    },
    email: function (v) {
      this.checkEmailForm();
    },
    password: function (v) {
      this.checkPasswordForm();
    },
    passwordConfirm: function (v) {
      this.checkPasswordConfirmForm();
    },
  },
  methods: {
    checkIdForm() {
      var regex = /[^A-Z^a-z^0-9]+/;
      if (
        (this.userId.length >= 0 && this.userId.length < 5) ||
        regex.exec(this.userId) != null
      ) {
        this.error.userId = "아이디는 5글자 이상, 특수문자 미포함입니다.";
      } else this.error.userId = false;
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
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    find() {
      let data = {
        userId: this.userId,
        email: this.email,
      };
      UserApi.findPassword(
        data,
        (res) => {
          console.dir(res);
          alert("비밀번호는 " + res.data + "입니다");
          this.$router.push("/");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    checkDuplicate(data) {
      UserApi.checkDuplicate();
    },
  },
};
</script>


