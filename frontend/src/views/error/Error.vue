<template>
    <div class="wrap components-page">
        <div class="error-contents">
            <h1>오류가 발생했습니다.</h1>
            <h2>message : {{message}}</h2>
            <BackButton text="돌아가기" :isBackground="false" />
        </div>
        
    </div>
</template>

<script>
    import BackButton from "../../components/common/ButtonBack";

    export default {
        components : {BackButton},
        data() {
            return{
                message : "error message"
            }
        }
    };

</script>

<style scoped>
.error-contents {
    text-align: center;
}
</style>