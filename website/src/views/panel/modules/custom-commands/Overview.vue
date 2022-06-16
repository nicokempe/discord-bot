<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="custom-commands">
    <h1>This is the Custom Commands page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'CustomCommands',
  components: {
    Header,
    Sidebar
  }
}
</script>