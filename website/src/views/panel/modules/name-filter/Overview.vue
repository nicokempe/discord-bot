<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="name-filter">
    <h1>This is the Name Filter page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'NameFilter',
  components: {
    Header,
    Sidebar
  }
}
</script>