<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="statistics">
    <h1>This is the Statistics page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Statistics',
  components: {
    Header,
    Sidebar
  }
}
</script>