<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="maintenance">
    <h1>This is the Maintenance page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Maintenance',
  components: {
    Header,
    Sidebar
  }
}
</script>