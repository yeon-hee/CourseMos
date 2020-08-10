<template>
    <div>
        <!-- <input v-model="text"
               :id="inputValue" :placeholder="placeholder"
               type="text"
               @keyup.enter="submit"
               class="form-control form-control"/>
        <label :for="inputValue"></label> -->

        <v-text-field v-model="text" label="검색어를 입력하세요" @keyup.enter="submit"></v-text-field>
    </div>
</template>

<script>

    export default {
        name: "input",
        props : ['inputValue', 'placeholder', 'label'],
        data: () => {
            return {
                text:""
            }
        },
        methods : {
            submit() {
                this.$emit('search', this.text);
            }
        }
    }
</script>
<style scoped>
/* div {
    padding-top : 0;
} */
</style>