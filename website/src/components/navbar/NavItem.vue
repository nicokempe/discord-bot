<template>
    <li>
        <router-link tag="a" :to="path">
            <i :class="icon"></i>
            <p>{{ text }}</p>
        </router-link>
    </li>
</template>

<script>

export default {
    props: {
        text: {
            type: String,
        },
        icon: {
            type: String,
        },
        path: {
            type: String,
        },
    }
}
</script>