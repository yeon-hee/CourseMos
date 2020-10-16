<template>
    <div>

        <v-text-field v-model="text" 
                outlined prepend-inner-icon="mdi-magnify"
                color="red"
                label="검색어를 입력하세요" @keyup.enter="submit"></v-text-field>
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
</style>