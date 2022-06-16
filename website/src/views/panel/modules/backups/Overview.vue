<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="backups">
    <h1>This is the Backups page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Backups',
  components: {
    Header,
    Sidebar
  }
}
</script>