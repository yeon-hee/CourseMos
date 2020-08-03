
<template>
  <div class="user join wrapC">
    <h1>비밀번호 변경하기</h1>
    <div class="form-wrap">
      
      <div class="input-with-label">
        <input 
          v-model.lazy="password" 
          v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
          id="password" :type="passwordType" 
          placeholder="현재 비밀번호를 입력하세요." />
        <label for="password">현재 비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>
     
      <div class="input-with-label">
        <input 
            v-model="newPassword"
            v-bind:class="{error:error.newPassword, complete:!error.newPassword&&newPassword.length!==0}"
            id="newPassword" :type="passwordType" 
            placeholder="변경할 비밀번호를 입력하세요." />
        <label for="newPassword">변경할 비밀번호</label>
        <div class="error-text" v-if="error.newPassword">{{error.newPassword}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          id="password-confirm"
          placeholder="비밀번호를 다시 한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
    </div>


    <button 
      class="btn-bottom"
      @click="onChange"
      :disabled="!isSubmit"
      :class="{disabled : !isSubmit}"
      >비밀번호 변경하기</button>
  </div>
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
