<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="backups">
    <h1>This is the Backups page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'Backups',
  components: {
    Header,
    NavBar
  }
}
</script>