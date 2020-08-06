<template>
    <div class="input-with-label">

        <input v-model="text"
               :id="inputValue" :placeholder="placeholder"
               type="text"
               @keyup.enter="submit"/>
        <label :for="inputValue">{{label}}</label>
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
