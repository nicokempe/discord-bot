<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="messages">
    <h1>This is the Messages page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'Messages',
  components: {
    Header,
    NavBar
  }
}
</script>