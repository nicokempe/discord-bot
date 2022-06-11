<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="tickets">
    <h1>This is the Tickets page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'Tickets',
  components: {
    Header,
    NavBar
  }
}
</script>