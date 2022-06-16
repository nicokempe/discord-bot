<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="permissions">
    <h1>This is the Permissions page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Permissions',
  components: {
    Header,
    Sidebar
  }
}
</script>