<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="messages">
    <h1>This is the Messages page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Messages',
  components: {
    Header,
    Sidebar
  }
}
</script>