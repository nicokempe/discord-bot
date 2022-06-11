<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="welcomes">
    <h1>This is the Welcomes page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'Welcomes',
  components: {
    Header,
    NavBar
  }
}
</script>