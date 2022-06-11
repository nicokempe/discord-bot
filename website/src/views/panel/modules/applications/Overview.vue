<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="applications">
    <h1>This is the Applications page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'Applications',
  components: {
    Header,
    NavBar
  }
}
</script>