<template>
  <div>
    <page-title :heading=heading :subheading=subheading :icon=icon></page-title>
    <b-card title="Bootstrap Table Options" class="main-card mb-4">
        
    </b-card>
  </div>
</template>

<script>
  import PageTitle from "../../Layout/Components/PageTitle.vue";

  export default {
    components: {
      PageTitle,
    },
    data: () => ({
      heading: 'Editor',
      subheading: 'Editor Example',
      icon: 'pe-7s-drawer icon-gradient bg-happy-itmeo',
      editorInitText : "<p>Editor <b>init text!!</b></p>"
    }),
    mounted() {
        
    },
    methods: {

    }
  }
</script>
