<template>
    <div class='SignUpComplete'>
        <h1>환영합니다! 회원가입이 완료되었습니다.</h1>
        <h2>회원가입 인증 메일이 발송되었습니다. 이메일을 확인해 주세요.</h2>
        <button><router-link to="/">로그인하기</router-link></button>
    </div>
</template>

<script>

export default {
    name: 'SignUpComplete',
}
</script>

<style scoped>
.SignUpComplete {
    text-align: center;
}
h1 {
    margin: 50px 0;
}
h2 {
    margin-bottom: 30px;
}
button {
    font-size: 20px;
}

</style>