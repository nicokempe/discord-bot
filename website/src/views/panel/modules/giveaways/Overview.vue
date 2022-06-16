<template>
  <Header></Header>
  <Sidebar></Sidebar>
  <div class="giveaways">
    <h1>This is the Giveaways page</h1>
  </div>
</template>

<script>
import Header from '@/components/static/Header.vue'
import Sidebar from '@/components/navbar/Sidebar.vue'

export default {
  name: 'Giveaways',
  components: {
    Header,
    Sidebar
  }
}
</script>