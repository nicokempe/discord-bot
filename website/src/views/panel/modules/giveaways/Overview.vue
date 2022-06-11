<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="giveaways">
    <h1>This is the Giveaways page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'Giveaways',
  components: {
    Header,
    NavBar
  }
}
</script>