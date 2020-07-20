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
                              <b-btn v-b-tooltip.hover title="Today Meeting List" class="btn-shadow p-1" size="sm" variant="info">
                                  <font-awesome-icon icon="calendar-alt" class="mr-1 ml-1"/>
                              </b-btn>
                            </span>
                            <b-dropdown-header id="dropdown-header-label" style="padding-left:15px;">
                              Today Meeting List
                            </b-dropdown-header>
                            <div style="width:400px; margin:0px 15px 0px 15px;" v-for="(item) in meetDataList" v-bind:key="item.summary">
                              <span class="font-weight-bold"><i class="pe-7s-headphones icon-gradient bg-premium-dark" style="padding-right: 6px;"/>[{{item.summary}}]</span><br>
                              <span class="font-weight-bold"><i class="pe-7s-map-2 icon-gradient bg-premium-dark" style="padding-right: 8px;"/>{{item.location}}</span><br>
                              <span class="font-weight-normal"><i class="pe-7s-timer icon-gradient bg-premium-dark" style="padding-right: 5px;"/>{{item.start_datetime_str}}&nbsp;~&nbsp;{{item.end_datetime_str}}</span><br>
                              <div>
                                <div class="float-left">
                                  <i class="pe-7s-users icon-gradient bg-premium-dark" style="padding-right: 5px;"/>
                                </div>
                                <div style="margin-left:17px;">
                                  <template v-for="(attendees, index) in item.attendeesList" v-bind:key="attendees.email">
                                    <span class="font-weight-light">{{attendees.displayName}}</span>(<span class="font-italic">{{attendees.email}}</span>)<br>
                                  </template>
                                </div>
                              </div>
                              <b-dropdown-divider></b-dropdown-divider>
                            </div>
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
