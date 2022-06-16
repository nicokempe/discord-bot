<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="updater">
    <h1>This is the Update page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Updater',
  components: {
    Header,
    Sidebar
  }
}
</script>