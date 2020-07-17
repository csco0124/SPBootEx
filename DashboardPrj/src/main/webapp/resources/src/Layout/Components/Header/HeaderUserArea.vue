<template>
    <div class="d-flex">
        <div class="header-btn-lg pr-0">
            <div class="widget-content p-0">
                <div class="widget-content-wrapper">
                    <div v-if="$store.state.userEmail != undefined" class="widget-content-left">
                        <b-dropdown toggle-class="p-0 mr-2" menu-class="dropdown-menu-lg" variant="link" right>
                            <span slot="button-content">
                                <div class="icon-wrapper icon-wrapper-alt rounded-circle">
                                    <img width="42" class="rounded-circle" v-bind:src="$store.state.userImgUrl">
                                </div>
                            </span>
                            <button type="button" tabindex="0" class="dropdown-item">Menus</button>
                            <button type="button" tabindex="0" class="dropdown-item">Settings</button>
                            <h6 tabindex="-1" class="dropdown-header">Header</h6>
                            <button type="button" tabindex="0" class="dropdown-item">Actions</button>
                            <div tabindex="-1" class="dropdown-divider"></div>
                            <button type="button" v-on:click="userSessionInvalidate()" tabindex="0" class="dropdown-item">Logout</button>
                        </b-dropdown>
                    </div>
                    <div v-if="$store.state.userEmail != undefined" class="widget-content-left  ml-3 header-user-info">
                        <div class="widget-heading">{{$store.state.userName}}</div>
                        <div class="widget-subheading">{{$store.state.userEmail}}</div>
                    </div>
                    <div v-else class="widget-content-left ml-3">
                        <router-link to='/pages/login-boxed'>
                            <b-btn v-b-tooltip.hover title="Login" class="btn-shadow p-1" size="sm" variant="info">
                            <font-awesome-icon icon="sign-in-alt" class="mr-1 ml-1"/>
                            </b-btn>
                        </router-link>
                    </div>
                    <div v-if="$store.state.userEmail != undefined" class="widget-content-right header-user-info ml-3">
                        <b-dropdown toggle-class="p-0 mr-2" menu-class="dropdown-menu-lg" variant="link" right no-caret>
                            <span slot="button-content">
                              <b-btn v-b-tooltip.hover title="Tooltip Example" class="btn-shadow p-1" size="sm" variant="info">
                                  <font-awesome-icon icon="calendar-alt" class="mr-1 ml-1"/>
                              </b-btn>
                            </span>
                            <template v-for="(item) in meetDataList" v-bind:key="item.summary">
                              {{item.summary}}[
                              <template v-for="(attendees, index) in item.attendeesList" v-bind:key="attendees.email">
                                {{attendees.displayName}}&nbsp;
                              </template>]<br>
                            </template>
                        </b-dropdown>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    import VuePerfectScrollbar from 'vue-perfect-scrollbar'

    import {library} from '@fortawesome/fontawesome-svg-core'
    import {
        faAngleDown,
        faCalendarAlt,
        faTrashAlt,
        faCheck,
        faFileAlt,
        faCloudDownloadAlt,
        faFileExcel,
        faFilePdf,
        faFileArchive,
        faEllipsisH,
        faSignInAlt,
    } from '@fortawesome/free-solid-svg-icons'
    import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

    library.add(
        faAngleDown,
        faCalendarAlt,
        faTrashAlt,
        faCheck,
        faFileAlt,
        faCloudDownloadAlt,
        faFileExcel,
        faFilePdf,
        faFileArchive,
        faEllipsisH,
        faSignInAlt,
    );

    export default {
        components: {
            VuePerfectScrollbar,
            'font-awesome-icon': FontAwesomeIcon,
        },
        data: () => ({
            meetDataList : []
        }),
        methods: {
            userSessionInvalidate(){
              this.$store.dispatch('userSessionInvalidate');
            },
            getGoogleCalendarMeetList(){
              axios.post('/getCalendarMeetList').then(response => {
                this.meetDataList = response.data;
                console.log(this.meetDataList);
              });
            }
        },
        mounted() {
          if(this.$store.state.userEmail != undefined){
              this.getGoogleCalendarMeetList();
          }
        }
    }


</script>
