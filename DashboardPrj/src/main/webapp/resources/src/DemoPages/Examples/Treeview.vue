<template>
  <div>
    <page-title :heading=heading :subheading=subheading :icon=icon></page-title>
    <b-card title="Treeview Example" class="main-card mb-4">
       <v-jstree :data="treeData" show-checkbox multiple allow-batch whole-row @item-click="itemClick"></v-jstree>
    </b-card>
  </div>
</template>

<script>
  import PageTitle from "../../Layout/Components/PageTitle.vue";
  import VJstree from 'vue-jstree'

  export default {
    components: {
      PageTitle,
      VJstree
    },
    data: () => ({
      heading: 'TreeView',
      subheading: 'TreeView Example',
      icon: 'pe-7s-drawer icon-gradient bg-happy-itmeo',
      treeData: [
          {
            "text": "Same but with checkboxes",
            "children": [
              {
                "text": "initially selected",
                "selected": true
              },
              {
                "text": "custom icon",
                "icon": "fa fa-warning icon-state-danger"
              },
              {
                "text": "initially open",
                "icon": "fa fa-folder icon-state-default",
                "opened": true,
                "children": [
                  {
                    "text": "Another node"
                  }
                ]
              },
              {
                "text": "custom icon",
                "icon": "fa fa-warning icon-state-warning"
              },
              {
                "text": "disabled node",
                "icon": "fa fa-check icon-state-success",
                "disabled": true
              }
            ]
          },
          {
            "text": "Same but with checkboxes",
            "opened": true,
            "children": [
              {
                "text": "initially selected",
                "selected": true
              },
              {
                "text": "custom icon",
                "icon": "fa fa-warning icon-state-danger"
              },
              {
                "text": "initially open",
                "icon": "fa fa-folder icon-state-default",
                "opened": true,
                "children": [
                  {
                    "text": "Another node"
                  }
                ]
              },
              {
                "text": "custom icon",
                "icon": "fa fa-warning icon-state-warning"
              },
              {
                "text": "disabled node",
                "icon": "fa fa-check icon-state-success",
                "disabled": true
              }
            ]
          },
          {
            "text": "And wholerow selection"
          }
        ]
    }),
    mounted() {
        
    },
    methods: {
      itemClick (node) {
          alert(node.model.text + ' clicked !');
        }
    }
  }
</script>
