<template>
  <v-container class="join py-0">
    <v-row>
      <v-col cols="12" md="6" class="pa-0">
        <v-parallax src="../../assets/images/Logo.png" style="height:100%; min-height: 670px;">
          <v-row justify="center">
            <v-col cols="12" style="margin-left:10px">
              <p class="mt-10 mb-0 font-weight-bold" style="color:#0c6212; font-size:50px;">가입하기</p>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="11" sm="6" offset-sm="3" class="mx-auto">
              <!-- <label for="email">이메일</label> -->
              <v-text-field
                v-model="email"
                id="email"
                label="이메일"
                color="#0c6212"
                v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
                placeholder="이메일을 입력하세요."
                type="text"
                outlined
              >
                <div class="error-text" style="color:#0c6212;" v-if="error.email">{{error.email}}</div>
              </v-text-field>
              <div class="error-text" style="color:#0c6212;" v-if="error.email">{{error.email}}</div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="11" sm="6" offset-sm="3" class="mx-auto pt-0">
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
              <!-- <label for="email">비밀번호</label> -->
              <v-text-field
                v-model="password"
                id="password"
                :type="passwordType"
                v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
                placeholder="비밀번호를 입력하세요."
                label="비밀번호"
                color="#0c6212"
                autocapitalize="none"
                outlined
              ></v-text-field>
              <div class="error-text" v-if="error.password">{{error.password}}</div>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="11" sm="6" offset-sm="3" class="mx-auto pt-0">
              <!-- <label for="email">비밀번호</label> -->
              <v-text-field
                v-model="passwordConfirm"
                :type="passwordConfirmType"
                v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!=0}"
                id="password-confirm"
                placeholder="비밀번호를 다시한번 입력하세요."
                label="비밀번호 확인"
                color="#0c6212"
                autocapitalize="none"
                outlined
              ></v-text-field>
              <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
            </v-col>
          </v-row>

          <v-row justify="center">
            <label>
              <input v-model="isTerm" type="checkbox" id="term" />
              <span style="color:black;">약관을 동의합니다.</span>
            </label>
            <span class="ml-5" style="color:black;" @click="termPopup=true">약관보기</span>
          </v-row>

          <v-row>
            <br />
          </v-row>

          <v-row>
            <v-col class="text-center py-0" cols="12">
              <div class>
                <v-btn class="teal darken-4 white--text" style="width:350px;" @click="signup">가입하기</v-btn>
              </div>
            </v-col>
          </v-row>

          <v-row>
            <br />
          </v-row>
        </v-parallax>
      </v-col>
    </v-row>
  </v-container>

  <!-- <div class="user join wrapC">
    <h1>가입하기</h1>
    <div class="form-wrap">
      <div class="input-with-label">
        <input v-model="email" id="email" 
          v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}" 
          placeholder="이메일을 입력하세요." type="text" autocapitalize="none"/>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
        <label for="email">이메일</label>
      </div>

      <div class="input-with-label">
        <input v-model="userId" id="userId" 
          v-bind:class="{error : error.userId, complete:!error.userId&&userId.length!==0}"
          placeholder="닉네임을 입력하세요." type="text" 
        />
        <div class="error-text" v-if="error.userId">{{error.userId}}</div>
        <label for="userId">닉네임</label>
      </div>

      <div class="input-with-label">
        <input v-model="password" id="password" 
          :type="passwordType" v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}" 
          placeholder="비밀번호를 입력하세요." />
        <div class="error-text" v-if="error.password">{{error.password}}</div>
        <label for="password">비밀번호</label>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!=0}"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
        <label for="password-confirm">비밀번호 확인</label>
      </div>
    </div>

    <label>
      <input v-model="isTerm" type="checkbox" id="term" />
      <span>약관을 동의합니다.</span>
    </label>

    <span @click="termPopup=true">약관보기</span>

    <button class="btn-bottom" @click="signup">가입하기</button>
  </div>-->
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
        this.error.userId = "5~20자의 영문 소문자, 숫자만 사용 가능합니다.";
      } else this.error.userId = false;
      this.checkForm();
    },
    checkEmailForm() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;
      this.checkForm();
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
    checkPasswordConfirmForm() {
      if (this.passwordConfirm === this.password) {
        this.error.passwordConfirm = false;
      } else this.error.passwordConfirm = "비밀번호가 일치하지 않습니다.";
      this.checkForm();
    },
    checkForm() {
      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    signup() {
      if (this.isSubmit) {
        let { userId, email, password } = this;
        let data = {
          userId,
          email,
          password,
        };
        console.dir(data);
        UserApi.requestSignup(
          data,
          (res) => {
            console.log(res);
            this.isSubmit = true;
            alert("회원가입이 성공적으로 되었습니다.");
            this.$router.push("/user/signup/complete");
          },
          (error) => {
            alert("회원가입에 실패하였습니다.");
            this.isSubmit = true;
          }
        );
      }
    },
    checkDuplicate(data) {
      UserApi.checkDuplicate();
    },
  },
};
</script>


