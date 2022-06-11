<template>
  <Header></Header>
  <NavBar></NavBar>
  <div class="chat-filter">
    <h1>This is the Chat Filter page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import NavBar from '@/components/static/NavBar.vue'

export default {
  name: 'ChatFilter',
  components: {
    Header,
    NavBar
  }
}
</script>