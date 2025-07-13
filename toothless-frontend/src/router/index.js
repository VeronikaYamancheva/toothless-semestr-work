import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/presentations/views/home/HomeView.vue';
import LoginView from '@/presentations/views/auth/LoginView.vue';
import RegisterView from '@/presentations/views/auth/RegisterView.vue';
import VerifyView from '@/presentations/views/auth/VerifyView.vue';
import { useAuthStore } from '@/store/auth.store';
import ProcedureView from "@/presentations/views/procedure/ProcedureView.vue";
import ProfileView from "@/presentations/views/profile/ProfileView.vue";
import OAuth2Success from "@/presentations/views/OAuth2Success.vue";
import UsersListView from "@/presentations/views/admin/UsersListView.vue";
import DentistsAcceptListView from "@/presentations/views/admin/DentistsAcceptListView.vue";
import DentistsView from "@/presentations/views/dentists/DentistsView.vue";
import DentistDetailedView from "@/presentations/views/dentists/DentistDetailedView.vue";
import AppointmentView from "@/presentations/views/appointment/AppointmentView.vue";
import AppointmentProcedures from "@/presentations/components/appoinment/AppointmentProcedures.vue";
import AppointmentTimetable from "@/presentations/components/appoinment/AppointmentTimetable.vue";
import AppointmentDentists from "@/presentations/components/appoinment/AppointmentDentists.vue";
import ProcedureManagementView from "@/presentations/views/procedure/ProcedureManagementView.vue";
import AppointmentListView from "@/presentations/views/appointment/AppointmentListView.vue";
import {inject} from "vue";
import MyAppointment from "@/presentations/views/appointment/MyAppointment.vue";
import ErrorNotFound from "@/presentations/components/layout/ErrorNotFound.vue";

const alert = inject('alert')


const router = createRouter({
    history: createWebHistory('/'),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView,
            meta: { requiresAuth: false }
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView,
            meta: { requiresAuth: false }
        },
        {
            path: '/register',
            name: 'register',
            component: RegisterView,
            meta: { requiresAuth: false }
        },
        {
            path: '/verify',
            name: 'verify',
            component: VerifyView,
            meta: { requiresAuth: false }
        },
        {
            path: '/procedures',
            name: 'procedures',
            component: ProcedureView,
            meta: {requiresAuth: false}
        },
        {
            path: '/procedures/management',
            name: 'procedures-management',
            component: ProcedureManagementView,
            meta: {
                requiresAuth: true,
                roles: ['ADMIN', 'MASTER']
            }
        },
        {
            path: '/oauth/success',
            name: 'oauth2-success',
            component: OAuth2Success,
            meta: {requiresAuth: false}
        },
        {
            path: '/profile',
            name: 'profile',
            component: ProfileView,
            meta: { requiresAuth: true }
        },
        {
            path: '/users',
            name: 'users',
            component: UsersListView,
            meta: { requiresAuth: true,
                roles: ['ADMIN', 'MASTER']
            }
        },
        {
            path: '/dentists',
            name: 'dentists',
            component: DentistsView,
            meta: { requiresAuth: false }
        },
        {
            path: '/dentists/:id',
            name: 'dentist-detailed',
            component: DentistDetailedView,
            meta: { requiresAuth: false }
        },
        {
            path: '/dentists/accept',
            name: 'dentists-accept',
            component: DentistsAcceptListView,
            meta: {
                requiresAuth: true,
                roles: ['ADMIN', 'MASTER']
            }
        },
        {
            path: '/appointment',
            component: AppointmentView,
            children: [
                {
                    path: 'procedures',
                    name: 'AppointmentProcedures',
                    component: AppointmentProcedures,
                },
                {
                    path: ':procedureId/dentists',
                    name: 'AppointmentDentists',
                    component: AppointmentDentists,
                },
                {
                    path: ':procedureId/:dentistId/timetable',
                    name: 'AppointmentTimetable',
                    component: AppointmentTimetable,
                },
            ],
        },
        {
            path: '/appointment/list',
            name: 'appointment-list',
            component: AppointmentListView,
            meta: {
                requiresAuth: true,
                roles: ['ADMIN', 'MASTER']
            }
        },
        {
            path: '/appointments',
            name: 'appointments',
            component: MyAppointment,
            meta: {
                requiresAuth: true,
            }
        },
        {
            path: '/:pathMatch(.*)*',
            name: 'NotFound',
            component: ErrorNotFound,
        }


    ],
    scrollBehavior(to, from, savedPosition) {
        return { top: 0 }
    }
});

router.beforeEach(async (to, from, next) => {
    const authStore = useAuthStore(); //из localstorage состояние аутентификации

    await authStore.init()
    const requiresAuth = to.meta.requiresAuth;
    const allowedRoles = to.meta.roles;

    if (requiresAuth && !(authStore.isAuthenticated) && !(authStore.user)) {
       return next('/login');
    } else if ((to.name === 'login' || to.name === 'register') && authStore.isAuthenticated) {
       return next('/profile');
    } else if (requiresAuth && allowedRoles?.length) {

        const userRoles = authStore.user?.roles || [];

        const hasAccess = userRoles.some(role => allowedRoles.includes(role));

        if (!hasAccess) {
            return next('/login');
        }
        return next();
    } else {
        return next();
    }

});

export default router;