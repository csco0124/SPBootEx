<template>
  <div>
    <page-title :heading=heading :subheading=subheading :icon=icon></page-title>
    <b-button class="mr-2 mb-2" variant="alternate" v-on:click="getEditorData()">
      get EditorData
    </b-button>
    <b-card title="CKEditor Example" class="main-card mb-4">
      <textarea id="editor" rows="10" cols="80">
          This is my textarea to be replaced with CKEditor 4.
      </textarea>
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
      icon: 'pe-7s-drawer icon-gradient bg-happy-itmeo'
    }),
    mounted() {
        editorInit('editor');
    },
    methods: {
      getEditorData(){
        console.log(CKEDITOR.instances.editor.getData());
      }
    }
  }
</script>
