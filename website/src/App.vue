<template>
  <router-view></router-view>
  <Footer></Footer>
</template>

<script>
import Footer from '@/components/static/Footer.vue'

export default {
  name: 'Dashboard',
  components: {
    Footer
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
