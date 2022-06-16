<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="tickets">
    <h1>This is the Tickets page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Tickets',
  components: {
    Header,
    Sidebar
  }
}
</script>