<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="updater">
    <h1>This is the Update page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'Updater',
  components: {
    Header,
    NavBar
  }
}
</script>