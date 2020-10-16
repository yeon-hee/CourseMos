
<template>
  <v-container fluid class="mt-2 mb-16">
    <v-row>
      <v-col cols="12" sm="6" offset-sm="3">
        <v-card class="pa-5">
          <v-row>
            <v-col cols="12">
              <h1>비밀번호 변경하기</h1>
              <p>Change Password</p>
            </v-col>
          </v-row>

          <v-text-field
            label="현재 비밀번호" 
            v-model.lazy="password" 
            id="password" :type="passwordType"
            :error-messages="error.password"
            placeholder="현재 비밀번호를 입력하세요." />
          
          <v-text-field
            label="변경할 비밀번호"
            v-model.lazy="newPassword"
            :error-messages="error.newPassword"
            id="newPassword" :type="passwordType" 
            placeholder="변경할 비밀번호를 입력하세요." />

          <v-text-field
            label="비밀번호 확인"
            v-model.lazy="passwordConfirm"
            :type="passwordConfirmType"
            :error-messages="error.passwordConfirm"
            id="password-confirm"
            placeholder="비밀번호를 다시 한번 입력하세요."
          />

          <v-row justify="center">
            <v-col cols="12" style="text-align:center;">
              <v-btn
                color="pink"
                class="pink white--text"
                @click="onChange"
                :disabled="!isSubmit"
                :class="{disabled : !isSubmit}"
              >
                비밀번호 변경하기
              </v-btn>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import PV from "password-validator";
import UserApi from "../../api/UserApi";
import axios from 'axios'

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
  watch: {
    password: function(v) {
      this.check();
    },
    newPassword: function(v) {
      this.newcheckForm();
    },
    passwordConfirm: function(v) {
        this.checkFormconfirm();
    }
  },
  methods: {
    check(){
      let data = { 
        password : this.password,
        token : localStorage.getItem('token')
      }
      UserApi.checkPassword(
        data,
        response => {
            this.error.password = false;
        },
        error => {
          this.error.password = "현재 비밀번호와 같지 않습니다."
        }
      )
    },
    newcheckForm() {
      if (
        this.newPassword.length >= 0 &&
        !this.passwordSchema.validate(this.newPassword)
      )
        this.error.newPassword = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.newPassword = false;
    },
    checkFormconfirm(){
       if(this.newPassword != this.passwordConfirm)
        this.error.passwordConfirm = "비밀번호가 일치하지 않습니다.";
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;

      if(this.newPassword == this.passwordConfirm) {
         this.isSubmit = true;
     }
    },
    onChange() {
      if (this.isSubmit) {
        let data = {
          password : this.newPassword,
          token : localStorage.getItem('token')
        };
        console.log('비밀번호 변경 들어옴 ');

        UserApi.requestPassword(
          data,
          response => {
            console.log('비밀번호 변경 성공!');
            alert("비밀번호 변경에 성공하였습니다!");
            this.isSubmit = true;
            this.$router.push("/feed/main");
          },
          error => {
            console.log(error);
            alert('실패하였습니다.');
            this.isSubmit = true;
          }
        );
        this.isSubmit = false;
      }
    }
  },
  data: () => {
    return {
      password: "",
      newPassword: "",
      passwordSchema: new PV(),
      passwordConfirm: "",
      isLoading: false,
      error: {
        password: false,
        newPassword: false,
        passwordConfirm: false
      },
      isSubmit: false,
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false
    };
  }
};
</script>
