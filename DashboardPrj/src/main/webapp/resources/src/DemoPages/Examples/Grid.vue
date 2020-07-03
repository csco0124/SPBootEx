<template>
  <div>
    <page-title :heading=heading :subheading=subheading :icon=icon></page-title>
    <b-card title="Grid Example (Guide Link : https://xaksis.github.io/vue-good-table/)" class="main-card mb-4">
      <vue-good-table :columns="columns" :rows="rows"/>
    </b-card>
  </div>
</template>

<script>
  import PageTitle from "../../Layout/Components/PageTitle.vue";

  export default {
    components: {
      PageTitle
    },
    data: () => ({
      heading: 'Grid',
      subheading: 'vue-good-table Grid Example',
      icon: 'pe-7s-drawer icon-gradient bg-happy-itmeo',
      columns: [
        {
          label: 'Name',
          field: 'name',
        },
        {
          label: 'Age',
          field: 'age',
          type: 'number',
        },
        {
          label: 'Created On',
          field: 'createdAt',
          type: 'date',
          dateInputFormat: 'yyyy-MM-dd',
          dateOutputFormat: 'yyyy-MM-dd',
        },
        {
          label: 'Percent',
          field: 'score',
          type: 'percentage',
        },
      ],
      rows: [
        { id:1, name:"John", age: 20, createdAt: '2020-10-31',score: 0.03343 },
        { id:2, name:"Jane", age: 24, createdAt: '2020-10-31', score: 0.03343 },
        { id:3, name:"Susan", age: 16, createdAt: '2020-10-30', score: 0.03343 },
        { id:4, name:"Chris", age: 55, createdAt: '2020-10-11', score: 0.03343 },
        { id:5, name:"Dan", age: 40, createdAt: '2020-10-21', score: 0.03343 },
        { id:6, name:"John", age: 20, createdAt: '2020-10-31', score: 0.03343 },
      ],
    }),
    mounted() {

    },
    methods: {

    }
  }
</script>
