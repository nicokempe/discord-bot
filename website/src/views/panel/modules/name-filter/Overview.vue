<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="name-filter">
    <h1>This is the Name Filter page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'NameFilter',
  components: {
    Header,
    NavBar
  }
}
</script>