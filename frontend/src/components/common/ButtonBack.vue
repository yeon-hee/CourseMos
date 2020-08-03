<template>
    <button onclick="location.href='javascript:history.back()'" class="btn-small" :class="{noneBackground : !isBackground}">
        {{text}}
    </button>
</template>

<script>
    export default {
        name: "buttonSmall",
        props : ['text', 'isBackground'],
    }
</script>