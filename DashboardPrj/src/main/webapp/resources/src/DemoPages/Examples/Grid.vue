<template>
  <div>
    <page-title :heading=heading :subheading=subheading :icon=icon></page-title>
    <b-card title="Grid Example (Guide Link : https://xaksis.github.io/vue-good-table/)" class="main-card mb-4">
      <vue-good-table v-bind:columns="columns" v-bind:rows="rows" v-bind:select-options="{enabled: true}"
                      v-on:on-row-click="toggleSelectRow">
        <template slot="table-row" slot-scope="props">
          <span v-if="props.row.active">
            <input type="text" v-model="props.row[props.column.field]" style="width : 98%;">
          </span>
          <span v-else>
            {{props.formattedRow[props.column.field]}}
          </span>
        </template>
      </vue-gooe-table>
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
      toggleSelectRow(params) {
        console.log(params.row + ":" + params.pageIndex + ":" + params.selected + ":" + params.row.id + ":" + this.rows[params.pageIndex].id);
        this.selectRow(params.row, params.selected);
        this.changeUpdate(params.row, params.pageIndex, params.selected);
        /*for (let i = 0; i < this.rows.length; i++) {
        	if (params.row.id === this.rows[i].id) {
            this.changeUpdate(this, i, params.selected);

          }
        }*/
      },
      selectRow(rowObj, targetSelected) { //체크박스 선택
      	this.$set(rowObj, 'vgtSelected', targetSelected);
  		},
      changeUpdate(rowObj, row, isActive){ //텍스트박스 활성화/비활성화
        this.$set(rowObj, 'active', isActive);
      }
    }
  }
</script>
