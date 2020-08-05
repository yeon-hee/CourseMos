<template>
    <div class="wrap components-page">
        <div class="wrapB">
            <h1>피드 작성하기</h1>

            <h2>form</h2>
            <div class="wrap">
                <!-- input type=text -->

                <!-- textarea -->
                <TextareaComponent
                inputValue="contents"
                placeholder="의견을 적어주세요."
                label="게시하기"
                maxlength="300"
                ></TextareaComponent>
                    
                <LargeButton text="내 큐레이션에 추가" :isBackground="false" />
            </div>

        </div>
    </div>
</template>

<script>
import TextareaComponent from "../../components/common/Textarea";
import LargeButton from "../../components/common/ButtonLarge";
import "../../assets/css/components.scss";

export default {
  components: {
    TextareaComponent,
    LargeButton,
  }
};
</script>

<style scoped>
</style>