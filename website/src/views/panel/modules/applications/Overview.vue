<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="applications">
    <h1>This is the Applications page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Applications',
  components: {
    Header,
    Sidebar
  }
}
</script>