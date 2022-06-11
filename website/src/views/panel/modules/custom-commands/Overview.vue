<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="custom-commands">
    <h1>This is the Custom Commands page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'CustomCommands',
  components: {
    Header,
    NavBar
  }
}
</script>