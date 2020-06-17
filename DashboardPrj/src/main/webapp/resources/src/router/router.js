import Vue from 'vue'
import VueRouter from 'vue-router'
import Analytics from '../DemoPages/Dashboards/Analytics.vue'
import Editor from '../DemoPages/Examples/Editor.vue'
import LoginBoxed from '../DemoPages/UserPages/LoginBoxed.vue'
import RegisterBoxed from '../DemoPages/UserPages/RegisterBoxed.vue'
import ForgotPasswordBoxed from '../DemoPages/UserPages/ForgotPasswordBoxed.vue'
import Standard from '../DemoPages/Elements/Buttons/Standard.vue'
import Dropdowns from '../DemoPages/Elements/Dropdowns.vue'
import Icons from '../DemoPages/Elements/Icons.vue'
import Badges from '../DemoPages/Elements/Badges.vue'
import Cards from '../DemoPages/Elements/Cards.vue'
import ListGroups from '../DemoPages/Elements/ListGroups.vue'
import Timeline from '../DemoPages/Elements/Timeline.vue'
import Utilities from '../DemoPages/Elements/Utilities.vue'
import Tabs from '../DemoPages/Components/Tabs.vue'
import Accordions from '../DemoPages/Components/Accordions.vue'
import Modals from '../DemoPages/Components/Modals.vue'
import ProgressBar from '../DemoPages/Components/ProgressBar.vue'
import TooltipsPopovers from '../DemoPages/Components/TooltipsPopovers.vue'
import Carousel from '../DemoPages/Components/Carousel.vue'
import Pagination from '../DemoPages/Components/Pagination.vue'
import Maps from '../DemoPages/Components/Maps.vue'
import RegularTables from '../DemoPages/Tables/RegularTables.vue'
import ChartBoxes3 from '../DemoPages/Widgets/ChartBoxes3.vue'
import Controls from '../DemoPages/Forms/Elements/Controls.vue'
import Layouts from '../DemoPages/Forms/Elements/Layouts.vue'
import Chartjs from '../DemoPages/Charts/Chartjs.vue'
Vue.use(VueRouter);

const router = new VueRouter({
	scrollBehavior() {
		return window.scrollTo({ top: 0, behavior: 'smooth' });
	},
	routes: [
		// Dashboards
		{
			path: '/',
			name: 'analytics',
			component: Analytics,
		},

		// Examples
		{
			path: '/examples/editor',
			name: 'editor',
			component: Editor,
		},

		// Pages

		{
			path: '/pages/login-boxed',
			name: 'login-boxed',
			meta: { layout: 'userpages' },
			component: LoginBoxed,
		},
		{
			path: '/pages/register-boxed',
			name: 'register-boxed',
			meta: { layout: 'userpages' },
			component: RegisterBoxed,
		},
		{
			path: '/pages/forgot-password-boxed',
			name: 'forgot-password-boxed',
			meta: { layout: 'userpages' },
			component: ForgotPasswordBoxed,
		},

		// Elements

		{
			path: '/elements/buttons-standard',
			name: 'buttons-standard',
			component: Standard,
		},
		{
			path: '/elements/dropdowns',
			name: 'dropdowns',
			component: Dropdowns,
		},
		{
			path: '/elements/icons',
			name: 'icons',
			component: Icons,
		},
		{
			path: '/elements/badges-labels',
			name: 'badges',
			component: Badges,
		},
		{
			path: '/elements/cards',
			name: 'cards',
			component: Cards,
		},
		{
			path: '/elements/list-group',
			name: 'list-group',
			component: ListGroups,
		},
		{
			path: '/elements/timelines',
			name: 'timeline',
			component: Timeline,
		},
		{
			path: '/elements/utilities',
			name: 'utilities',
			component: Utilities,
		},

		// Components

		{
			path: '/components/tabs',
			name: 'tabs',
			component: Tabs,
		},
		{
			path: '/components/accordions',
			name: 'accordions',
			component: Accordions,
		},
		{
			path: '/components/modals',
			name: 'modals',
			component: Modals,
		},
		{
			path: '/components/progress-bar',
			name: 'progress-bar',
			component: ProgressBar,
		},
		{
			path: '/components/tooltips-popovers',
			name: 'tooltips-popovers',
			component: TooltipsPopovers,
		},
		{
			path: '/components/carousel',
			name: 'carousel',
			component: Carousel,
		},
		{
			path: '/components/pagination',
			name: 'pagination',
			component: Pagination,
		},
		{
			path: '/components/maps',
			name: 'maps',
			component: Maps,
		},

		// Tables

		{
			path: '/tables/regular-tables',
			name: 'regular-tables',
			component: RegularTables,
		},

		// Dashboard Widgets

		{
			path: '/widgets/chart-boxes-3',
			name: 'chart-boxes-3',
			component: ChartBoxes3,
		},

		// Forms

		{
			path: '/forms/controls',
			name: 'controls',
			component: Controls,
		},
		{
			path: '/forms/layouts',
			name: 'layouts',
			component: Layouts,
		},
		// Charts

		{
			path: '/charts/chartjs',
			name: 'chartjs',
			component: Chartjs,
		}
	]
})

export default router