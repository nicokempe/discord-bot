<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="chat-filter">
    <h1>This is the Chat Filter page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'ChatFilter',
  components: {
    Header,
    Sidebar
  }
}
</script>