<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="permissions">
    <h1>This is the Permissions page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'Permissions',
  components: {
    Header,
    NavBar
  }
}
</script>