<template>
    <div>

        <input v-model="text"
               :id="inputValue" :placeholder="placeholder"
               type="text"
               @keyup.enter="submit"
               class="form-control form-control"/>
        <label :for="inputValue"></label>
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
div {
    padding-top : 0;
}
</style>